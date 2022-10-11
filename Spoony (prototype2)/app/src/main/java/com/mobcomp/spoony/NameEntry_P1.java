package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameEntry_P1 extends AppCompatActivity {

    private SharedPreferences data;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p1);
        data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
        editor = data.edit();
    }

    public void next(View view){
        Intent intent = new Intent(this, NameEntry_P2.class);
        EditText editText = (EditText) findViewById(R.id.name_input_p1);
        String p1Name = editText.getText().toString();
        editor.putString(Key.P1_NAME, p1Name);
        editor.apply();
        startActivity(intent);
    }
}