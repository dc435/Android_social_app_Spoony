package com.mobcomp.spoony;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    ImageView cloud_blue_upLeft;
    ImageView cloud_blue_upRight;
    ImageView cloud_blue_downLeft;
    ImageView cloud_blue_downRight;
    ImageView cloud_white;
    ImageView cloud_watercolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // configure our window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);

        cloud_blue_upLeft = findViewById(R.id.cover_cloud_upLeft);
        cloud_blue_upRight = findViewById(R.id.cover_cloud_upRight);
        cloud_blue_downLeft = findViewById(R.id.cover_cloud_downLeft);
        cloud_blue_downRight = findViewById(R.id.cover_cloud_downRight);
        cloud_white = findViewById(R.id.cover_cloud_white);
        cloud_watercolor =findViewById(R.id.cover_cloud_watercolor);

        Animation anim_cloud_upLeft = AnimationUtils.loadAnimation(this,R.anim.anim_cloud_upleft);
        Animation anim_cloud_upRight = AnimationUtils.loadAnimation(this,R.anim.anim_cloud_upright);
        Animation anim_cloud_downLeft = AnimationUtils.loadAnimation(this,R.anim.anim_cloud_downleft);
        Animation anim_cloud_downRight = AnimationUtils.loadAnimation(this,R.anim.anim_cloud_downright);
        Animation anim_cloud_white = AnimationUtils.loadAnimation(this,R.anim.anim_cloud_white);
        Animation anim_cloud_watercolor = AnimationUtils.loadAnimation(this,R.anim.anim_cloud_watercolor);

        cloud_blue_upLeft.startAnimation(anim_cloud_upLeft);
        cloud_blue_upRight.startAnimation(anim_cloud_upRight);
        cloud_blue_downLeft.startAnimation(anim_cloud_downLeft);
        cloud_blue_downRight.startAnimation(anim_cloud_downRight);
        cloud_white.startAnimation(anim_cloud_white);
        cloud_watercolor.startAnimation(anim_cloud_watercolor);

        // call handler to run a task for specific time interval
        new Handler().postDelayed(() -> {
            // creating a new intent
            Intent i = new Intent(Splash.this, HomePage.class);
            startActivity(i);

            // finish our current activity.
            finish();
        }, 3800);

    }
}
