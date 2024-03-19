package com.nolovr.core.mediartc.base;

import android.app.Service;
import android.content.Intent;

public abstract class HatchService extends Service {

    public HatchService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        faultTolerantHandle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 容错处理
     */
    public abstract void faultTolerantHandle();



}