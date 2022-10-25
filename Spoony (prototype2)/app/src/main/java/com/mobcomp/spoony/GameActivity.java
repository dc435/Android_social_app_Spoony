package com.mobcomp.spoony;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton backBtn;
    AudioService audioService;
    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AudioService.AudioBinder binder = (AudioService.AudioBinder) service;
            audioService = binder.getService();
            if (!audioService.getIsPlaying()) {
                audioService.startGame();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {}
    };

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

    protected void showHomeDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage("Are you sure to exit game?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("YES", (dialogInterface, i) -> goBackHome());
        dialog.setNegativeButton("NOT REALLY", (dialogInterface, i) -> {});
        dialog.show();
    }

    protected void goBackHome() {
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void commonBtnSetup() {
        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        homeBtn.setOnClickListener((View v) -> showHomeDialog());
        backBtn = (ImageButton) findViewById(R.id.back_btn);
        backBtn.setOnClickListener((View v) -> onBackPressed());
    }

    public GameDetails getGameDetails() {
        Intent intent = getIntent();
        return (GameDetails) intent.getSerializableExtra("GameDetails");
    }

    public void changeActivity(Class<?> cls, GameDetails gameDetails) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("GameDetails", gameDetails);
        startActivity(intent);
    }
}