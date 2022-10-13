package com.mobcomp.spoony;

public class Angle {

    // calculates (unsigned) distance between two angles
    public static float rotationDistanceUnsigned(float a, float b) {
        return 180 - Math.abs((Math.abs(a - b) % 360) - 180);
    }

    // calculates signed distance between two angles TODO: this is borked
    public static float rotationDistanceSigned(float a, float b) {
        float d = rotationDistanceUnsigned(a, b);
        int sign = 1;
        if (d >= 180 || normaliseAngle(a) > normaliseAngle(b)) sign = -1;

        return sign * d;
    }

    // returns angle within [0, 360)
    public static float normaliseAngle(float a) {
        return ((360 + (a % 360)) % 360);
    }
}
