package com.mobcomp.spoony;

import java.io.Serializable;

public class Question implements Serializable {
    public String question;
    public Player answer;

    public Question(String question) {
        this.question = question;
        answer = null;
    }
}
