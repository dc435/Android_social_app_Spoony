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
    ImageView[] clouds;
    ImageView phone;
    TextView startMsg;
    ConstraintLayout egQuestion;
    ConstraintLayout egRotate;
    Animation cloudMove1;
    Animation cloudMove2;
    Animation fadein;
    Animation fadein2;
    Animation fadein3;
    Animation fadeout;
    Animation fadeout2;
    Animation fadeout3;
    Animation rotate;

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


        fadein = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        fadein2 = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        fadein3 = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
        fadeout2 = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
        fadeout3 = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
        rotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
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

        egQuestion = (ConstraintLayout) findViewById(R.id.eg_question_layout);
        egRotate = (ConstraintLayout) findViewById(R.id.eg_rotate_layout);
        phone = (ImageView) findViewById(R.id.eg_phone_anim);
        startMsg = (TextView) findViewById(R.id.startGame_text);


        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadein.setStartOffset(8000);
                startMsg.startAnimation(fadein);

            }
        });

        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadeout.setStartOffset(2000);
                startMsg.startAnimation(fadeout);
            }
        });

        fadein2.setStartOffset(4000);
        fadeout2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub


            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadein2.setStartOffset(8000);
                egQuestion.startAnimation(fadein2);

            }
        });

        fadein2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadeout2.setStartOffset(2000);
                egQuestion.startAnimation(fadeout2);
            }
        });

        fadein3.setStartOffset(8000);
        fadeout3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadein3.setStartOffset(8000);
                egRotate.startAnimation(fadein3);

            }
        });


        fadein3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadeout3.setStartOffset(2000);
                egRotate.startAnimation(fadeout3);
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
        startMsg.startAnimation(fadein);
        egQuestion.startAnimation(fadein2);
        egRotate.startAnimation(fadein3);
        phone.startAnimation(rotate);
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
        startMsg.clearAnimation();
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
