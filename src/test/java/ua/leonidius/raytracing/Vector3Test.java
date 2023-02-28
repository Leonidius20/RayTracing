package ua.leonidius.raytracing;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.enitites.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    @Test
    void testCrossProduct() {
        Vector3 v1 = new Vector3(1, 2, 3);
        var v2 = new Vector3(2, 1, -1);

        var expected = new Vector3(-5, 7, -3);

        assertEquals(expected, v1.crossProduct(v2));
    }

    @Test
    void testMagnitude() {
        Vector3 v1 = new Vector3(1, 2, 3);

        double expected = 3.74165739;

        assertTrue(Math.abs(v1.calculateLength() - expected) < 1e-7);
    }
}