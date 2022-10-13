package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends SpoonyActivity {

    private GameDetails gameDetails;

    private int answerDisplay;
    private int putDownDisplay;

    private ConstraintLayout spinner;
    private GradientDrawable gradient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("GameDetails");

        answerDisplay = R.layout.activity_answer;
        putDownDisplay = R.layout.place_flat_flat;

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
    }

    private void displaySpinnerScreen() {
        setContentView(answerDisplay);

        Button lockInButton = findViewById(R.id.answer_lock_button);
        lockInButton.setOnClickListener(this::lockInAnswer);

        TextView leadText = findViewById(R.id.answer_lead_text);
        TextView followText = findViewById(R.id.answer_follow_text);

        spinner = findViewById(R.id.answer_spinner_layout);

        leadText.setTextColor(gameDetails.getLead().getColour());
        leadText.setText(gameDetails.getLead().getName());

        followText.setTextColor(gameDetails.getFollow().getColour());
        followText.setText(gameDetails.getFollow().getName());
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
