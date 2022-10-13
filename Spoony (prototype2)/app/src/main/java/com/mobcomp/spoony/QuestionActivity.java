package com.mobcomp.spoony;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends SpoonyActivity {

    private int questionDisplay;
    private int giveToDisplay;

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fetch layout ids
        questionDisplay = R.layout.activity_question;
        giveToDisplay = R.layout.give_to_xxx;

        // fetch question
        question = getGameDetails().newQuestion();

        displayGiveToScreen(); // to avoid accidentally showing the question to the wrong player in the first frame
    }


    @Override
    protected void onEnterLeadView() {
        displayQuestionScreen();
    }

    @Override
    protected void onExitLeadView() {
        displayGiveToScreen();
    }

    private void displayGiveToScreen() {
        setContentView(giveToDisplay);
        TextView giveToText = findViewById(R.id.give_to_text);
        giveToText.setText(String.format("Give the phone to %s, silly!",
                getGameDetails().getLead().getName()));
    }

    private void displayQuestionScreen() {
        setContentView(questionDisplay);

        Button button = findViewById(R.id.next_button);
        button.setOnClickListener(this::lockIn);

        TextView introText = findViewById(R.id.textView_question_title);
        TextView questionText = findViewById(R.id.textView_question_content);
        button = findViewById(R.id.next_button);
        introText.setText(String.format("Here's your question, %s. Don't tell %s!",
                getGameDetails().getLead().getName(),
                getGameDetails().getFollow().getName()));
        questionText.setText(question.question);
    }

    // continue to the answer stage
    private void lockIn(View view) {
        changeActivity(AnswerActivity.class);
    }
}
