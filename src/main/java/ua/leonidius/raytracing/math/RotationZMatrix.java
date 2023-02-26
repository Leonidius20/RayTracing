package ua.leonidius.raytracing.math;

import static ua.leonidius.raytracing.math.TrigonometryDegrees.cosD;
import static ua.leonidius.raytracing.math.TrigonometryDegrees.sinD;

public class RotationZMatrix extends TransformMatrix3d {

    public RotationZMatrix(double angleD) {
        super(new double[][]{
                new double[] {cosD(angleD), -sinD(angleD), 0, 0},
                new double[] {sinD(angleD), cosD(angleD), 0, 0},
                new double[] {0, 0, 1, 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
