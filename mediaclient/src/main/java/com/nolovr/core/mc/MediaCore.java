package com.nolovr.core.mc;

import android.content.Context;
import android.util.Log;

import org.mediasoup.droid.Logger;
import org.mediasoup.droid.MediasoupClient;


public class MediaCore extends MediaBase{

    private static final String TAG = "MediaCore";

    private static MediaCore invokeSingleTon = null;


    private MediaCore(Context context) {
        super(context);
        pContext = context;
        String FLAG = context.getPackageName() + ":" + TAG;
        Log.d(TAG, "TerminalClient: " + FLAG);
        Log.d(TAG, "TerminalClient: expCallbackMap:" + this.hashCode());
    }

    public static MediaCore getInstance(Context context) {
        synchronized (MediaCore.class) {
            if (invokeSingleTon == null) {
                invokeSingleTon = new MediaCore(context);
            }
        }
        return invokeSingleTon;
    }

    public void init() {
        super.init();
        Logger.setLogLevel(Logger.LogLevel.LOG_DEBUG);
        Logger.setDefaultHandler();
        MediasoupClient.initialize(pContext.getApplicationContext());
    }

    public void release() {
        super.release();
        Log.d(TAG, "release: ");
    }

    //--------------------------------aidl接口调用--------------------------------




}
