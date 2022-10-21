package com.mobcomp.spoony;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;

public class WhichAnswer extends GameActivity {

    private GameDetails gd;
    private TextView result_txt_leadName;
    private TextView result_txt_question;
    private TextView result_txt_outcome;
    private TextView result_txt_qguessed;
    private TextView result_txt_leadScore;
    private TextView result_txt_followScore;
    private Button result_btn_home;
    private Button result_btn_playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent intent = getIntent();
        gd = (GameDetails) intent.getSerializableExtra("GameDetails");

        // Construct view:
        result_txt_leadName = findViewById(R.id.result_txt_leadName);
        result_txt_question = findViewById(R.id.result_txt_question);
        result_txt_outcome = findViewById(R.id.result_txt_outcome);
        result_txt_qguessed = findViewById(R.id.result_txt_qguessed);
        result_txt_leadScore = findViewById(R.id.result_txt_leadScore);
        result_txt_followScore = findViewById(R.id.result_txt_followScore);
        result_btn_home = findViewById(R.id.result_btn_home);
        result_btn_playAgain = findViewById(R.id.result_btn_playAgain);

        result_txt_leadName.setText(gd.getLead().getName());
        result_txt_question.setText(gd.getCurrentString());
        result_txt_qguessed.setText(gd.getGuessedString());

        // Assess if question was answered correctly, and increment scores:
        if (gd.getGuessedString().equals(gd.getCurrentString())) {
            gd.getFollow().incrementScore();
            result_txt_outcome.setText("YOU GOT IT!");
        } else {
            result_txt_outcome.setText("MAYBE NEXT TIME");
        }

        // Display scores:
        result_txt_leadScore.setText(gd.getLead().getName() + ": " + gd.getLead().getScore());
        result_txt_followScore.setText(gd.getFollow().getName() + ": " + gd.getFollow().getScore());

        // Finalise view build:
        result_btn_home = findViewById(R.id.result_btn_home);
        result_btn_playAgain = findViewById(R.id.result_btn_playAgain);
        View.OnClickListener jumpToHome = view -> jumpToHome();
        View.OnClickListener playAgain = view -> playAgain();
        result_btn_home.setOnClickListener(jumpToHome);
        result_btn_playAgain.setOnClickListener(playAgain);

    }

    private void jumpToHome() {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);
    }

    private void playAgain() {
        gd.nextRound();
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);
    }
}
