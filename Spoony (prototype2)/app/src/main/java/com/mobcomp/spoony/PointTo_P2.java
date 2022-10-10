package com.mobcomp.spoony;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PointTo_P2 extends SpoonyActivity {

    private int _pointTo_P2Layout;
    private int _flatPromptLayout;

    private Button _confirmBtn;
    private Intent _intent;

    private SharedPreferences _data;
    private SharedPreferences.Editor _editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _pointTo_P2Layout = R.layout.activity_point_to_p2;
        _flatPromptLayout = R.layout.place_flat_flat;
        setContentView(_pointTo_P2Layout);
        buttonSetup();
        _data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
        _editor = _data.edit();
        _intent = new Intent(this, Question.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onEnterTable() {
        setContentView(_pointTo_P2Layout);
        buttonSetup();
    }

    @Override
    protected void onExitTable() {
        setContentView(_flatPromptLayout);
    }

    private void buttonSetup() {
        _confirmBtn = (Button) findViewById(R.id.lock_button);
        _confirmBtn.setOnClickListener((View v) -> {
            float zOrientation = DeviceOrientation[0];
            Log.d("Orientation Prompt", String.valueOf(zOrientation));
            _editor.putFloat(Key.P2_POSITION, zOrientation);
            startActivity(_intent);
        });
    }
}
