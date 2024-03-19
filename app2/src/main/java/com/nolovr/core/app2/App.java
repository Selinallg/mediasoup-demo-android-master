package com.nolovr.core.app2;

import android.app.Application;

import com.nolovr.core.mc.MediaCore;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MediaCore.getInstance(this).init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MediaCore.getInstance(this).release();
    }
}
