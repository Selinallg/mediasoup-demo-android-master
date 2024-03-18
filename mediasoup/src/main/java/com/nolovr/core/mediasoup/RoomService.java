package com.nolovr.core.mediasoup;


import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.nolovr.core.mediasoup.lib.PeerConnectionUtils;
import com.nolovr.core.mediasoup.lib.RoomClient;
import com.nolovr.core.mediasoup.lib.RoomOptions;
import com.nolovr.core.mediasoup.lib.Utils;
import com.nolovr.core.mediasoup.lib.lv.RoomStore;

import org.mediasoup.droid.Logger;


public class RoomService extends Service {

    private static final String TAG = RoomService.class.getSimpleName();
    private static final int REQUEST_CODE_SETTING = 1;

    private String mRoomId, mPeerId, mDisplayName;
    private boolean mForceH264, mForceVP9;

    private RoomOptions mOptions;
    private RoomStore mRoomStore;
    private RoomClient mRoomClient;


    @Override
    public void onCreate() {
        super.onCreate();
        createRoom();
        checkPermission();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand ");
        return START_NOT_STICKY;
    }

  // TODO: 2024/3/18 aidl 接口
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void createRoom() {
        mOptions = new RoomOptions();
        loadRoomConfig();

        mRoomStore = new RoomStore();
        initRoomClient();

    }

    private void loadRoomConfig() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Room initial config.
        mRoomId = preferences.getString("roomId", "");
        mPeerId = preferences.getString("peerId", "");
        mDisplayName = preferences.getString("displayName", "");
        mForceH264 = preferences.getBoolean("forceH264", false);
        mForceVP9 = preferences.getBoolean("forceVP9", false);
        if (TextUtils.isEmpty(mRoomId)) {
            //mRoomId = getRandomString(8);
            mRoomId = "broadcaster";
            preferences.edit().putString("roomId", mRoomId).apply();
        }
        if (TextUtils.isEmpty(mPeerId)) {
            mPeerId = Utils.getRandomString(8);
            preferences.edit().putString("peerId", mPeerId).apply();
        }
        if (TextUtils.isEmpty(mDisplayName)) {
            mDisplayName = Utils.getRandomString(8);
            preferences.edit().putString("displayName", mDisplayName).apply();
        }

        // Room action config.
        mOptions.setProduce(preferences.getBoolean("produce", true));
        mOptions.setConsume(preferences.getBoolean("consume", true));
        mOptions.setForceTcp(preferences.getBoolean("forceTcp", false));
        mOptions.setUseDataChannel(preferences.getBoolean("dataChannel", true));


        // Device config.
        // mic add by llg
        mOptions.setEnableMic(preferences.getBoolean("mic", false));
        // preview add by llg
        mOptions.setPreview(preferences.getBoolean("preview", false));

        String camera = preferences.getString("camera", "front");
        PeerConnectionUtils.setPreferCameraFace(camera);

        // Display version number.
    }

    private void initRoomClient() {
        mRoomClient =
                new RoomClient(
                        this, mRoomStore, mRoomId, mPeerId, mDisplayName, mForceH264, mForceVP9, mOptions);
    }


    private PermissionHandler permissionHandler =
            new PermissionHandler() {
                @Override
                public void onGranted() {
                    Logger.d(TAG, "permission granted");
                    if (mRoomClient != null) {
                        mRoomClient.join();
                    }
                }
            };

    private void checkPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        String rationale = "Please provide permissions";
        Permissions.Options options =
                new Permissions.Options().setRationaleDialogTitle("Info").setSettingsDialogTitle("Warning");
        Permissions.check(this, permissions, rationale, options, permissionHandler);
    }

    private void destroyRoom() {
        if (mRoomClient != null) {
            mRoomClient.close();
            mRoomClient = null;
        }
        if (mRoomStore != null) {
            mRoomStore = null;
        }
    }

//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.room_menu, menu);
//    return true;
//  }
//
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    // Handle item selection
//    if (item.getItemId() == R.id.setting) {
//      Intent intent = new Intent(this, SettingsActivity.class);
//      startActivityForResult(intent, REQUEST_CODE_SETTING);
//      return true;
//    } else {
//      return super.onOptionsItemSelected(item);
//    }
//  }
//
//  @Override
//  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//    if (requestCode == REQUEST_CODE_SETTING) {
//      Logger.d(TAG, "request config done");
//      // close, dispose room related and clear store.
//      destroyRoom();
//      // local config and reCreate room related.
//      createRoom();
//      // check permission again. if granted, join room.
//      checkPermission();
//    } else {
//      super.onActivityResult(requestCode, resultCode, data);
//    }
//  }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyRoom();
    }

}
