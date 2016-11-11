package com.stewartrap.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;
import com.stewartrap.movies.bus.BusProvider;
import com.stewartrap.movies.bus.HideDialog;
import com.stewartrap.movies.bus.ShowDialog;
import com.stewartrap.movies.model.Movie;
import com.stewartrap.movies.model.MoviePojo;
import com.stewartrap.movies.model.QueryResultPojo;
import com.stewartrap.movies.model.Screen;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonDownloadTask extends AsyncTask<String, Void, String> {

    public final String TAG = JsonDownloadTask.class.getSimpleName();
    ImageAdapter mImageAdapter;

    @Override
    protected void onPostExecute( String result ) {

        Log.d( TAG, result );
        Gson gson = new Gson();
        QueryResultPojo queryResult  = gson.fromJson( result, QueryResultPojo.class );

        int i = 0;
        for ( MoviePojo moviePojo : queryResult.results ) {

            Movie movie = new Movie( moviePojo );

            if ( i == 0 && Screen.isMultiPane( mImageAdapter.getContext() ) ) {

                Intent movieDetailIntent = new Intent();
                movieDetailIntent.putExtra( "posterUrl", movie.getPosterUrl() );
                movieDetailIntent.putExtra( "backdropUrl", movie.getBackdropUrl() );
                movieDetailIntent.putExtra( "plotSynopsis", movie.getPlotSynopsis() );
                movieDetailIntent.putExtra( "popularity", String.valueOf( movie.getPopularity() ) );
                movieDetailIntent.putExtra( "releaseDate", movie.getReleaseDate() );
                movieDetailIntent.putExtra( "userRating", String.valueOf( movie.getVoteAverage() ) );
                movieDetailIntent.putExtra( "title", movie.getTitle() );

                BusProvider.getInstance().post( movieDetailIntent );
            }

            mImageAdapter.addMovie( movie );
            i++;
        }
    }

    @Override
    protected String doInBackground( String... params ) {

        if ( intertoobsNoWorky() ) {
            BusProvider.getInstance().post( new ShowDialog() );

            while ( intertoobsNoWorky() ) {
                try { Thread.sleep( 1000 ); } catch ( InterruptedException e ) { e.printStackTrace(); }
            }

            try { Thread.sleep( 4000 ); } catch ( InterruptedException e ) { e.printStackTrace(); }
            BusProvider.getInstance().post( new HideDialog() );
        }

        final String DEVELOPER_KEY_KEY = "api_key";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme( "https" )
                .authority( "api.themoviedb.org" )
                .appendPath( "3" )
                .appendPath( "movie" )
                .appendPath( "top_rated" )
                .appendQueryParameter( DEVELOPER_KEY_KEY, mImageAdapter.getContext().getString( R.string.api_key ) );

        URL url = null;
        try                                 { url = new URL( builder.build().toString() ); }
        catch ( MalformedURLException e )   { e.printStackTrace(); }

        return downloadJson( url );
    }

    private  boolean intertoobsNoWorky() {

        ConnectivityManager manager =
                (ConnectivityManager) mImageAdapter.getContext().getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return ! ( activeNetwork != null && activeNetwork.isConnected() );
    }












    private String downloadJson( URL url ) {

        HttpURLConnection urlConnection = null;
        StringBuffer buffer = new StringBuffer();

        try {
            Log.d( TAG, "Connecting to " + url.toString() );
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream( urlConnection.getInputStream() );

            if ( inputStream == null ) return null;

            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line;

            while ( ( line = reader.readLine() ) != null ) {

                buffer.append( line + "\n" );
            }

        } catch ( IOException e ) {

            Log.d( TAG, "OH SHOT NBO WORKY" );
            e.printStackTrace();

        } finally {

            urlConnection.disconnect();
        }

        return buffer.toString();
    }

    public void setImageAdapter( ImageAdapter imageAdapter ) {

        mImageAdapter = imageAdapter;
    }
}

