package com.nolovr.core.mc;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.nolovr.core.mediartc.IMediaCore;

/**
 * 远程service绑定
 */
public class MediaBase {

    private static final String TAG = "MediaBase";
    private static String PACKAGE_NAME;
    protected Context pContext;
    protected IMediaCore pIMediaCore;

    protected MediaBase(Context context) {
        pContext = context;
        PACKAGE_NAME = pContext.getPackageName();
        Log.d(TAG, "MediaBase: ");
    }

    public void init() {
        Log.d(TAG, "init: ");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_BIND_MEDIA_SERVICE);
        filter.addAction(Constants.ACTION_EXIT_MEDIA_SERVICE);
        pContext.registerReceiver(mbinderReceiver, filter);
    }

    public void release() {
        Log.d(TAG, "release: ");
        pContext.unregisterReceiver(mbinderReceiver);
    }


    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, Thread.currentThread().getId() + "");
            Log.d(TAG, "onServiceConnected: ");
            pIMediaCore = IMediaCore.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");

        }
    };


    protected void doBind() {
        Intent intent = new Intent();
        intent.setClassName(PACKAGE_NAME,
                "com.nolovr.core.space.service.MediaService");
        boolean ret = pContext.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "init: bindService=" + ret);
    }


    protected void unBind() {
        try {
            if (pIMediaCore != null) {
            }
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e(TAG, "unBind: media_client throw execption");
        }
        try {
            pContext.unbindService(conn);
        } catch (Exception e) {
            // e.printStackTrace();
            Log.e(TAG, "unBind: media_client unbindService throw execption");
        }
        Log.d(TAG, "media_client unbind Success!!!");
    }


    protected final BroadcastReceiver mbinderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "CONTROLLER_SERVICE receive action: " + action);
            if (Constants.ACTION_BIND_MEDIA_SERVICE.equals(action)) {
                unBind();
            } else if (Constants.ACTION_EXIT_MEDIA_SERVICE.equals(action)) {
                doBind();
            }
        }
    };
}
