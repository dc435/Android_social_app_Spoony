package com.mobcomp.spoony;

import android.util.Log;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GameDetails implements Serializable {

    private LinkedList<Question> freshQuestions;
    private LinkedList<Question> usedQuestions;
    private Question currentQuestion;
    private Player lead;
    private Player follow;
    private int round;

    public GameDetails() {
        freshQuestions = new LinkedList<Question>();
        usedQuestions = new LinkedList<Question>();
        lead = null;
        follow = null;
    }

//    public void setLeadName(String name) {
//        P1_NAME = name;
//    }
//
//    public void setFollowName(String name) {
//        P2_NAME = name;
//    }
//
//    public String getLeadName() {
//        if (LEAD == 1) {
//            return P1_NAME;
//        } else {
//            return P2_NAME;
//        }
//    }

    public void addPlayer(Player player) {
        if (lead == null) lead = player;
        else if (follow == null) follow = player;
        else Log.e("GameDetails", "No player slots free to add new player " + player.getName());
    }

    public void clearPlayers() {
        lead = null;
        follow = null;
    }

    public void addQuestion(String question) {
        freshQuestions.add(new Question(question));
    }

    public void addQuestion(Question question) {
        freshQuestions.add(question);
    }

    // retrieves a random new question and removes it from the list
    public Question newQuestion() {
        Collections.shuffle(freshQuestions);
        currentQuestion = freshQuestions.pop();
        return currentQuestion;
    }

    // retrieves a random new question without removing it from the list
    public Question getQuestionNonDestructive() {
        Random random = new Random();
        return freshQuestions.get(random.nextInt(freshQuestions.size()));
    }

    public void discardQuestion(Question question) {
        usedQuestions.add(question);
    }

    public Question getCurrentQuestion() { return currentQuestion; }
    public Player getLead() { return lead; }
    public Player getFollow() { return follow; }

    public int getRound() {
        return round;
    }

    public void nextRound() {
        round++;
        Player temp = lead;
        lead = follow;
        follow = temp;
    }
}
