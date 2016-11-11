package com.stewartrap.movies.unused;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.lang.ref.WeakReference;

class LoadBitmapTask extends AsyncTask<Void, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private Context mContext;

    public void setContext( Context context ) { mContext = context; }

    public LoadBitmapTask( ImageView imageView ) {

        imageViewReference = new WeakReference<ImageView>( imageView );
    }

    @Override
    protected Bitmap doInBackground( Void... params ) {
//        data = params[0];
//        return decodeSampledBitmapFromResource( getResources(), data, 100, 100 ));
        Bitmap bitmap = null;
        try {

            FileInputStream inputStream = mContext.openFileInput( "poster.png" );
            bitmap = BitmapFactory.decodeStream( inputStream );
            inputStream.close();

        } catch ( Exception e ) { e.printStackTrace(); }

        return bitmap;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}