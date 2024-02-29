package org.mediasoup.droid.demo.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.mediasoup.droid.lib.PeerConnectionUtils;

public class ProJActivity extends AppCompatActivity {

    private static final String TAG = "_ProJActivity";
    public static Intent mMediaProjectionPermissionResultData;
    public static int mMediaProjectionPermissionResultCode;

    private static final int CAPTURE_PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startScreenCapture();
        } else {
            init();
        }
    }

    @TargetApi(21)
    private void startScreenCapture() {
        MediaProjectionManager mediaProjectionManager =
                (MediaProjectionManager) getApplication().getSystemService(
                        Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(), CAPTURE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode="+requestCode);
        if (requestCode != CAPTURE_PERMISSION_REQUEST_CODE) {
            return;
        }
        mMediaProjectionPermissionResultCode = resultCode;
        mMediaProjectionPermissionResultData = data;
        init();
    }

    public void init() {
        Log.d(TAG, "init: ");
        PeerConnectionUtils.setMediaProjectionIntent(mMediaProjectionPermissionResultData);
    }
}
