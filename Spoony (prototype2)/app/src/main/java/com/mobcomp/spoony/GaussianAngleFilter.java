package com.mobcomp.spoony;

import java.util.ArrayList;

// the filter introduces a delay of (bufferSize - 1) / 2 frames since the gaussian curve is centred
// on the midpoint of the buffer
public class GaussianAngleFilter {

    private final float WIDTH = 0.2f; // weierstrass width (t), determines intensity of smoothing
    private final float UPPER_BOUND = 180f;
    private final float LOWER_BOUND = -180f;
    private final float THRESHOLD = 20f;
    private final int bufferSize;
    private ArrayList<Float> buffer;

    public GaussianAngleFilter(int bufferSize) {
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
        boolean nearUpperBound = false;
        boolean nearLowerBound = false;

        // gaussian filter struggles when crossing discontinuous portion of the angle representation
        // (i.e. as 180 crosses to -180), so we will translate those angles to (0, 360) to process
        // them, then normalise them back to the original range
        for (Float n : buffer) {
            if (n + THRESHOLD > UPPER_BOUND) nearUpperBound = true;
            if (n - THRESHOLD < LOWER_BOUND) nearLowerBound = true;
        }

        if (nearLowerBound && nearUpperBound) translateNegativeAngles(360f);

        int midpoint = (buffer.size() - 1) / 2;

        float result = 0f;

        for (int i = 0; i < buffer.size(); i++) {
            int x = i - midpoint; // centre the frame around a midpoint 0

            result += buffer.get(i) *
                    ((1f / (Math.sqrt(4f * Math.PI * WIDTH))) *
                    Math.pow(Math.E, -1f * ((x * x) / (4f * WIDTH))));
        }


        if (buffer.get(buffer.size() - 1) - THRESHOLD < LOWER_BOUND ||
                buffer.get(buffer.size() - 1) + THRESHOLD > UPPER_BOUND) {
            return buffer.get(buffer.size() - 1);
        }

        // we need to re-normalise any translated angles or they will affect future calculations
        normaliseAngles();

        return Angle.normaliseAngle(result);
    }

    private void translateNegativeAngles(float translation) {
        for (int i = 0; i < buffer.size(); i++) {
            if (buffer.get(i) < 0) buffer.set(i, buffer.get(i) + translation);
        }
    }

    private void normaliseAngles() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.set(0, Angle.normaliseAngle(buffer.get(0)));
        }
    }
}
