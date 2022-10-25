package com.mobcomp.spoony;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;

public class PToP extends SpoonyActivity {

    private final int POINT_TO_PLR_LAYOUT = R.layout.ptp;
    private final int FLAT_PROMPT_LAYOUT = R.layout.down;
    private int currentLayout;

    private ImageButton confirmBtn;
    private ImageView innerCompass;
    private TextView nameView;

    private boolean p1Located;

    private GameDetails gameDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLayout = FLAT_PROMPT_LAYOUT;
        setContentView(currentLayout);
        commonBtnSetup();

        p1Located = false;
        gameDetails = getGameDetails();
    }

    @Override
    public void onBackPressed() {
        if(p1Located) {
            p1Located = false;
            nameSetup();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onEnterTable() {
        currentLayout = POINT_TO_PLR_LAYOUT;
        setContentView(currentLayout);
        nameSetup();
        btnSetup();
        compassSetup();
    }

    @Override
    protected void updateTable() {
        if(p1Located && currentLayout == POINT_TO_PLR_LAYOUT) {
            float offset = Angle.rotationDistanceSigned(
                    deviceOrientation[0],
                    gameDetails.getLead().getDirection());
            innerCompass.setRotation(offset);
        }
    }

    @Override
    protected void onExitTable() {
        currentLayout = FLAT_PROMPT_LAYOUT;
        setContentView(currentLayout);
        commonBtnSetup();
    }

    private void nameSetup() {
        if (currentLayout == POINT_TO_PLR_LAYOUT) {
            nameView = (TextView) findViewById(R.id.entry_name_p1);
            Player player = p1Located ? gameDetails.getFollow() : gameDetails.getLead();
            nameView.setText(player.getName());
            nameView.setTextColor(player.getColour());
        }
    }

    private void btnSetup() {
        if (currentLayout == POINT_TO_PLR_LAYOUT) {
            confirmBtn = (ImageButton) findViewById(R.id.lock_button);
            confirmBtn.setOnClickListener((View v) -> onConfirmPressed());
            commonBtnSetup();
        }
    }

    private void compassSetup() {
        if (currentLayout == POINT_TO_PLR_LAYOUT)
            innerCompass = (ImageView) findViewById(R.id.imageView_compass_inner_circle);
    }

    private void onConfirmPressed() {
        float zOrientation = deviceOrientation[0];
        if (!p1Located) {
            gameDetails.getLead().setDirection(zOrientation);
            p1Located = true;
            nameSetup();
        } else {
            float offset = Math.abs(zOrientation - gameDetails.getLead().getDirection());
            if (offset > 135 && offset < 215) {
                gameDetails.getFollow().setDirection(zOrientation);
                changeActivity(QuestionActivity.class, gameDetails);
            } else {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_refuse_shake);
                confirmBtn.startAnimation(shake);
                String prompt = "Please sit opposite to " + gameDetails.getLead().getName();
                Toast toast = Toast.makeText(this, prompt, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}