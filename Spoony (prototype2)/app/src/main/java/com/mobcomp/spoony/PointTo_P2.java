package com.mobcomp.spoony;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PointTo_P2 extends SpoonyActivity {

    private int _pointTo_P2Layout;
    private int _flatPromptLayout;

    private Button _confirmBtn;
    private Intent _intent;
    private TextView _p2NameView;
    private String _p2Name;

    private SharedPreferences _data;
    private SharedPreferences.Editor _editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _pointTo_P2Layout = R.layout.activity_point_to_p2;
        _flatPromptLayout = R.layout.place_flat_flat;
        setContentView(_pointTo_P2Layout);
        nameSetup();
        buttonSetup();
        _data = getSharedPreferences(Key.DEFAULT_PREFERENCES, MODE_PRIVATE);
        _editor = _data.edit();
        _p2Name = _data.getString("p2Name", "Player 2");
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
        nameSetup();
        buttonSetup();
    }

    @Override
    protected void onExitTable() {
        setContentView(_flatPromptLayout);
    }

    private void nameSetup() {
        _p2NameView = (TextView) findViewById(R.id.entry_name_p2);
        _p2NameView.setText(_p2Name);
    }

    private void buttonSetup() {
        _confirmBtn = (Button) findViewById(R.id.lock_button);
        _confirmBtn.setOnClickListener((View v) -> {
            float zOrientation = DeviceOrientation[0];
            Log.d("Orientation Prompt", String.valueOf(zOrientation));
            _editor.putFloat(Key.P2_POSITION, zOrientation);
            _editor.apply();
            startActivity(_intent);
        });
    }
}
