package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PointTo_P1 extends AppCompatActivity {
    private static final String EXTRA_MESSAGE2 = "p2Name";
    String p1Name;
    String p2Name;
    TextView p1Name_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_to_p1);
        p1Name_show = findViewById(R.id.entry_name_p1);
        Intent intent = getIntent();
        p1Name = intent.getStringExtra(NameEntry_P2.EXTRA_MESSAGE1);
        p2Name = intent.getStringExtra(NameEntry_P2.EXTRA_MESSAGE2);
        p1Name_show.setText(p1Name);
    }


    public void nextToPointP2(View view){
        Intent intent2 = new Intent(this, PointTo_P2.class);
        intent2.putExtra(EXTRA_MESSAGE2, p2Name);
        startActivity(intent2);
    }
}
