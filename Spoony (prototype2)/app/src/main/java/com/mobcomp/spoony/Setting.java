package com.mobcomp.spoony;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    private String TAG = "OnClick:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        setContentView(R.layout.activity_home_page);

        Log.d(TAG,  "Now on Setting page now. Waitting to be implemented :)");
    }

}
