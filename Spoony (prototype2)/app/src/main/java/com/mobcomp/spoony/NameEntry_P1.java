package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameEntry_P1 extends AppCompatActivity {
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p1);
        preference = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
    }

    public void next(View view){
        Intent intent = new Intent(this, NameEntry_P2.class);

        // get player 1 name input
        EditText editText = (EditText) findViewById(R.id.name_input_p1);
        String p1Name = editText.getText().toString();

        // save player 1 name to preference
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(Key.P1_NAME, p1Name.trim());
        edit.apply();
        startActivity(intent);
    }
}