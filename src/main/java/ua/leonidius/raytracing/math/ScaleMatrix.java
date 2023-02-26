package ua.leonidius.raytracing.math;

public class ScaleMatrix extends TransformMatrix3d {

    public ScaleMatrix(double scaleX, double scaleY, double scaleZ) {
        super(new double[][] {
                new double[] {scaleX, 0, 0, 0},
                new double[] {0, scaleY, 0, 0},
                new double[] {0, 0, scaleZ, 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
