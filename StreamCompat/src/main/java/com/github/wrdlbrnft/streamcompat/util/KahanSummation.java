package com.github.wrdlbrnft.streamcompat.util;

/**
 * Created by kapeller on 21/03/16.
 */
public class KahanSummation {

    public static double[] sumWithCompensation(double[] intermediateSum, double value) {
        double tmp = value - intermediateSum[1];
        double sum = intermediateSum[0];
        double velvel = sum + tmp; // Little wolf of rounding error
        intermediateSum[1] = (velvel - sum) - tmp;
        intermediateSum[0] = velvel;
        return intermediateSum;
    }

    public static double computeFinalSum(double[] summands) {
        double tmp = summands[0] + summands[1];
        double simpleSum = summands[summands.length - 1];
        if (Double.isNaN(tmp) && Double.isInfinite(simpleSum)) {
            return simpleSum;
        } else {
            return tmp;
        }
    }

    public static float[] sumWithCompensation(float[] intermediateSum, float value) {
        float tmp = value - intermediateSum[1];
        float sum = intermediateSum[0];
        float velvel = sum + tmp; // Little wolf of rounding error
        intermediateSum[1] = (velvel - sum) - tmp;
        intermediateSum[0] = velvel;
        return intermediateSum;
    }

    public static float computeFinalSum(float[] summands) {
        float tmp = summands[0] + summands[1];
        float simpleSum = summands[summands.length - 1];
        if (Float.isNaN(tmp) && Float.isInfinite(simpleSum)) {
            return simpleSum;
        } else {
            return tmp;
        }
    }
}
