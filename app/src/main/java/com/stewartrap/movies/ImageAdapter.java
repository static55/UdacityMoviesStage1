package com.stewartrap.movies;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.stewartrap.movies.model.Movie;
import com.stewartrap.movies.model.Screen;

import java.util.ArrayList;
import java.util.Collections;

public class ImageAdapter extends BaseAdapter {

    public final String TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    GridView mGridView;

    @Override public int getCount() { return mMovies.size(); }

    @Override public Object getItem( int position ) { return mMovies.get( position ); }

    @Override public long getItemId( int position ) { return 0; }

    public void setGridView( GridView gridView ) { mGridView = gridView; }

    public ImageAdapter( Context context ) { mContext = context; }

    public Context getContext() { return mContext; }

    public View getView( int position, View convertView, ViewGroup parent ) {

        return mMovies.get( position ).getPosterImageView();
    }

    public void addMovie( Movie movie ) {

        ImageView posterImageView = new ImageView( mContext );

        if ( Screen.isMultiPane( mContext ) )
            posterImageView.setMinimumHeight((int) ((Screen.getWidth( mContext ) / 4) * 1.5));
        else
            posterImageView.setMinimumHeight((int) ((Screen.getWidth( mContext ) / 2) * 1.5));


        posterImageView.setScaleType( ImageView.ScaleType.FIT_XY );

        Picasso.with( mContext )
                .load( movie.getPosterUrl() )
                .placeholder( new ColorDrawable( Color.TRANSPARENT ) )
                .error( R.mipmap.error )
                .into( posterImageView );

        movie.setPosterImageView( posterImageView );
        mMovies.add( movie );
        mGridView.invalidateViews();
    }

    public void updateImageViewHeights() {

        for ( Movie movie : mMovies ) {

            if ( Screen.isMultiPane( mContext ) )
                movie.getPosterImageView().setMinimumHeight((int) ((Screen.getWidth( mContext ) / 4) * 1.5));
            else
                movie.getPosterImageView().setMinimumHeight((int) ((Screen.getWidth( mContext ) / 2) * 1.5));
        }
    }

    public void sortBy( SortTypeEnum type ) {

        Log.d( TAG, "in sortBy()" );
        if ( type == SortTypeEnum.POPULARITY ) {
            Log.d( TAG, "POPULARITY" );


            Collections.sort( mMovies, ( o1, o2 ) -> {
                if ( o1.getPopularity() > o2.getPopularity() ) { return -1; }
                else if ( o1.getPopularity() == o2.getPopularity() ) { return 0; }
                else { return 1; }
            });

        } else if ( type == SortTypeEnum.RATING ) {
            Log.d( TAG, "RATING" );

            Collections.sort( mMovies, ( o1, o2 ) -> {
                if ( o1.getVoteAverage() > o2.getVoteAverage() ) { return -1; }
                else if ( o1.getVoteAverage() == o2.getVoteAverage() ) { return 0; }
                else { return 1; }
            });
        }

        mGridView.invalidateViews();
    }

}