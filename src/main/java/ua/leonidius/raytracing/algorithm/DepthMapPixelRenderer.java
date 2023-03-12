package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.enitites.Color;

public class DepthMapPixelRenderer implements IPixelRenderer {

    @Override
    public Color renderIntersection(Scene scene, Intersection intersection) {
        double clampedT = clamp(intersection.tParam(), 0, 3);
        double grayscaleValueReverse = clampedT / 10.0;
        return Color.grayscale(clamp0to1(1 - grayscaleValueReverse));
    }

    private static double clamp0to1(double value) {
        return Math.max(0, Math.min(1, value));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

}
