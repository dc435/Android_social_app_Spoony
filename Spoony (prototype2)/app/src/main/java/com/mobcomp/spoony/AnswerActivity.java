package com.mobcomp.spoony;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AnswerActivity extends SpoonyActivity {

    private GameDetails gameDetails;

    private int answerDisplay;
    private int putDownDisplay;

    private ConstraintLayout spinner;
    private GradientDrawable gradient;

    private int leadPercent;
    private TextView leadPercentText;
    private TextView followPercentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ansq);

        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("GameDetails");

        answerDisplay = R.layout.ansq;
        putDownDisplay = R.layout.down;

        displayPutDownScreen();
    }

    @Override
    protected void onEnterTable() {
        displaySpinnerScreen();
    }

    @Override
    protected void onExitTable() {
        displayPutDownScreen();
    }

    @Override
    protected void updateTable() {
        spinner.setRotation(Angle.rotationDistanceSigned(deviceOrientation[0], gameDetails.getLead().getDirection()));

        leadPercent = (int) (Angle.percentageBetween(deviceOrientation[0], gameDetails.getLead().getDirection(), gameDetails.getFollow().getDirection()) * 100);

        if (leadPercentText != null) {
            leadPercentText.setText(String.valueOf(leadPercent));
        }

        if (leadPercentText != null) {
            leadPercentText.setText(String.valueOf(100 - leadPercent));
        }
    }

    private void displaySpinnerScreen() {
        setContentView(answerDisplay);

        ImageButton lockInButton = findViewById(R.id.lock_button);
        lockInButton.setOnClickListener(this::lockInAnswer);

        TextView leadText = findViewById(R.id.answer_lead_text);
        TextView followText = findViewById(R.id.answer_follow_text);

        spinner = findViewById(R.id.answer_spinner_layout);

        leadText.setTextColor(gameDetails.getLead().getColour());
        leadText.setText(gameDetails.getLead().getName());

        followText.setTextColor(gameDetails.getFollow().getColour());
        followText.setText(gameDetails.getFollow().getName());

        leadPercentText = findViewById(R.id.answer_lead_pct);
        followPercentText = findViewById(R.id.answer_follow_pct);
    }

    private void displayPutDownScreen() {
        setContentView(putDownDisplay);
    }

    private void lockInAnswer(View view) {
        // TODO: what are we doing with the answer?
        Intent intent = new Intent(this, WhichQuestion.class);
        intent.putExtra("GameDetails", gameDetails);
        startActivity(intent);
    }
}
