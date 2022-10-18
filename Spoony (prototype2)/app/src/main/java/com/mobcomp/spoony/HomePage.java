package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.LinkedList;

public class HomePage extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "hello...";
    ImageButton setting_button;
    ImageButton start_button;
    ImageButton firebase_button;
    GameDetails gd;

    AudioService audioService;
    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AudioService.AudioBinder binder = (AudioService.AudioBinder) service;
            audioService = binder.getService();
            audioService.startHome();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setContentView(R.layout.splash);
        setContentView(R.layout.home);

        setting_button = (ImageButton)findViewById(R.id.btnSetting);
        start_button = (ImageButton)findViewById(R.id.btnStart);
        setting_button = (ImageButton)findViewById(R.id.btnSetting);
        firebase_button = (ImageButton)findViewById(R.id.btnLibrary);

        setting_button.setOnClickListener(this::jumpToSetting);
        start_button.setOnClickListener(this::jumpToStart);
        setting_button.setOnClickListener(this::jumpToSetting);
        firebase_button.setOnClickListener(this::jumpToFirebase);

        gd = new GameDetails();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    /**
     * Called when the user click the button
     */
    public void jumpToSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    public void jumpToStart(View view) {
        audioService.startGame();
        Intent intent = new Intent(this, Name.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);
    }

    public void jumpToFirebase(View view) {
        Intent intent = new Intent(this, FirebaseActivity.class);
        startActivity(intent);
    }
}
