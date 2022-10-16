package com.mobcomp.spoony;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class PToP extends SpoonyActivity {

    private final int POINT_TO_PLR_LAYOUT = R.layout.ptp;
    private final int FLAT_PROMPT_LAYOUT = R.layout.down;
    private int currentLayout;

    private ImageButton confirmBtn;
    private ImageButton backBtn;
    private ImageButton homeBtn;
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

        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("GameDetails");
    }

    @Override
    public void onBackPressed() {
        if(p1Located) {
            p1Located = false;
            nameSetup();
        } else {
            Intent intent = new Intent(this, Name.class);
            intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void onEnterTable() {
        currentLayout = POINT_TO_PLR_LAYOUT;
        setContentView(currentLayout);
        nameSetup();
        exclusiveBtnSetup();
    }

    @Override
    protected void updateTable() {

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
            if (!p1Located) {
                nameView.setText(getGameDetails().getLead().getName());
                // hardcode the color for now
                nameView.setTextColor(ContextCompat.getColor(this, R.color.p1_color));
//                nameView.setTextColor(getGameDetails().getLead().getColour());
            } else {
                nameView.setText(getGameDetails().getFollow().getName());
                // hardcode the color for now
                nameView.setTextColor(ContextCompat.getColor(this, R.color.p2_color));
//                nameView.setTextColor(getGameDetails().getFollow().getColour());
            }
        }
    }

    private void exclusiveBtnSetup() {
        confirmBtn = (ImageButton) findViewById(R.id.lock_button);
        confirmBtn.setOnClickListener((View v) -> onConfirmPressed());
        commonBtnSetup();
    }

    private void commonBtnSetup() {
        backBtn = (ImageButton) findViewById(R.id.back_btn);
        backBtn.setOnClickListener((View v) -> onBackPressed());
        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        homeBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, HomePage.class);
            intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void onConfirmPressed() {
        float zOrientation = deviceOrientation[0];
        Log.d("Orientation Prompt", String.valueOf(zOrientation));
        if (!p1Located) {
            getGameDetails().getLead().setDirection(zOrientation);
            p1Located = true;
            nameSetup();
        } else {
            getGameDetails().getFollow().setDirection(zOrientation);
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("GameDetails", gameDetails);
            startActivity(intent);
        }
    }

}
