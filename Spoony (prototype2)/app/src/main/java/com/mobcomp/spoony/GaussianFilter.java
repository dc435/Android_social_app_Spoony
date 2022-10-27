package com.mobcomp.spoony;

import android.util.Log;

import java.util.ArrayList;

// the filter introduces a delay of (bufferSize - 1) / 2 frames since the gaussian curve is centred
// on the midpoint of the buffer
public class GaussianFilter {

    private final float WIDTH = 0.2f; // weierstrass width (t), determines intensity of smoothing
    private final float UPPER_BOUND = 180f;
    private final float LOWER_BOUND = -180f;
    private final float THRESHOLD = 20f;
    private final int bufferSize;
    private ArrayList<Float> buffer;

    public GaussianFilter(int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new ArrayList<>();
    }

    public void add(float x) {
        if (buffer.size() >= bufferSize) {
            buffer.remove(0);
        }

        buffer.add(x);
    }

    public float read() {
        int midpoint = (buffer.size() - 1) / 2;

        float result = 0f;

        for (int i = 0; i < buffer.size(); i++) {
            int x = i - midpoint; // centre the frame around a midpoint 0

            result += buffer.get(i) *
                    ((1f / (Math.sqrt(4f * Math.PI * WIDTH))) *
                    Math.pow(Math.E, -1f * ((x * x) / (4f * WIDTH))));
        }

        // gaussian filter struggles when crossing discontinuous portion of the angle representation
        // (i.e. as 180 crosses to -180), so we will use raw data for that section
        if (buffer.get(buffer.size() - 1) - THRESHOLD < LOWER_BOUND ||
                buffer.get(buffer.size() - 1) + THRESHOLD > UPPER_BOUND) {
            return buffer.get(buffer.size() - 1);
        }

        return result;
    }
}
