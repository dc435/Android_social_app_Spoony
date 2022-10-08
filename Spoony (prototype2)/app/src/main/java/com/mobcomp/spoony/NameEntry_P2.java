package com.mobcomp.spoony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NameEntry_P2 extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Name of P2" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry_p2);
   
    }

    public void nextToPointP1(View view){
        Intent intent = new Intent(this, com.example.mobilecomp.PointTo_P1.class);
        startActivity(intent);
    }
}
