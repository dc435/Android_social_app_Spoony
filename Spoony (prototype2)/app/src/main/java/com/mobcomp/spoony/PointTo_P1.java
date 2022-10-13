package com.mobcomp.spoony;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PointTo_P1 extends SpoonyActivity {

    private int _pointTo_P1Layout;
    private int _flatPromptLayout;

    private Button _confirmBtn;
    private TextView _p1NameView;
    private String _p1Name;

    private SharedPreferences _data;
    private SharedPreferences.Editor _editor;

    private GameDetails gameDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _pointTo_P1Layout = R.layout.activity_point_to_p1;
        _flatPromptLayout = R.layout.place_flat_flat;
        setContentView(_flatPromptLayout);


        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("GameDetails");
    }

    @Override
    protected void onEnterTable() {
        setContentView(_pointTo_P1Layout);
        nameSetup();
        buttonSetup();
    }

    @Override
    protected void onExitTable() {
        setContentView(_flatPromptLayout);
    }

    private void nameSetup() {
        _p1NameView = (TextView) findViewById(R.id.entry_name_p1);
        _p1NameView.setText(getGameDetails().getLead().getName());
    }

    private void buttonSetup() {
        _confirmBtn = (Button) findViewById(R.id.answer_lock_button);
        _confirmBtn.setOnClickListener((View v) -> {
            float zOrientation = deviceOrientation[0];
            Log.d("Orientation Prompt", String.valueOf(zOrientation));
            getGameDetails().getLead().setDirection(zOrientation);

            Intent intent = new Intent(this, PointTo_P2.class);
            intent.putExtra("GameDetails", gameDetails);
            startActivity(intent);
        });
    }
}
