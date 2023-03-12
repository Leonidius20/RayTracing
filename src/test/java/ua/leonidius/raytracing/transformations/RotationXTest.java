package ua.leonidius.raytracing.transformations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotationXTest {

    @Test
    void constructorTest() {
        double angleDegrees = 30;
        var actual = new RotationX(angleDegrees);

        double sin = 0.5;
        double cos = 0.86602540378;

        var expected = new AffineTransform3d(new double[][]{
                new double[] {1, 0, 0, 0},
                new double[] {0, cos, sin, 0},
                new double[] {0, -sin, cos, 0},
                new double[] {0, 0, 0, 1},
        });

        assertEquals(expected, actual);
    }

}