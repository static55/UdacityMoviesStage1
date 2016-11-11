package com.stewartrap.movies.unused;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveBitmapTask extends AsyncTask<Void, Void, Void> {

    public final String TAG = SaveBitmapTask.class.getSimpleName();
    private Context mContext;
    private Bitmap mBitmap;

    public void setContext( Context context ) { mContext = context; }
    public void setBitmap( Bitmap bitmap ) { mBitmap = Bitmap.createBitmap( bitmap ); }

    @Override
    protected Void doInBackground( Void... params ) {

        try {
            FileInputStream inputStream = mContext.openFileInput("poster.png");


                Log.d(TAG, "file exists");
//                myFile.delete();
                mContext.deleteFile("poster.png");


        } catch ( FileNotFoundException e ) { Log.d( TAG, "file no exist " ); }

        try {

            FileOutputStream outputStream = mContext.openFileOutput( "poster.png", Context.MODE_PRIVATE );
            mBitmap.compress( Bitmap.CompressFormat.PNG, 100, outputStream );
            outputStream.close();
            mBitmap.recycle();

        } catch ( IOException e ) { e.printStackTrace(); }


        return null;
    }
}