package ua.leonidius.raytracing.math;

/**
 * Angle in degrees
 */
public class AngleD {

    private final double sin;
    private final double cos;

    public AngleD(double value) {
        this.sin = Math.sin(Math.toRadians(value));
        this.cos = Math.cos(Math.toRadians(value));
    }

    public double cos() { return cos; }

    public double sin() { return sin; }

}
