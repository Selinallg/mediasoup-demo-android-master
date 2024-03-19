package org.mediasoup.droid.demo;

import com.nolovr.core.mc.MediaCore;

public class Application extends android.app.Application {

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
