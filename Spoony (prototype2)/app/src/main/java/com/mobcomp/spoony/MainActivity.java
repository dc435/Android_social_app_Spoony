package com.mobcomp.spoony;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
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

    private ImageView _p1Arrow;
    private ImageView _p2Arrow;

    private CoordinatorLayout _layout;

    // TEMP COLOURS TODO: remove
    private final int _p1Colour = Color.parseColor("#74D3A4");
    private final int _p2Colour = Color.parseColor("#ECC3E9");
    private final int _tableColour = Color.parseColor("#A98D76");
    private final int _defaultColour = Color.parseColor("#f3f3f3");

    private float _p1Position;
    private float _p2Position;

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

        _p1Arrow = findViewById(R.id.directionP1);
        _p2Arrow = findViewById(R.id.directionP2);
        _p1Arrow.setVisibility(View.GONE);
        _p2Arrow.setVisibility(View.GONE);

        _p1Position = _data.getFloat(Key.P1_POSITION, 0.0f);
        _p2Position = _data.getFloat(Key.P2_POSITION, 0.0f);

    }

    @Override
    protected void updateAlways() {
        _xRot.setText("$$xrot: " + Double.toString(DeviceOrientation[0]));
        _yRot.setText("$$yrot: " + Double.toString(DeviceOrientation[1]));
        _zRot.setText("$$zrot: " + Double.toString(DeviceOrientation[2]));
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

        _p1Arrow.setVisibility(View.VISIBLE);
        _p2Arrow.setVisibility(View.VISIBLE);
    }

    @Override
    protected void updateTable() {
        _p1Arrow.setRotation(worldToScreenRotation(_p1Position));
        _p2Arrow.setRotation(worldToScreenRotation(_p2Position));
    }

    @Override
    protected void onExitTable() {
        _p1Arrow.setVisibility(View.GONE);
        _p2Arrow.setVisibility(View.GONE);
    }

    @Override
    protected void onEnterDefault() {
        if (_stateDisplay != null) _stateDisplay.setText("$$ DEFAULT");
        _layout.setBackgroundColor(_defaultColour);
    }
}