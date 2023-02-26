package ua.leonidius.raytracing.math;

import static ua.leonidius.raytracing.math.TrigonometryDegrees.cosD;
import static ua.leonidius.raytracing.math.TrigonometryDegrees.sinD;

public class RotationYMatrix extends TransformMatrix3d {

    public RotationYMatrix(double angleD) {
        super(new double[][]{
                new double[] {cosD(angleD), 0, sinD(angleD), 0},
                new double[] {0, 1, 0, 0},
                new double[] {-sinD(angleD), 0, cosD(angleD), 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
