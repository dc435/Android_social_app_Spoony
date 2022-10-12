package com.mobcomp.spoony;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class GameDetails implements Serializable {

    private static final int MINQUESTIONLEFT = 3;
    private LinkedList<Question> freshQuestions;
    private final LinkedList<Question> usedQuestions;
    private Question currentQuestion;
    private Player lead;
    private Player follow;
    private int round;
    firebaseHandler fb = new firebaseHandler();

    public GameDetails() {
        freshQuestions = new LinkedList<>();
        usedQuestions = new LinkedList<>();
        lead = null;
        follow = null;
    }

    public void addPlayer(Player player) {
        if (lead == null) lead = player;
        else if (follow == null) follow = player;
        else Log.e("GameDetails", "No player slots free to add new player " + player.getName());
    }

    public void clearPlayers() {
        lead = null;
        follow = null;
    }

    public void addQuestion(Context c) {
//        AtomicReference<HashMap<String, Object>> qs = new AtomicReference<>(new HashMap<>());
        fb.updateQuestions(fb.loadQuestionFromJSONFile(c, fb.isFirstBoot()), success -> {
            if (success) {
                freshQuestions = fb.getQuestions();
                fb.saveQuestionToJSONFile(c, freshQuestions);
                Log.d("GDQ", String.valueOf(freshQuestions));
            }
            else {
                Log.e("GDQERR", "SOMETHING VERY WRONG HAS HAPPENED WITH ADDING QUESTIONS OH GOD");
                }
            });
        }

//    public void addQuestion(Question question) {
//        freshQuestions.add(new Question(question));
//    }

    // retrieves a random new question and removes it from the list
    public Question newQuestion() {
        Collections.shuffle(freshQuestions);
        Log.d("GDQUESTION", String.valueOf(freshQuestions));
        currentQuestion = freshQuestions.pop();
        if (freshQuestions.size() < MINQUESTIONLEFT) {
            // TODO: implement add new questions
        }
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
