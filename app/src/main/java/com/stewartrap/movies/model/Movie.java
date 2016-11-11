package com.stewartrap.movies.model;

import android.util.Log;
import android.widget.ImageView;

public class Movie {

    public final String TAG = Movie.class.getSimpleName();

    ImageView mPosterImageView;
    ImageView mBackdropImageView;
    String mTitle;
    String mPosterUrl;
    String mBackdropUrl;
    String mPlotSynopsis;
    String mReleaseDate;
    Float mPopularity;
    Float mVoteAverage;

    public String getTitle() { return mTitle; }

    public String getPlotSynopsis() { return mPlotSynopsis; }

    public String getReleaseDate() { return mReleaseDate; }

    public String getBackdropUrl() { return mBackdropUrl; }

    public Float getPopularity() { return mPopularity; }

    public Float getVoteAverage() { return mVoteAverage; }

    public String getPosterUrl() { return mPosterUrl; }

    public ImageView getPosterImageView() { return mPosterImageView; }

    public void setPosterImageView( ImageView posterImageView ) { mPosterImageView = posterImageView; }

    public void setBackdropImageView( ImageView backdropImageView ) { mBackdropImageView = backdropImageView; }

    public Movie( MoviePojo moviePojo ) {

        final String baseImageUrl = "http://image.tmdb.org/t/p/w185/";
        mPosterUrl = baseImageUrl + moviePojo.getPoster_path();
        mPlotSynopsis = moviePojo.getOverview();
        mReleaseDate = moviePojo.getRelease_date().substring( 0, 4 );

        mTitle = moviePojo.getOriginal_title();
        mBackdropUrl = baseImageUrl + moviePojo.getBackdrop_path();
        mPopularity = Float.valueOf( moviePojo.getPopularity() );
        mVoteAverage = Float.valueOf( moviePojo.getVote_average() );

    }

    @Override
    public String toString() {

        return "Movie{" +
                "mImageUrl='" + mPosterUrl + '\'' +
                ", mBackdropUrl='" + mBackdropUrl + '\'' +
                ", mVoteAverage=" + mVoteAverage +
                ", mPopularity=" + mPopularity +
                '}';
    }
}
