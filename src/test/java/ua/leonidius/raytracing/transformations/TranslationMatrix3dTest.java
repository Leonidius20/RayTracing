package ua.leonidius.raytracing.transformations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslationMatrix3dTest {

    @Test
    void constructorTest() {
        double dx = 1.5;
        double dy = 9.8;
        double dz = 5.44;

        var actual = new TranslationMatrix3d(dx, dy, dz);

        var expected = new AffineTransform3d(new double[][]{
                new double[] {1, 0, 0, dx},
                new double[] {0, 1, 0, dy},
                new double[] {0, 0, 1, dz},
                new double[] {0, 0, 0, 1},
        });

        assertEquals(expected, actual);
    }

}