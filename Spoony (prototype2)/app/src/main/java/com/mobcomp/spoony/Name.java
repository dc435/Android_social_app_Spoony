package com.mobcomp.spoony;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Name extends GameActivity {

    private int p1Color;
    private int p2Color;

    private Button confirmBtn;
    private TextView nameEdit;
    private TextView nameView;
    private GameDetails gameDetails;

    private boolean p1Confirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);

        p1Confirmed = false;
        p1Color = ContextCompat.getColor(this, R.color.p1_color);
        p2Color = ContextCompat.getColor(this, R.color.p2_color);

        gameDetails = getGameDetails();

        nameView = (TextView) findViewById(R.id.textView_p1p2);
        nameEdit = (TextView) findViewById(R.id.name_input_p1);
        textViewSetup();

        confirmBtn = (Button) findViewById(R.id.next_button);
        confirmBtn.setOnClickListener((View v) -> onConfirmPressed());
        commonBtnSetup();
    }

    private void onConfirmPressed() {
        String nameInput = nameEdit.getText().toString();
        if (nameInput.length() > 0) {
            int color = p1Confirmed ? p2Color : p1Color;
            gameDetails.addPlayer(new Player(nameInput, color), !p1Confirmed);
            if (p1Confirmed) {
                changeActivity(PToP.class, gameDetails);
            } else {
                p1Confirmed = true;
                textViewSetup();
            }
        } else {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_refuse_shake);
            confirmBtn.startAnimation(shake);
            String prompt = "Please enter the name";
            Toast toast = Toast.makeText(this, prompt, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        if(p1Confirmed) {
            p1Confirmed = false;
            textViewSetup();
        } else {
            goBackHome();
        }
    }

    private void textViewSetup() {
        int color = p1Confirmed ? p2Color : p1Color;
        String name = p1Confirmed ? "Spooner 2" : "Spooner 1";
        nameView.setText(name);
        nameView.setTextColor(color);
        nameEdit.setText("");
    }
}