package com.stewartrap.movies.unused;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.otto.Subscribe;
import com.stewartrap.movies.bus.BusProvider;
import com.stewartrap.movies.bus.HideDialog;
import com.stewartrap.movies.bus.ShowDialog;
//
//public class NoInternetDialog {
//
//    private static ProgressDialog mDialog = null;
//    private static Context mContext;
//
//
//
//    public static void setContext( Context context ) {
//
//        mContext = context;
//    }
//
//
//
//    public static boolean isInternetAvailable() {
//
//        ConnectivityManager manager =
//                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
//        return activeNetwork != null && activeNetwork.isConnected();
//    }
//
//
//
//
//    public static void show() {
//
//
//        mDialog = ProgressDialog.show(
//                mContext,
//                "Unable to connect to Internet",
//                "Waiting for Internet connection ...",
//                true, false);
//
//    }
//
//
//}
