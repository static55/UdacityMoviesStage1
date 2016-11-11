package com.stewartrap.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.stewartrap.movies.bus.BusProvider;
import com.stewartrap.movies.model.Screen;

public class MovieDetailFragment extends Fragment {

    public final String TAG = MovieDetailFragment.class.getSimpleName();

    ImageView mMoviePosterView;
    ImageView mMovieBackdropView;
    TextView mTitleView;
    TextView mReleaseDateView;
    TextView mUserRatingView;
    TextView mPlotSynopsisView;
    LinearLayout mLinearLayout;

    public MovieDetailFragment() {}

    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        BusProvider.getInstance().register( this );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister( this );
    }

    @Override
    public View onCreateView(   LayoutInflater inflater,
                                ViewGroup container,
                                Bundle savedInstanceState ) {

        View thisView = inflater.inflate( R.layout.movie_detail_fragment_layout, container, false );

        mMoviePosterView = (ImageView) thisView.findViewById( R.id.moviePoster );
        mMovieBackdropView = (ImageView) thisView.findViewById( R.id.movieBackdrop );
        mTitleView = (TextView) thisView.findViewById( R.id.movieTitle );
        mReleaseDateView = (TextView) thisView.findViewById( R.id.releaseDate );
        mUserRatingView = (TextView) thisView.findViewById( R.id.userRating );
        mPlotSynopsisView = (TextView) thisView.findViewById( R.id.plotSynopsis );
        mLinearLayout = (LinearLayout) thisView.findViewById( R.id.linearLayout );

        if ( ! Screen.isMultiPane( getActivity() ) )
            drawWidgets( getActivity().getIntent(), getActivity() );

        return thisView;
    }

    private void drawWidgets( Intent intent, Context context ) {

        String posterUrl = intent.getStringExtra( "posterUrl" );
        String backdropUrl = intent.getStringExtra( "backdropUrl" );
        String title = intent.getStringExtra( "title" );
        String releaseDate = intent.getStringExtra( "releaseDate" );
        String userRating = intent.getStringExtra( "userRating" );
        String plotSynopsis = intent.getStringExtra( "plotSynopsis" );
        backdropUrl = backdropUrl.replaceAll( "/w185/", "/w342/" );

        mTitleView.setText( title );
        mReleaseDateView.setText( releaseDate );
        mUserRatingView.setText( userRating );
        mPlotSynopsisView.setText( plotSynopsis );

        Log.d( TAG, "getActivity() is " + getActivity() );
        int backdropWidth = Screen.getWidth( getActivity() );
        if ( Screen.isMultiPane( context ) ) backdropWidth /= 2;
        int backdropHeight = (int) ( backdropWidth * 0.5614 );
        int posterWidth = backdropWidth / 2;
        int posterHeight = (int) ( posterWidth * 1.5 );

        mLinearLayout.setMinimumHeight( posterHeight );
        mMoviePosterView.setMinimumHeight( posterWidth );

        Picasso.with( getActivity() )
                .load( posterUrl )
                .placeholder( R.drawable.null_poster )
                .error( R.mipmap.error )
                .resize( posterWidth, posterHeight )
                .into( mMoviePosterView );

        Picasso.with( getActivity() )
                .load( backdropUrl )
                .placeholder( R.drawable.null_backdrop )
                .error( R.mipmap.error )
                .resize( backdropWidth, backdropHeight )
                .into( mMovieBackdropView );
    }

    @Subscribe
    public void handleMultipaneIntent( Intent intent ) {

        drawWidgets( intent, getActivity() );
    }



}

