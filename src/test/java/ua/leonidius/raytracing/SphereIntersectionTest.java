package ua.leonidius.raytracing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereIntersectionTest {

    @Test
    public void testFindVisibleIntersectionWithRay_simpleIntersection() {
        var sphere = new Sphere(new Point(2, 2, 2), 1);

        var rayOrigin = new Point(0, 2, 2);
        var rayDirection = new Vector3(1, 0, 0);

        double actualT = sphere.findVisibleIntersectionWithRay(rayOrigin, rayDirection);
        System.out.println("Acutual T: " + actualT);

        var actualIntersectionPoint = rayDirection.multiplyBy(actualT).add(rayOrigin);

        assertEquals(new Vector3(1.0, 2.0, 2.0), actualIntersectionPoint);
    }

    @Test
    public void testFindVisibleIntersectionWithRay_simpleNoIntersection() {
        var sphere = new Sphere(new Point(2, 2, 2), 1);

        var rayOrigin = new Point(0, 2, 2);
        var rayDirection = new Vector3(0, 0, 1);

        Double actual = sphere.findVisibleIntersectionWithRay(rayOrigin, rayDirection);
        assertNull(actual);
    }

    @Test
    public void testFindVisibleIntersectionWithRay_harderIntersection() {
        var sphere = new Sphere(new Point(2, 2, 1), 1.3);

        var rayOrigin = new Point(0, 0, 0);
        var rayDirection = new Vector3(1, 1, 1);

        Double actual = sphere.findVisibleIntersectionWithRay(rayOrigin, rayDirection);
        assertTrue(Math.abs(actual - 1.08261948) < 0.0001);
    }



}