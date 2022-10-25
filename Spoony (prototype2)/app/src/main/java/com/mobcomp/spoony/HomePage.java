package com.mobcomp.spoony;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    ImageButton start_button;
    ImageButton firebase_button;
    GameDetails gd;

    AnimationDrawable catAnimation;
    ImageView[] clouds;
    Animation cloudMove1;
    Animation cloudMove2;

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

        setContentView(R.layout.home);

        start_button = (ImageButton)findViewById(R.id.btnStart);
        firebase_button = (ImageButton)findViewById(R.id.btnLibrary);

        start_button.setOnClickListener(this::jumpToStart);
        firebase_button.setOnClickListener(this::jumpToFirebase);

        ImageView catView = (ImageView) findViewById(R.id.cat_anim);
        catView.setBackgroundResource(R.drawable.cat_animation);
        catAnimation = (AnimationDrawable) catView.getBackground();

        cloudMove1 = AnimationUtils.loadAnimation(this, R.anim.anim_cloud_move1);
        cloudMove2 = AnimationUtils.loadAnimation(this, R.anim.anim_cloud_move2);
        cloudMove1.setRepeatCount(Animation.INFINITE);
        cloudMove2.setRepeatCount(Animation.INFINITE);
        clouds = new ImageView[5];
        clouds[0] = (ImageView) findViewById(R.id.imageView4);
        clouds[1] = (ImageView) findViewById(R.id.imageView2);
        clouds[2] = (ImageView) findViewById(R.id.imageView7);
        clouds[3] = (ImageView) findViewById(R.id.imageView3);
        clouds[4] = (ImageView) findViewById(R.id.imageView);

        gd = new GameDetails();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        catAnimation.start();
        clouds[0].startAnimation(cloudMove1);
        clouds[1].startAnimation(cloudMove2);
        clouds[2].startAnimation(cloudMove2);
        clouds[3].startAnimation(cloudMove1);
        clouds[4].startAnimation(cloudMove2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        catAnimation.stop();
        for (ImageView v: clouds) {
            v.clearAnimation();
        }
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
