package com.mobcomp.spoony;

import android.graphics.Color;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private float direction;
    private int colour;
    private int score;

    public Player(String name, float direction, int colour) {
        this.name = name;
        this.direction = direction;
        this.colour = colour;
        score = 0;
    }

    public Player(String name, float direction) {
        this.name = name;
        this.direction = direction;
        colour = Color.WHITE;
        score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
}
