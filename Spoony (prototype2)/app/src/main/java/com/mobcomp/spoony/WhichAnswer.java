package com.mobcomp.spoony;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WhichAnswer extends AppCompatActivity {

    private GameDetails gd;
    private TextView txtLeadName;
    private TextView txtAnswer;
    private TextView txtOutcome;
    private Button btnHome;
    private Button btnPlayAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.which_answer);

        Intent intent = getIntent();
        gd = (GameDetails) intent.getSerializableExtra("GameDetails");

        txtLeadName = findViewById(R.id.txtLeadName);
        txtAnswer = findViewById(R.id.txtAnswer);
        txtOutcome = findViewById(R.id.txtOutcome);
        btnHome = findViewById(R.id.btnHome);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        txtLeadName.setText(gd.getLeadName());
        txtLeadName.setTextColor(gd.getLeadColor());
        txtAnswer.setText(gd.QUESTIONS[gd.QTN_INDEX]);

        if (gd.QTN_GUESS==gd.QTN_INDEX) {
            txtOutcome.setText("YOU GOT IT!");
        } else {
            txtOutcome.setText("MAYBE NEXT TIME");
        }

    }

}
