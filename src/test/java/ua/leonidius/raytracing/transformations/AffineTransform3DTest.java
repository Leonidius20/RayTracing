package ua.leonidius.raytracing.transformations;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.entities.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffineTransform3DTest {

    @Test
    void multiplyByMatrix() {
        var matrix1 = new AffineTransform3d(new double[][]{
                new double[] {4, 6, -9, 0},
                new double[] {6.56, 3, 6, 8},
                new double[] {2, 758, 8, 5},
                new double[] {0, 0, 0, 1},
        });

        var matrix2 = new AffineTransform3d(new double[][]{
                new double[] {1, 8, -5, 3},
                new double[] {0, 77, -5, 3},
                new double[] {0, 8, -5, 3},
                new double[] {0, 0, 0, 1},
        });

        var expectedResult = new AffineTransform3d(new double[][]{
                new double[] {4, 422, -5, 3},
                new double[] {6.56, 331.48, -77.8, 54.68},
                new double[] {2, 58446, -3840, 2309},
                new double[] {0, 0, 0, 1},
        });

        assertEquals(expectedResult, matrix1.combineWith(matrix2));
    }

    @Test
    void testMultiplyByVector() {
        var matrix2 = new AffineTransform3d(new double[][]{
                new double[] {1, 8, -5, 3},
                new double[] {0, 77, -5, 3},
                new double[] {0, 8, -5, 3},
                new double[] {0, 0, 0, 1},
        });

        var vector = new Point(5, 6, 7);

        var expected = new Point(21, 430, 16);

        assertEquals(expected, matrix2.applyTo(vector));

        // todo another test with non-1 4th element

        matrix2 = new AffineTransform3d(new double[][]{
                new double[] {1, 8, -5, 3},
                new double[] {0, 77, -5, 3},
                new double[] {0, 8, -5, 3},
                new double[] {5, 3, -0.7, 1},
        });

        expected = new Point(21 / 39.1, 430 / 39.1, 16 / 39.1);

        assertEquals(expected, matrix2.applyTo(vector));
    }
}