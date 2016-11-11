package com.stewartrap.movies;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.stewartrap.movies.bus.BusProvider;

public class MovieDetailActivity extends AppCompatActivity {

    public final String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.movie_detail_activity_layout );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar );
        setSupportActionBar( toolbar );

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add( R.id.detailFragmentPlaceholder, movieDetailFragment, "movieDetailFragment" );
        transaction.commit();
    }
}
