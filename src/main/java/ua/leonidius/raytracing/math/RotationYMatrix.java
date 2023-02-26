package ua.leonidius.raytracing.math;

public class RotationYMatrix extends TransformMatrix3d {

    public RotationYMatrix(double angleD) {
        this(new AngleD(angleD));
    }

    public RotationYMatrix(AngleD angleD) {
        super(new double[][]{
                new double[] {angleD.cos(), 0, angleD.sin(), 0},
                new double[] {0, 1, 0, 0},
                new double[] {-angleD.sin(), 0, angleD.cos(), 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
