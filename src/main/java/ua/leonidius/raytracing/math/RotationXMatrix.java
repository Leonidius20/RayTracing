package ua.leonidius.raytracing.math;

public class RotationXMatrix extends TransformMatrix3d {

    public RotationXMatrix(double angleD) {
        this(new AngleD(angleD));
    }

    private RotationXMatrix(AngleD angleD) {
        super(new double[][]{
                new double[] {1, 0, 0, 0},
                new double[] {0, angleD.cos(), -angleD.sin(), 0},
                new double[] {0, angleD.sin(), angleD.cos(), 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
