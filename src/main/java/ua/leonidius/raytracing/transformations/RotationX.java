package ua.leonidius.raytracing.transformations;

public class RotationX extends AffineTransform3d {

    public RotationX(double angleD) {
        this(new AngleD(angleD));
    }

    private RotationX(AngleD angleD) {
        super(new double[][]{
                new double[] {1, 0, 0, 0},
                new double[] {0, angleD.cos(), -angleD.sin(), 0},
                new double[] {0, angleD.sin(), angleD.cos(), 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
