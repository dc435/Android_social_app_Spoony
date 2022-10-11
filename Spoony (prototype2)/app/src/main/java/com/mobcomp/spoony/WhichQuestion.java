package com.mobcomp.spoony;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class WhichQuestion extends SpoonyActivity {

    private GameDetails gd;
    private TextView txtLeadName;
    private TextView txtQA;
    private TextView txtQB;
    private TextView txtQC;
    private TextView txtLead_LeadName;
    private TextView txtLead_FollowName;
    private TextView txtTable_FollowName;

    private ImageButton btnA;
    private ImageButton btnB;
    private ImageButton btnC;
    private int colorBtnClicked;
    private int colorBtnNormal;

    private ConstraintLayout lytFollow;
    private ConstraintLayout lytLead;
    private ConstraintLayout lytTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.which_question);

        Intent intent = getIntent();
        gd = (GameDetails) intent.getSerializableExtra("GameDetails");

        txtLeadName = findViewById(R.id.txtLeadName);
        txtQA = findViewById(R.id.txtQA);
        txtQB = findViewById(R.id.txtQB);
        txtQC = findViewById(R.id.txtQC);

        txtLead_LeadName = findViewById(R.id.txtLead_LeadName);
        txtLead_FollowName = findViewById(R.id.txtLead_FollowName);

        txtTable_FollowName = findViewById(R.id.txtTable_FollowName);

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);

        lytFollow = findViewById(R.id.lytFollow);
        lytLead = findViewById(R.id.lytLead);
        lytTable = findViewById(R.id.lytTable);

        colorBtnClicked = getResources().getColor(R.color.spooner_color);
        colorBtnNormal = 16777215; //HEX FFFFFFF (white)

        View.OnClickListener optionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClick(view);
            }
        };

        btnA.setOnClickListener(optionClick);
        btnB.setOnClickListener(optionClick);
        btnC.setOnClickListener(optionClick);

        makeAllInvisible();
        setTexts();
        setFormats();

    }

    private void setTexts() {
        // TODO: the first question will currently always be the correct one
        txtLeadName.setText(gd.getLead().getName());
        txtQA.setText("A. " + gd.getCurrentQuestion().question);
        txtQB.setText("B. " + gd.getQuestionNonDestructive().question);
        txtQC.setText("C. " + gd.getQuestionNonDestructive().question);
        txtLead_LeadName.setText(gd.getLead().getName());
        txtLead_FollowName.setText(gd.getFollow().getName());
        txtTable_FollowName.setText(gd.getFollow().getName());
    }

    private void setFormats() {
        txtLeadName.setTextColor(gd.getLead().getColour());
        txtLead_LeadName.setTextColor(gd.getLead().getColour());
        txtLead_FollowName.setTextColor(gd.getFollow().getColour());
        txtTable_FollowName.setTextColor(gd.getFollow().getColour());
    }

    private void makeAllInvisible(){
        lytFollow.setVisibility(View.INVISIBLE);
        lytLead.setVisibility(View.INVISIBLE);
        lytTable.setVisibility(View.INVISIBLE);
    }

    private void optionClick(View view) {
        ImageButton btnClicked = (ImageButton) view;
        btnA.setBackgroundColor(colorBtnNormal);
        btnB.setBackgroundColor(colorBtnNormal);
        btnC.setBackgroundColor(colorBtnNormal);
        btnClicked.setBackgroundColor(colorBtnClicked);
    }

    protected void onEnterLeadView() {
        makeAllInvisible();
        lytLead.setVisibility(View.VISIBLE);
    }
    protected void updateLeadView() {}
    protected void onExitLeadView() {}

    protected void onEnterFollowView() {
        makeAllInvisible();
        lytFollow.setVisibility(View.VISIBLE);
    }
    protected void updateFollowView() {}
    protected void onExitFollowView() {}

    protected void onEnterTable() {
        makeAllInvisible();
        lytTable.setVisibility(View.VISIBLE);
    }
    protected void updateTable() {}
    protected void onExitTable() {}

    protected void onEnterDefault() {}
    protected void updateDefault() {}
    protected void onExitDefault() {}

    protected void updateAlways() {}

}
