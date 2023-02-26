package ua.leonidius.raytracing.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScaleMatrixTest {

    @Test
    void constructorTest() {
        double scaleX = 1.5;
        double scaleY = 9.8;
        double scaleZ = 5.44;

        var actual = new ScaleMatrix(scaleX, scaleY, scaleZ);

        var expected = new TransformMatrix3d(new double[][]{
                new double[] {scaleX, 0, 0, 0},
                new double[] {0, scaleY, 0, 0},
                new double[] {0, 0, scaleZ, 0},
                new double[] {0, 0, 0, 1},
        });

        assertEquals(expected, actual);
    }

}