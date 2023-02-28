package ua.leonidius.raytracing.transformations;

public class ScaleTransform3d extends AffineTransform3d {

    public ScaleTransform3d(double scaleX, double scaleY, double scaleZ) {
        super(new double[][] {
                new double[] {scaleX, 0, 0, 0},
                new double[] {0, scaleY, 0, 0},
                new double[] {0, 0, scaleZ, 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
