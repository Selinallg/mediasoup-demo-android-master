package org.mediasoup.droid.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nolovr.core.mediartc.RoomService;
import com.nolovr.core.mediartc.lib.UrlFactory;

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

    public void goRoomA(View view) {
        UrlFactory.HOSTNAME = "82.157.172.119";
        UrlFactory.PORT_WSS = 4443;
        Toast.makeText(this, UrlFactory.HOSTNAME + ":" + UrlFactory.PORT_WSS, Toast.LENGTH_LONG).show();
    }

    public void goRoomB(View view) {
        UrlFactory.HOSTNAME = "8.147.104.161";
//        UrlFactory.PORT = 3000;
        UrlFactory.PORT_WSS = 4443;
        Toast.makeText(this, UrlFactory.HOSTNAME + ":" + UrlFactory.PORT_WSS, Toast.LENGTH_LONG).show();
    }
}