package com.stewartrap.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.squareup.otto.Subscribe;
import com.stewartrap.movies.bus.BusProvider;
import com.stewartrap.movies.bus.HideDialog;
import com.stewartrap.movies.bus.ShowDialog;
import com.stewartrap.movies.model.Movie;
import com.stewartrap.movies.model.Screen;

public class MovieListFragment extends Fragment {

    public final String TAG = MovieListFragment.class.getSimpleName();
    ImageAdapter mImageAdapter;
    ProgressDialog mDialog;

    public MovieListFragment() {}

    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        BusProvider.getInstance().register( this );
        setHasOptionsMenu( true );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister( this );
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {

        super.onConfigurationChanged( newConfig );
        mImageAdapter.updateImageViewHeights();
    }

    @Override
    public View onCreateView(   LayoutInflater inflater,
                                ViewGroup container,
                                Bundle savedInstanceState ) {

        View thisView = inflater.inflate( R.layout.movie_list_fragment_layout, container, false );
        GridView gridView = (GridView) thisView.findViewById( R.id.gridView );
        Log.d( TAG, "in onCreateView, mImageAdapter is " + mImageAdapter );
        mImageAdapter = new ImageAdapter( getActivity() );
        gridView.setAdapter( mImageAdapter );
        mImageAdapter.setGridView( gridView );

        gridView.setOnItemClickListener( ( parent, v, position, id ) -> {

            Intent movieDetailIntent = new Intent( getActivity(), MovieDetailActivity.class );
            Movie movie = (Movie) mImageAdapter.getItem( position );
            movieDetailIntent.putExtra( "posterUrl", movie.getPosterUrl() );
            movieDetailIntent.putExtra( "backdropUrl", movie.getBackdropUrl() );
            movieDetailIntent.putExtra( "plotSynopsis", movie.getPlotSynopsis() );
            movieDetailIntent.putExtra( "popularity", String.valueOf( movie.getPopularity() ) );
            movieDetailIntent.putExtra( "releaseDate", movie.getReleaseDate() );
            movieDetailIntent.putExtra( "userRating", String.valueOf( movie.getVoteAverage() ) );
            movieDetailIntent.putExtra( "title", movie.getTitle() );


            if ( Screen.isMultiPane( getActivity() ) ) {

                BusProvider.getInstance().post( movieDetailIntent );

            } else {

                startActivity( movieDetailIntent );
            }
        });

        ConnectivityManager manager =
                (ConnectivityManager) getActivity().getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();





        JsonDownloadTask task = new JsonDownloadTask();
        task.setImageAdapter( mImageAdapter );
        task.execute();

        return thisView;
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        menu.clear();
        inflater.inflate( R.menu.movie_list_fragment_menu, menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        Log.d( TAG, "in onOptionItemSelected() mImageAdapter is " + mImageAdapter );
        int id = item.getItemId();

        if ( id == R.id.action_order_by_popularity ) {
            Log.d( TAG, "action_order_by_popularity" );

            mImageAdapter.sortBy( SortTypeEnum.POPULARITY );
            return true;

        } else if ( id == R.id.action_order_by_rating ) {
            Log.d( TAG, "action_order_by_rating" );


            mImageAdapter.sortBy( SortTypeEnum.RATING );
            return true;
        }

        return super.onOptionsItemSelected( item );

    }

    @Subscribe
    public void showNoInternetDialog( ShowDialog foo ) {

        mDialog = ProgressDialog.show(
                getActivity(),
                "Unable to connect to Internet",
                "Waiting for Internet connection ...",
                true, true );
    }

    @Subscribe
    public void dismissNoInternetDialog( HideDialog foo ) {

        if ( mDialog != null )
            mDialog.dismiss();
    }
}
