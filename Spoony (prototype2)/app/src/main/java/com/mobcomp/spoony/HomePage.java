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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class HomePage extends AppCompatActivity {

    ImageButton start_button;
    ImageButton firebase_button;
    GameDetails gd;

    AnimationDrawable catAnimation;
    ImageView shadow;
    ImageView phone;
    ImageView start_btn_bkg;
    TextView startTxt;
    TextView spoonyTxt;
    Animation startMsg_fadein;
    Animation startMsg_fadeout;
    ConstraintLayout egQuestion;
    Animation egQuestion_fadein;
    Animation egQuestion_fadeout;
    ConstraintLayout egRotate;
    Animation egRotate_fadein;
    Animation egRotate_fadeout;
    Animation cloudMove1;
    Animation cloudMove2;
    Animation rotate;
    Animation blink;

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

        start_button = (ImageButton)findViewById(R.id.btn_start);
        firebase_button = (ImageButton)findViewById(R.id.btn_add_question);

        start_button.setOnClickListener(this::jumpToStart);
        firebase_button.setOnClickListener(this::jumpToFirebase);

        ImageView catView = (ImageView) findViewById(R.id.cat_anim);
        catView.setBackgroundResource(R.drawable.cat_animation);
        catAnimation = (AnimationDrawable) catView.getBackground();


        startMsg_fadein = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        egQuestion_fadein = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        egRotate_fadein = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);

        startMsg_fadeout = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
        egQuestion_fadeout = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
        egRotate_fadeout = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);

        rotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_phone);
        blink = AnimationUtils.loadAnimation(this,R.anim.anim_blink);

        egQuestion = (ConstraintLayout) findViewById(R.id.eg_question_layout);
        egRotate = (ConstraintLayout) findViewById(R.id.eg_rotate_layout);
        phone = (ImageView) findViewById(R.id.eg_phone_anim);
        startTxt = (TextView) findViewById(R.id.start_text);
        spoonyTxt = (TextView) findViewById(R.id.spoony_text);

        cloudMove1 = AnimationUtils.loadAnimation(this, R.anim.anim_cloud_move1);
        cloudMove2 = AnimationUtils.loadAnimation(this, R.anim.anim_cloud_move2);
        cloudMove1.setRepeatCount(Animation.INFINITE);
        cloudMove2.setRepeatCount(Animation.INFINITE);

        shadow = (ImageView) findViewById(R.id.splash_black_circle);

        start_btn_bkg = (ImageView) findViewById(R.id.start_btn_bkg);



        startMsg_fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                startMsg_fadein.setStartOffset(8000);
                startTxt.startAnimation(startMsg_fadein);
                spoonyTxt.startAnimation(startMsg_fadein);

            }
        });

        startMsg_fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                startMsg_fadeout.setStartOffset(2000);
                startTxt.startAnimation(startMsg_fadeout);
                spoonyTxt.startAnimation(startMsg_fadeout);
            }
        });

        egQuestion_fadein.setStartOffset(4000);
        egQuestion_fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                egQuestion_fadein.setStartOffset(8000);
                egQuestion.startAnimation(egQuestion_fadein);

            }
        });

        egQuestion_fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                egQuestion_fadeout.setStartOffset(2000);
                egQuestion.startAnimation(egQuestion_fadeout);
            }
        });

        egRotate_fadein.setStartOffset(8000);
        egRotate_fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                egRotate_fadein.setStartOffset(8050);
                egRotate.startAnimation(egRotate_fadein);

            }
        });


        egRotate_fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                egRotate_fadeout.setStartOffset(2000);
                egRotate.startAnimation(egRotate_fadeout);
            }
        });

        gd = new GameDetails();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        catAnimation.start();
        startTxt.startAnimation(startMsg_fadein);
        start_btn_bkg.startAnimation(blink);
        spoonyTxt.startAnimation(startMsg_fadein);
        egQuestion.startAnimation(egQuestion_fadein);
        egRotate.startAnimation(egRotate_fadein);
        phone.startAnimation(rotate);
        shadow.startAnimation(cloudMove1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        catAnimation.stop();
        shadow.clearAnimation();
        startTxt.clearAnimation();
        start_btn_bkg.clearAnimation();
        spoonyTxt.clearAnimation();
        egQuestion.clearAnimation();
        egRotate.clearAnimation();
        phone.clearAnimation();
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
