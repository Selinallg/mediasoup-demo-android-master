package org.mediasoup.droid.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nolovr.core.mediartc.RoomService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goRoom(View view) {
        startActivity(new Intent(this, RoomActivity.class));
    }

    public void startService(View view) {
        startService(new Intent(this, RoomService.class));
    }


    public void stopService(View view) {
        stopService(new Intent(this, RoomService.class));
    }

    public void goSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}