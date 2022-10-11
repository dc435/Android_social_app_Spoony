package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameEntry_P2 extends AppCompatActivity {

    private SharedPreferences data;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p2);
        data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
        editor = data.edit();
    }

    public void nextToPointP1(View view){
        Intent intent2 = new Intent(this, PointTo_P1.class);
        EditText editText = (EditText) findViewById(R.id.name_input_p2);
        String p2Name = editText.getText().toString();
        editor.putString(Key.P2_NAME, p2Name);
        editor.apply();
        startActivity(intent2);
    }
}
