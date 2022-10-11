package com.mobcomp.spoony;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class WhichQuestion extends SpoonyActivity {

    private GameDetails gd;
    private TextView txtFollow_LeadName;
    private TextView txtLead_LeadName;
    private TextView txtLead_FollowName;
    private TextView txtTable_FollowName;

    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnConfirm;

    private Drawable drwBtnDefault;
    private Drawable drwBtnClicked;
    private int clrBlack;

    private ConstraintLayout lytFollow;
    private ConstraintLayout lytLead;
    private ConstraintLayout lytTable;
    private ConstraintLayout lytTableInner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.which_question);

        Intent intent = getIntent();
        gd = (GameDetails) intent.getSerializableExtra("GameDetails");

        txtFollow_LeadName = findViewById(R.id.txtFollow_LeadName);

        txtLead_LeadName = findViewById(R.id.txtLead_LeadName);
        txtLead_FollowName = findViewById(R.id.txtLead_FollowName);

        txtTable_FollowName = findViewById(R.id.txtTable_FollowName);

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnConfirm = findViewById(R.id.btnConfirm);

        lytFollow = findViewById(R.id.lytFollow);
        lytLead = findViewById(R.id.lytLead);
        lytTable = findViewById(R.id.lytTable);
        lytTableInner = findViewById(R.id.lytTableInner);

        drwBtnDefault = getResources().getDrawable(R.drawable.question_box_choose);
        drwBtnClicked = getResources().getDrawable(R.drawable.question_box_choose_trans);
        clrBlack = Color.BLACK;

        View.OnClickListener optionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClick(view);
            }
        };

        View.OnClickListener confirmClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClick();
            }
        };

        btnA.setOnClickListener(optionClick);
        btnB.setOnClickListener(optionClick);
        btnC.setOnClickListener(optionClick);
        btnConfirm.setOnClickListener(confirmClick);

        makeAllInvisible();
        setTexts();
        setFormats();

    }

    private void setTexts() {
        txtFollow_LeadName.setText(gd.getLeadName());
        btnA.setText("A. " + gd.QUESTIONS[0]);
        btnB.setText("B. " + gd.QUESTIONS[1]);
        btnC.setText("C. " + gd.QUESTIONS[2]);
        txtLead_LeadName.setText(gd.getLeadName());
        txtLead_FollowName.setText(gd.getFollowName());
        txtTable_FollowName.setText(gd.getFollowName());
    }

    private void setFormats() {
        txtFollow_LeadName.setTextColor(gd.getLeadColor());
        txtLead_LeadName.setTextColor(gd.getLeadColor());
        txtLead_FollowName.setTextColor(gd.getFollowColor());
        txtTable_FollowName.setTextColor(gd.getFollowColor());
    }

    private void makeAllInvisible(){
        lytFollow.setVisibility(View.INVISIBLE);
        lytLead.setVisibility(View.INVISIBLE);
        lytTable.setVisibility(View.INVISIBLE);
    }

    private void optionClick(View view) {
        btnA.setBackground(drwBtnDefault);
        btnB.setBackground(drwBtnDefault);
        btnC.setBackground(drwBtnDefault);
        Button btnClicked = (Button) view;
        btnClicked.setBackground(drwBtnClicked);
        btnConfirm.setClickable(true);
        btnConfirm.setTextColor(clrBlack);

        if (btnClicked.getId()==btnA.getId()) {
            gd.QTN_GUESS = 0;
        } else if (btnClicked.getId()==btnB.getId()) {
            gd.QTN_GUESS = 1;
        } else {
            gd.QTN_GUESS = 2;
        }
    }

    private void confirmClick() {
        Intent intent = new Intent(this, WhichAnswer.class);
        intent.putExtra("GameDetails", gd);
        startActivity(intent);
    }

    protected void onEnterP1View() {
        makeAllInvisible();
        lytLead.setVisibility(View.VISIBLE);
    }

    protected void updateP1View() {}
    protected void onExitP1View() {}

    protected void onEnterP2View() {
        makeAllInvisible();
        lytFollow.setVisibility(View.VISIBLE);
    }

    protected void updateP2View() {}
    protected void onExitP2View() {}

    protected void onEnterTable() {
        makeAllInvisible();
        lytTable.setVisibility(View.VISIBLE);
    }

    protected void updateTable() {
        //TODO: Designed to display text pointing to Follow. Need to identify correct metric:
        lytTableInner.setRotation(DeviceOrientation[0]);
    }

    protected void onExitTable() {}

    protected void onEnterDefault() {}
    protected void updateDefault() {}
    protected void onExitDefault() {}

    protected void updateAlways() {}

}
