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
        setting_button = findViewById(R.id.setting_button);
        firebase_button = findViewById(R.id.firebase_button);

        setting_button.setOnClickListener(this::jumpToSetting);
        start_button.setOnClickListener(this::jumpToStart);
        setting_button.setOnClickListener(this::jumpToSetting);
        firebase_button.setOnClickListener(this::jumpToFirebase);

        gd = new GameDetails();
        gd.addQuestion(this);
    }

    /**
     * Called when the user click the button
     */
    public void jumpToSetting(View view) {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    public void jumpToStart(View view) {
        GameDetails gameDetails = new GameDetails();
        Intent intent = new Intent(this, Name.class);
        intent.putExtra("GameDetails", gameDetails);
        startActivity(intent);
    }

    public void jumpToFirebase(View view) {
        Intent intent = new Intent(this, FirebaseActivity.class);
        startActivity(intent);
    }
}
