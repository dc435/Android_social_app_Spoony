package com.mobcomp.spoony;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class WhichQuestion extends SpoonyActivity {

    private GameDetails gd;
    private TextView text_txt_center;
    private TextView whichq_txt_leadName;
    private Button whichq_btn_OptA;
    private Button whichq_btn_OptB;
    private Button whichq_btn_OptC;
    private ArrayList<String> questionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gd = getGameDetails();

        // Fetch and shuffle questions
        questionSet = new ArrayList<String>();
        String q = gd.getCurrentQuestion().text;
        gd.setCurrentString(q);
        questionSet.add(q);
        questionSet.add(gd.getQuestionNonDestructive().text);
        questionSet.add(gd.getQuestionNonDestructive().text);
        Collections.shuffle(questionSet);

    }

    private void optionClick(View view) {

        // Identify which button was clicked:
        Button btnClicked = (Button) view;

        // Set the 'guessed' question based on button clicked:
        if (btnClicked.getId()==whichq_btn_OptA.getId()) {
            gd.setGuessedString(questionSet.get(0));
        } else if (btnClicked.getId()==whichq_btn_OptB.getId()) {
            gd.setGuessedString(questionSet.get(1));
        } else {
            gd.setGuessedString(questionSet.get(2));
        }

        // Start next activity:
        Intent intent = new Intent(this, WhichAnswer.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);

    }

    protected void onEnterLeadView() {
        setContentView(R.layout.text);
        text_txt_center = findViewById(R.id.txt);
        text_txt_center.setText(gd.getLead().getName() + ", give me to " + gd.getFollow().getName());
    }

    protected void onEnterFollowView() {

        // Construct view:
        setContentView(R.layout.whichq);
        whichq_txt_leadName = findViewById(R.id.whichq_txt_leadName);
        whichq_txt_leadName.setText(gd.getLead().getName());
        whichq_txt_leadName.setTextColor(gd.getLead().getColour());
        whichq_btn_OptA = findViewById(R.id.whichq_btn_OptA);
        whichq_btn_OptB = findViewById(R.id.whichq_btn_OptB);
        whichq_btn_OptC = findViewById(R.id.whichq_btn_OptC);
        View.OnClickListener optionClick = view -> optionClick(view);
        whichq_btn_OptA.setOnClickListener(optionClick);
        whichq_btn_OptB.setOnClickListener(optionClick);
        whichq_btn_OptC.setOnClickListener(optionClick);

        // Set question text in buttons:
        whichq_btn_OptA.setText("A. " + questionSet.get(0));
        whichq_btn_OptB.setText("B. " + questionSet.get(1));
        whichq_btn_OptC.setText("C. " + questionSet.get(2));

    }

    protected void onEnterTable() {
        setContentView(R.layout.text);
        text_txt_center = findViewById(R.id.txt);
        text_txt_center.setText(gd.getFollow().getName() + ", pick me up!");
    }

}