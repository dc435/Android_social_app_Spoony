package com.mobcomp.spoony;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MainActivity extends SpoonyActivity {

    private SharedPreferences _data;

    // state display
    private TextView _stateDisplay;

    // orientation display
    private TextView _xRot;
    private TextView _yRot;
    private TextView _zRot;

    private CoordinatorLayout _layout;

    // TEMP COLOURS TODO: remove
    private final int _p1Colour = Color.parseColor("#74D3A4");
    private final int _p2Colour = Color.parseColor("#ECC3E9");
    private final int _tableColour = Color.parseColor("#A98D76");
    private final int _defaultColour = Color.parseColor("#f3f3f3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ######### TEMP ########## TODO: remove
        SharedPreferences.Editor editor = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(Key.P1_NAME, "Barbie");
        editor.putString(Key.P2_NAME, "Ken");
        editor.putFloat(Key.P1_POSITION, 0.0f);
        editor.putFloat(Key.P2_POSITION, 180.0f);
        editor.apply();
        // ######### TEMP ##########

        _data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _stateDisplay = findViewById(R.id.stateText);
        _layout = findViewById(R.id.mainLayout);

        _xRot = findViewById(R.id.xrot);
        _yRot = findViewById(R.id.yrot);
        _zRot = findViewById(R.id.zrot);
    }

    @Override
    protected void updateAlways() {
        _xRot.setText("$$xrot: " + Double.toString(deviceOrientation[0]));
        _yRot.setText("$$yrot: " + Double.toString(deviceOrientation[1]));
        _zRot.setText("$$zrot: " + Double.toString(deviceOrientation[2]));
    }

    @Override
    protected void onEnterP1View() {
        if (_stateDisplay != null) _stateDisplay.setText("$$ " + _data.getString(Key.P1_NAME, ""));
        _layout.setBackgroundColor(_p1Colour);
    }

    @Override
    protected void onEnterP2View() {
        if (_stateDisplay != null) _stateDisplay.setText("$$ " + _data.getString(Key.P2_NAME, ""));
        _layout.setBackgroundColor(_p2Colour);
    }

    @Override
    protected void onEnterTable() {
        if (_stateDisplay != null) _stateDisplay.setText("$$ TABLE");
        _layout.setBackgroundColor(_tableColour);
    }

    @Override
    protected void onEnterDefault() {
        if (_stateDisplay != null) _stateDisplay.setText("$$ DEFAULT");
        _layout.setBackgroundColor(_defaultColour);
    }
}