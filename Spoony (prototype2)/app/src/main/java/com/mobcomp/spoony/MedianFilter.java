package com.mobcomp.spoony;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class MedianFilter {

    private final int bufferSize;
    private ArrayList<Float> buffer;
    private ArrayList<Float> sorted;

    public MedianFilter(int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new ArrayList<>();
        sorted = new ArrayList<>();
    }

    public void add(float x) {
        if (buffer.size() >= bufferSize) {
            buffer.remove(0);
        }

        buffer.add(x);
    }

    public float read() {
        int midpoint = (buffer.size() - 1) / 2;

        sorted = (ArrayList<Float>) buffer.clone();
        Collections.sort(sorted);

        return sorted.get(midpoint);
    }
}
