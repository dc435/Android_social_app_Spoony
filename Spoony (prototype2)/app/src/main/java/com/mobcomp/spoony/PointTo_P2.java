package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class PointTo_P2 extends AppCompatActivity {

    SharedPreferences preference;
    String p2Name;
    TextView p2Name_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_to_p2);

        p2Name_show = findViewById(R.id.entry_name_p2);
        preference = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
        p2Name = preference.getString("p2name", "");
        p2Name_show.setText(p2Name);
    }
}
