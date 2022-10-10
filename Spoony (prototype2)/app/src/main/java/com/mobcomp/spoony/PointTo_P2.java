package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PointTo_P2 extends AppCompatActivity {

    String p2Name;
    TextView p2Name_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_to_p2);

        p2Name_show = findViewById(R.id.entry_name_p2);
        Intent intent = getIntent();
        p2Name = intent.getStringExtra(NameEntry_P2.EXTRA_MESSAGE2);
        p2Name_show.setText(p2Name);
    }
}
