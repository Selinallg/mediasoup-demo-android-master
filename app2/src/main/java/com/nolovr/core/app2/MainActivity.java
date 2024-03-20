package com.nolovr.core.app2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.nolovr.core.mediartc.RoomService;

public class MainActivity extends Activity {

    // TODO: 2024/3/20 camera 授权

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startService(View view) {
        startService(new Intent(this, RoomService.class));
    }


    public void stopService(View view) {
        stopService(new Intent(this, RoomService.class));
    }


    public void goSettings(View view) {
       // startActivity(new Intent(this, SettingsActivity.class));
    }



//    private PermissionHandler permissionHandler =
//            new PermissionHandler() {
//                @Override
//                public void onGranted() {
//                    Logger.d(TAG, "permission granted");
//                    if (mRoomClient != null) {
//                        mRoomClient.join();
//                    }
//                }
//            };

//    private void checkPermission() {
//        String[] permissions = {
//                android.Manifest.permission.INTERNET,
//                android.Manifest.permission.RECORD_AUDIO,
//                android.Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//        String rationale = "Please provide permissions";
//        Permissions.Options options =
//                new Permissions.Options().setRationaleDialogTitle("Info").setSettingsDialogTitle("Warning");
//        Permissions.check(this, permissions, rationale, options, permissionHandler);
//    }
}