package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameEntry_P1 extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Name of P1" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p1);
    }

    public void next(View view){
        Intent intent = new Intent(this, NameEntry_P2.class);
        EditText editText = (EditText) findViewById(R.id.name_input_p1);
        String p1Name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, p1Name);
        startActivity(intent);
    }
}