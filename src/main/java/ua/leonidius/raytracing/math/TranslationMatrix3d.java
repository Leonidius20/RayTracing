package ua.leonidius.raytracing.math;

public class TranslationMatrix3d extends TransformMatrix3d {

    public TranslationMatrix3d(double dx, double dy, double dz) {
        super(new double[][]{
                new double[] {1, 0, 0, dx},
                new double[] {0, 1, 0, dy},
                new double[] {0, 0, 1, dz},
                new double[] {0, 0, 0, 1},
        });
    }

}
