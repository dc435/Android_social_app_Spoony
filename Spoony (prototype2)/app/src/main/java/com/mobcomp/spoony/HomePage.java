package com.mobcomp.spoony;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomePage extends AppCompatActivity {

    private Button btnStart;
    private GameDetails gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        setContentView(R.layout.activity_home_page);

        btnStart = findViewById(R.id.btnStart);

        setDefaultStatus();

        btnStart.setOnClickListener(view -> {
            this.goToWhichQuestion();
        });

    }

    private void setDefaultStatus() {

        gd = new GameDetails();
        gd.P1_NAME = "Bobby";
        gd.P2_NAME = "Anna";
        gd.QUESTIONS[0] = "Who is more a cat or dog person?";
        gd.QUESTIONS[1] = "How adventurous are you?";
        gd.QUESTIONS[2] = "Are you a gamer?";
        gd.QTN_INDEX = 0;
        gd.P1_COLOR = getResources().getColor(R.color.p1_color);
        gd.P2_COLOR = getResources().getColor(R.color.p2_color);

    }

    private void goToWhichQuestion() {
        Intent intent = new Intent(this, WhichQuestion.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);
    }

}
