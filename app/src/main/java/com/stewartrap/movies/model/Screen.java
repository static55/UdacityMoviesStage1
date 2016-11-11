package com.stewartrap.movies.model;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

public class Screen {

    private static Context mContext;
    private static Point mScreenSize;
    private static ArrayList<Integer> multiPaneSizes = new ArrayList<>();
    public static final String TAG = Screen.class.getSimpleName();


    static {
        multiPaneSizes.add( Configuration.SCREENLAYOUT_SIZE_LARGE );
        multiPaneSizes.add( Configuration.SCREENLAYOUT_SIZE_XLARGE );
    }

    private static void loadCurrentDisplay() {

//        Log.d( TAG, "mContext is " + mContext );
        WindowManager manager = (WindowManager) mContext.getSystemService( Context.WINDOW_SERVICE );
        Display display = manager.getDefaultDisplay();
        mScreenSize = new Point();
        display.getSize( mScreenSize );
    }

    public static int getWidth( Context context ) {

        mContext = context;
        loadCurrentDisplay();
        return mScreenSize.x;
    }

    public static int getHeight( Context context ) {

        mContext = context;
        loadCurrentDisplay();
        return mScreenSize.y;
    }

    public static boolean isMultiPane( Context context ) {

        int screenSize = context
                            .getResources()
                            .getConfiguration()
                            .screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        for ( int multiPaneSize : multiPaneSizes ) {

            if ( screenSize == multiPaneSize )
                return true;
        }

        return false;
    }

}
