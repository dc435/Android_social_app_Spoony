package com.mobcomp.spoony;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GameDetails implements Serializable {

    private static final int MINQUESTIONLEFT = 3;
    private LinkedList<Question> freshQuestions;
    private final LinkedList<Question> usedQuestions;
    private Question currentQuestion;
    private String guessedString;
    private String currentString;
    private Player lead;
    private Player follow;
    private int round;

    public GameDetails() {
        freshQuestions = new LinkedList<>();
        usedQuestions = new LinkedList<>();
        lead = null;
        follow = null;

        populate();
    }

    public void addPlayer(Player player, boolean isLead) {
        if (isLead)
            lead = player;
        else
            follow = player;
    }

    public void clearPlayers() {
        lead = null;
        follow = null;
    }

    public void setQuestions(LinkedList<Question> qs) {
        freshQuestions = qs;
    }

    // public void addQuestion(Question question) {
    // freshQuestions.add(new Question(question));
    // }

    // retrieves a random new question and removes it from the list
    public Question newQuestion() {
        Collections.shuffle(freshQuestions);
        Log.d("GDQUESTION", String.valueOf(freshQuestions));

        if (freshQuestions.size() < MINQUESTIONLEFT) {
            populate();
        }

        currentQuestion = freshQuestions.pop();
        return currentQuestion;
    }

    // retrieves a random new question without removing it from the list
    public Question getQuestionNonDestructive() {
        Random random = new Random();
        if (freshQuestions.size() == 0)
            populate();
        return freshQuestions.get(random.nextInt(freshQuestions.size()));
    }

    public void discardQuestion(Question question) {
        usedQuestions.add(question);
    }

    private void populate() {
        // pull from firebase here?
//        freshQuestions.add(new Question("Who is more like a cat?"));
//        freshQuestions.add(new Question("How much does each player talk?"));
//        freshQuestions.add(new Question("Who is more likely to eat a raw onion?"));
//        freshQuestions.add(new Question("Who is more like their mother?"));
//        freshQuestions.add(new Question("Who is more like a dog"));
//        freshQuestions.add(new Question("Who eats more?"));
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public Question getGuessedQuestion() {
        return currentQuestion;
    }

    public void setGuessedString(String qString) {
        guessedString = qString;
    }
    public String getGuessedString() {return guessedString;}
    public void setCurrentString(String cString) {currentString = cString;}
    public String getCurrentString() {return currentString;}

    public Player getLead() {
        return lead;
    }

    public Player getFollow() {
        return follow;
    }

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
