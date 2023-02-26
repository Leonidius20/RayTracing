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

    public TranslationMatrix3d() {
        super();
        this.data[0][0] = 1;
        this.data[1][1] = 1;
        this.data[2][2] = 1;
    }

    public void setXTranslation(double dx) {
        this.data[0][3] = dx;
    }

    public void setYTranslation(double dy) {
        this.data[1][3] = dy;
    }

    public void setZTranslation(double dz) {
        this.data[2][3] = dz;
    }

}
