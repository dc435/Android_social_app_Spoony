package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameEntry_P2 extends AppCompatActivity {
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p2);
        preference = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
    }

    public void nextToPointP1(View view){
        Intent intent2 = new Intent(this, PointTo_P1.class);

        // get player 2 name input
        EditText editText = (EditText) findViewById(R.id.name_input_p2);
        String p2Name = editText.getText().toString();

        // save player 2 name to preference
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(Key.P2_NAME, p2Name.trim());
        edit.apply();
        startActivity(intent2);
    }
}
