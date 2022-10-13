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
    private TextView _p2NameView;

    private SharedPreferences _data;
    private SharedPreferences.Editor _editor;

    private GameDetails gameDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _pointTo_P2Layout = R.layout.activity_point_to_p2;
        _flatPromptLayout = R.layout.place_flat_flat;
        setContentView(_pointTo_P2Layout);
        nameSetup();
        buttonSetup();

        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("GameDetails");
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
        _p2NameView.setText(getGameDetails().getFollow().getName());
    }

    private void buttonSetup() {
        _confirmBtn = (Button) findViewById(R.id.answer_lock_button);
        _confirmBtn.setOnClickListener((View v) -> {
            float zOrientation = deviceOrientation[0];
            Log.d("Orientation Prompt", String.valueOf(zOrientation));
            getGameDetails().getFollow().setDirection(zOrientation);

            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("GameDetails", gameDetails);
            startActivity(intent);
        });
    }
}
