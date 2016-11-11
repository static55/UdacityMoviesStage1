package com.stewartrap.movies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.stewartrap.movies.bus.BusProvider;
import com.stewartrap.movies.model.Screen;

public class MovieListActivity extends AppCompatActivity {


    public final String TAG = MovieListActivity.class.getSimpleName();
    public MovieListFragment mMovieListFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.movie_list_activity_layout );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        BusProvider.getInstance().register( this );

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mMovieListFragment = new MovieListFragment();
        transaction.add( R.id.listFragmentPlaceholder, mMovieListFragment );

        if ( Screen.isMultiPane( this ) ) {

            if ( savedInstanceState == null ) {

                setSupportActionBar( toolbar );
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                transaction.add( R.id.detailFragmentPlaceholder, movieDetailFragment, "movieDetailFragment" );
            }
        }

        transaction.commit();


    }


}
