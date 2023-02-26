package ua.leonidius.raytracing.math;

import static ua.leonidius.raytracing.math.TrigonometryDegrees.*;

public class RotationXMatrix extends TransformMatrix3d {

    public RotationXMatrix(double angleDegrees) {
        super(new double[][]{
                new double[] {1, 0, 0, 0},
                new double[] {0, cosD(angleDegrees), -sinD(angleDegrees), 0},
                new double[] {0, sinD(angleDegrees), cosD(angleDegrees), 0},
                new double[] {0, 0, 0, 1},
        });
    }

}
