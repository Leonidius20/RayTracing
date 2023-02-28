package ua.leonidius.raytracing.transformations;

public class RotationZMatrix extends AffineTransform3d {

    public RotationZMatrix(double angleD) {
        this(new AngleD(angleD));
    }

    public RotationZMatrix(AngleD angleD) {
        super(new double[][]{
                new double[] {angleD.cos(), -angleD.sin(), 0, 0},
                new double[] {angleD.sin(), angleD.cos(), 0, 0},
                new double[] {0, 0, 1, 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
