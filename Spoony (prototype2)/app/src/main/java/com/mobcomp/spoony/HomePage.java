package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;

public class HomePage extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "hello...";
    Button setting_button;
    Button start_button;
    Button firebase_button;
    LinkedList<Question> questions;
    GameDetails gd;
    FirebaseHandler fb = new FirebaseHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        setContentView(R.layout.activity_home_page);

        setting_button = findViewById(R.id.setting_button);
        start_button = findViewById(R.id.btnStart);
        setting_button = findViewById(R.id.setting_button);
        firebase_button = findViewById(R.id.firebase_button);

        setting_button.setOnClickListener(this::jumpToSetting);
        start_button.setOnClickListener(this::jumpToStart);
        setting_button.setOnClickListener(this::jumpToSetting);
        firebase_button.setOnClickListener(this::jumpToFirebase);

        gd = new GameDetails();
        questions = new LinkedList<>();
        fb.updateQuestions(fb.loadQuestionFromJSONFile(this, fb.isFirstBoot()), success -> {
            if (success) {
                questions = fb.getQuestions();
                fb.saveQuestionToJSONFile(this, questions);
                gd.setQuestions(questions);
                Log.d("GDQ", String.valueOf(questions));
            } else {
                Log.e("GDQERR", "SOMETHING VERY WRONG HAS HAPPENED WITH ADDING QUESTIONS OH GOD");
            }
        });
    }

    /**
     * Called when the user click the button
     */
    public void jumpToSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    public void jumpToStart(View view) {
        Intent intent = new Intent(this, Name.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);
    }

    public void jumpToFirebase(View view) {
        Intent intent = new Intent(this, FirebaseActivity.class);
        startActivity(intent);
    }
}
