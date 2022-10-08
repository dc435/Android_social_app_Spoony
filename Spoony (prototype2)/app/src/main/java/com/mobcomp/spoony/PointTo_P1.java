package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class PointTo_P1 extends AppCompatActivity {
    String p1Name;
    String p2Name;
    TextView p1Name_show = findViewById(R.id.entry_name_p1) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_to_p1);

        Intent intent = getIntent();
        p1Name = intent.getStringExtra(NameEntry_P2.EXTRA_MESSAGE1);
        p2Name = intent.getStringExtra(NameEntry_P2.EXTRA_MESSAGE);

        p1Name_show.setText(p1Name);
    }
}
