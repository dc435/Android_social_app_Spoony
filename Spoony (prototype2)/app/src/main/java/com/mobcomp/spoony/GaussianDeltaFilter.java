package com.mobcomp.spoony;

import java.util.ArrayList;

// the filter introduces a delay of (bufferSize - 1) / 2 frames since the gaussian curve is centred
// on the midpoint of the buffer
public class GaussianDeltaFilter {

    private final float width; // weierstrass width (t), determines intensity of smoothing
    private final int bufferSize;
    private ArrayList<Float> buffer;
    private ArrayList<Float> deltas;

    public GaussianDeltaFilter(int bufferSize, float weierstrassWidth) {
        this.bufferSize = bufferSize;
        this.width = weierstrassWidth;
        buffer = new ArrayList<>();
        deltas = new ArrayList<>();
    }

    public void add(float x) {
        if (buffer.size() >= bufferSize) {
            buffer.remove(0);
        }

        buffer.add(x);
    }

    public float read() {
        // use midpoint as centre of gaussian distribution
        int midpoint = (buffer.size() - 1) / 2;
        float baseAngle = buffer.get(midpoint);

        deltas.clear();

        for (Float n : buffer) {
            deltas.add(Angle.rotationDistanceUnsigned(baseAngle, n));
        }

        float resultDelta = 0f;

        for (int i = 0; i < deltas.size(); i++) {
            int x = i - midpoint; // centre the frame around origin 0

            resultDelta += deltas.get(i) *
                    ((1f / (Math.sqrt(4f * Math.PI * width))) *
                    Math.pow(Math.E, -1f * ((x * x) / (4f * width))));
        }

        return Angle.normaliseAngle(baseAngle + resultDelta);
    }
}
