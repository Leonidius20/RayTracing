package ua.leonidius.raytracing.math;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class TransformMatrix3dTest {

    @Test
    void multiplyByMatrix() {
        var matrix1 = new TransformMatrix3d(new double[][]{
                new double[] {4, 6, -9, 0},
                new double[] {6.56, 3, 6, 8},
                new double[] {2, 758, 8, 5},
                new double[] {0, 0, 0, 1},
        });

        var matrix2 = new TransformMatrix3d(new double[][]{
                new double[] {1, 8, -5, 3},
                new double[] {0, 77, -5, 3},
                new double[] {0, 8, -5, 3},
                new double[] {0, 0, 0, 1},
        });

        var expectedResult = new TransformMatrix3d(new double[][]{
                new double[] {4, 422, -5, 3},
                new double[] {6.56, 331.48, -77.8, 54.68},
                new double[] {2, 58446, -3840, 2309},
                new double[] {0, 0, 0, 1},
        });

        assertEquals(expectedResult, matrix1.multiplyBy(matrix2));
    }

    @Test
    void testMultiplyByVector() {
        var matrix2 = new TransformMatrix3d(new double[][]{
                new double[] {1, 8, -5, 3},
                new double[] {0, 77, -5, 3},
                new double[] {0, 8, -5, 3},
                new double[] {0, 0, 0, 1},
        });

        var vector = new Vector3(5, 6, 7);

        var expected = new double[] {21, 430, 16, 1};

        assertArrayEquals(expected, matrix2.multiplyBy(vector));
    }
}