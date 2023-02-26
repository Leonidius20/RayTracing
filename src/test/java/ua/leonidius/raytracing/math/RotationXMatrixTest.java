package ua.leonidius.raytracing.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotationXMatrixTest {

    @Test
    void constructorTest() {
        double angleDegrees = 30;
        var actual = new RotationXMatrix(angleDegrees);

        double sin = 0.5;
        double cos = 0.86602540378;

        var expected = new TransformMatrix3d(new double[][]{
                new double[] {1, 0, 0, 0},
                new double[] {0, cos, sin, 0},
                new double[] {0, -sin, cos, 0},
                new double[] {0, 0, 0, 1},
        });

        assertEquals(expected, actual);
    }

}