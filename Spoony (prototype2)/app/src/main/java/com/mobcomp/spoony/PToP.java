package com.mobcomp.spoony;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PToP extends SpoonyActivity {

    private int POINT_TO_PLR_LAYOUT = R.layout.activity_point_to_p1;
    private int FALT_PROMPT_LAYOUT = R.layout.place_flat_flat;
    private int currentLayout;

    private Button confirmBtn;
    private TextView nameView;

    private boolean p1Located;

    private GameDetails gameDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLayout = FALT_PROMPT_LAYOUT;
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
        currentLayout = FALT_PROMPT_LAYOUT;
        setContentView(currentLayout);
    }

    private void nameSetup() {
        if (currentLayout == POINT_TO_PLR_LAYOUT) {
            nameView = (TextView) findViewById(R.id.entry_name_p1);
            if (!p1Located) {
                nameView.setText(getGameDetails().getLead().getName());
            } else {
                nameView.setText(getGameDetails().getFollow().getName());
            }
        }
    }

    private void buttonSetup() {
        confirmBtn = (Button) findViewById(R.id.answer_lock_button);
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
