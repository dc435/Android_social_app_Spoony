package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "hello...";
    Button setting_button;
    Button start_button;
    Button firebase_button;
    GameDetails gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        setContentView(R.layout.activity_home_page);

        setting_button = findViewById(R.id.setting_button);
        start_button = findViewById(R.id.btnStart);
        firebase_button = findViewById(R.id.firebase_button);

        setting_button.setOnClickListener(this::jumpToSetting);
        start_button.setOnClickListener(this::jumpToStart);
        firebase_button.setOnClickListener(this::jumpToFirebase);

        gd = new GameDetails();
        gd.addQuestion(this);
    }

    /**
     * Called when the user click the button
     */
    public void jumpToSetting(View view){
        Toast toast=Toast.makeText(getApplicationContext(),"DO NOT PRESS ME!",Toast.LENGTH_LONG);
        toast.setMargin(50,50);
        toast.show();
//        Intent intent = new Intent(this, Setting.class);
//        startActivity(intent);
    }

    public void jumpToStart(View view){

        //test get question
        setDefaultStatus();
        Intent intent = new Intent(this, NameEntry_P1.class);
        startActivity(intent);
    }

    public void jumpToFirebase(View view){
        Intent intent = new Intent(this, FirebaseActivity.class);
        startActivity(intent);
    }

    // these are changes from main branch, save them in case of future use
//    btnStart = findViewById(R.id.btnStart);
//
//    setDefaultStatus();
//
//        btnStart.setOnClickListener(view -> {
//        this.goToWhichQuestion();
//    });
//
//}
//
    private void setDefaultStatus() {
        Log.d("GDQUESTION", gd.newQuestion().question);

//        gd.P1_NAME = "Bobby";
//        gd.P2_NAME = "Anna";
//        gd.QUESTIONS[0] = "Who is more a cat or dog person?";
//        gd.QUESTIONS[1] = "How adventurous are you?";
//        gd.QUESTIONS[2] = "Are you a gamer?";
//        gd.QTN_INDEX = 0;
//        gd.P1_COLOR = getResources().getColor(R.color.p1_color);
//        gd.P2_COLOR = getResources().getColor(R.color.p2_color);

    }
//
//    private void goToWhichQuestion() {
//        Intent intent = new Intent(this, WhichQuestion.class);
//        intent.putExtra("GameDetails", gd);
//        startActivity(intent);
//    }
}
