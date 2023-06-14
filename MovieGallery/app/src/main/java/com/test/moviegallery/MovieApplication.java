package com.test.moviegallery;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MovieApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
