package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NameEntry_P2 extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "p2Name" ;
    public static final String EXTRA_MESSAGE1 = "p1Name" ;
    String p1Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p2);

        // Get the Intent that started this activity and extract the p1 Name
        Intent intent = getIntent();
        p1Name = intent.getStringExtra(HomePage.EXTRA_MESSAGE);
    }

    public void nextToPointP1(View view){
        Intent intent = new Intent(this, PointTo_P1.class);
        EditText editText = (EditText) findViewById(R.id.name_input_p2);
        String p2Name = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE1, p1Name);
        intent.putExtra(EXTRA_MESSAGE, p2Name);
        startActivity(intent);
    }
}
