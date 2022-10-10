package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PointTo_P1 extends AppCompatActivity {

    SharedPreferences preference;
    String p1Name;
    TextView p1Name_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_to_p1);

        p1Name_show = findViewById(R.id.entry_name_p1);
        preference = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
        p1Name = preference.getString("p1name","");
        p1Name_show.setText(p1Name);
    }


    public void nextToPointP2(View view){
        Intent intent2 = new Intent(this, PointTo_P2.class);
        startActivity(intent2);
    }
}
