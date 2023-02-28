package ua.leonidius.raytracing.transformations;

public class RotationXMatrix extends AffineTransform3d {

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
