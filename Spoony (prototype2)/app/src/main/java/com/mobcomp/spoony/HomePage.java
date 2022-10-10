package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomePage extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "hello...";
    Button setting_button;
    Button start_button;
    Button firebase_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        setContentView(R.layout.activity_home_page);
    }

    /**
     * Called when the user click the button
     */
    public void jumpToSetting(View view){
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    public void jumpToStart(View view){
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
//    private void setDefaultStatus() {
//
//        gd = new GameDetails();
//        gd.P1_NAME = "Bobby";
//        gd.P2_NAME = "Anna";
//        gd.QUESTIONS[0] = "Who is more a cat or dog person?";
//        gd.QUESTIONS[1] = "How adventurous are you?";
//        gd.QUESTIONS[2] = "Are you a gamer?";
//        gd.QTN_INDEX = 0;
//        gd.P1_COLOR = getResources().getColor(R.color.p1_color);
//        gd.P2_COLOR = getResources().getColor(R.color.p2_color);
//
//    }
//
//    private void goToWhichQuestion() {
//        Intent intent = new Intent(this, WhichQuestion.class);
//        intent.putExtra("GameDetails", gd);
//        startActivity(intent);
//    }
}