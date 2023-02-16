package ua.leonidius.raytracing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereIntersectionTest {

    @Test
    public void testFindVisibleIntersectionWithRay_simpleIntersection() {
        var sphere = new Sphere(new Point(2, 2, 2), 1);

        var rayOrigin = new Point(0, 2, 2);
        var rayDirection = new Vector3(1, 0, 0);

        boolean actual = sphere.findVisibleIntersectionWithRay(rayOrigin, rayDirection);
        assertTrue(actual);
    }

    @Test
    public void testFindVisibleIntersectionWithRay_simpleNoIntersection() {
        var sphere = new Sphere(new Point(2, 2, 2), 1);

        var rayOrigin = new Point(0, 2, 2);
        var rayDirection = new Vector3(0, 0, 1);

        boolean actual = sphere.findVisibleIntersectionWithRay(rayOrigin, rayDirection);
        assertFalse(actual);
    }

    @Test
    public void testFindVisibleIntersectionWithRay_harderIntersection() {
        var sphere = new Sphere(new Point(2, 2, 1), 1.3);

        var rayOrigin = new Point(0, 0, 0);
        var rayDirection = new Vector3(1, 1, 1);

        boolean actual = sphere.findVisibleIntersectionWithRay(rayOrigin, rayDirection);
        assertTrue(actual);
    }

}