package com.mobcomp.spoony;

public class Angle {

    // calculates (unsigned) distance between two angles
    public static float rotationDistanceUnsigned(float from, float to) {
        return 180 - Math.abs((Math.abs(to - from) % 360) - 180);
    }

    // returns the percentage [0.0, 1.0] along a path between angles a and b the given angle lies on
    public static float percentageBetween(float target, float from, float to) {
        return rotationDistanceUnsigned(target, from) /
                (rotationDistanceUnsigned(target, from) + rotationDistanceUnsigned(target, to));
    }

    // calculates signed distance from one angle to the other (+ clockwise, - counter-clockwise)
    // normalise angles first (instead of in method to reduce overhead)
    public static float rotationDistanceSigned(float from, float to) {
        float rotation = to - from;

        if (rotation > 180) return rotation - 360f;
        if (rotation < -180) return rotation + 360f;
        else return rotation;
    }

    // returns angle within (-180, 180]
    public static float normaliseAngle(float a) {
        return (360 + ((a + 180) % 360)) % 360 - 180f;
    }
}
