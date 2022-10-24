package com.mobcomp.spoony;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
            changeActivity(Name.class, gameDetails);
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
            gameDetails.getFollow().setDirection(zOrientation);
            changeActivity(QuestionActivity.class, gameDetails);
        }
    }
}
