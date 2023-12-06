package com.app.ivans.ghimli.utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.lifecycle.LiveData;

public class InternetUtil extends LiveData<Boolean> {

    private BroadcastReceiver broadcastReceiver;
    private static Application application;

    public static void init(Application app) {
        application = app;
    }

    public static boolean isInternetOn() {
        ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onActive() {
        registerBroadCastReceiver();
    }

    @Override
    protected void onInactive() {
        unRegisterBroadCastReceiver();
    }

    private void registerBroadCastReceiver() {
        if (broadcastReceiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = extras != null ? extras.getParcelable("networkInfo") : null;
                    setValue(info != null && info.getState() == NetworkInfo.State.CONNECTED);
                }
            };

            application.registerReceiver(broadcastReceiver, filter);
        }
    }

    private void unRegisterBroadCastReceiver() {
        if (broadcastReceiver != null) {
            application.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }
}