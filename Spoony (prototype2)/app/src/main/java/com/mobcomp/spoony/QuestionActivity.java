package com.mobcomp.spoony;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends SpoonyActivity {

    private GameDetails gameDetails;

    private int questionDisplay;
    private int giveToDisplay;

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fetch game details
        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("GameDetails");

        // TODO temp
        convertSharedPrefs();

        // fetch layout ids
        questionDisplay = R.layout.activity_question;
        giveToDisplay = R.layout.give_to_xxx;

        // fetch question
        question = gameDetails.newQuestion();

        displayGiveToScreen(); // to avoid accidentally showing the question to the wrong player in the first frame
    }

    // TODO: REMOVE TEMP TEMP TEMP TEMP
    // generate data for testing
    private void convertSharedPrefs() {
        if (gameDetails == null) gameDetails = new GameDetails();

        if (gameDetails.getLead() != null && gameDetails.getFollow() != null) return;

        SharedPreferences data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);

        Player lead = new Player(data.getString(Key.P1_NAME, "Player 1"),
                data.getFloat(Key.P1_POSITION, 0.0f),
                getResources().getColor(R.color.p1_color)); // TODO move to using themes, this is deprecated

        Player follow = new Player(data.getString(Key.P2_NAME, "Player 2"),
                data.getFloat(Key.P2_POSITION, 0.0f),
                getResources().getColor(R.color.p2_color));

        gameDetails.addPlayer(lead);
        gameDetails.addPlayer(follow);

        gameDetails.addQuestion("Who is more like a cat?");
        gameDetails.addQuestion("How much does each player talk?");
        gameDetails.addQuestion("Who is more likely to eat a raw onion?");
        gameDetails.addQuestion("Who is more like their mother?");
        gameDetails.addQuestion("Who is more like a dog");
        gameDetails.addQuestion("Who eats more?");
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
                gameDetails.getLead().getName()));
    }

    private void displayQuestionScreen() {
        setContentView(questionDisplay);

        Button button = findViewById(R.id.next_button);
        button.setOnClickListener(this::lockIn);

        TextView introText = findViewById(R.id.textView_question_title);
        TextView questionText = findViewById(R.id.textView_question_content);
        button = findViewById(R.id.next_button);
        introText.setText(String.format("Here's your question, %s. Don't tell %s!",
                gameDetails.getLead().getName(),
                gameDetails.getFollow().getName()));
        questionText.setText(question.question);
    }

    // continue to the answer stage
    private void lockIn(View view) {
        Intent intent = new Intent(this, AnswerActivity.class);
        intent.putExtra("GameDetails", gameDetails);
        startActivity(intent);
    }
}
