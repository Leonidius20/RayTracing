package ua.leonidius.raytracing.transformations;

public class RotationY extends AffineTransform3d {

    public RotationY(double angleD) {
        this(new AngleD(angleD));
    }

    public RotationY(AngleD angleD) {
        super(new double[][]{
                new double[] {angleD.cos(), 0, angleD.sin(), 0},
                new double[] {0, 1, 0, 0},
                new double[] {-angleD.sin(), 0, angleD.cos(), 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
