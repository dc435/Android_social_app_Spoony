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

    private int POINT_TO_PLR_LAYOUT = R.layout.ptp;
    private int FLAT_PROMPT_LAYOUT = R.layout.down;
    private int currentLayout;

    private ImageButton confirmBtn;
    private TextView nameView;

    private boolean p1Located;

    private GameDetails gameDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLayout = FLAT_PROMPT_LAYOUT;
        setContentView(currentLayout);

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
        buttonSetup();
    }

    @Override
    protected void onExitTable() {
        currentLayout = FLAT_PROMPT_LAYOUT;
        setContentView(currentLayout);
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

    private void buttonSetup() {
        confirmBtn = (ImageButton) findViewById(R.id.lock_button);
        confirmBtn.setOnClickListener((View v) -> {
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
        });
    }
}
