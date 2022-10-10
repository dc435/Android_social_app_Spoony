package com.mobcomp.spoony;

import android.graphics.Color;
import android.util.Log;

import java.io.Serializable;

public class GameDetails implements Serializable {

    public String P1_NAME;
    public String P2_NAME;
    public int P1_COLOR;
    public int P2_COLOR;
    public String[] QUESTIONS;
    public int QTN_INDEX;
    public int QTN_GUESS;
    private int LEAD;
    public GameDetails() {
        QUESTIONS = new String[3];
        LEAD = 1;
    }

    public String getLeadName() {
        if (LEAD == 1) {
            return P1_NAME;
        } else {
            return P2_NAME;
        }
    }

    public String getFollowName() {
        if (LEAD == 1) {
            return P2_NAME;
        } else {
            return P1_NAME;
        }
    }

    public int getLeadColor() {
        if (LEAD == 1) {
            return P1_COLOR;
        } else {
            return P2_COLOR;
        }
    }

    public int getFollowColor() {
        if (LEAD == 1) {
            return P2_COLOR;
        } else {
            return P1_COLOR;
        }
    }

}
