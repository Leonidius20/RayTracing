package ua.leonidius.raytracing.shapes;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class SphereIntersectionTest {

    @Test
    public void testFindVisibleIntersectionWithRay_simpleIntersection() {
        var sphere = new Sphere(new Point(2, 2, 2), 1);

        var rayOrigin = new Point(0, 2, 2);
        var rayDirection = new Vector3(1, 0, 0);

        var actualT = sphere.findVisibleIntersectionWithRay(new Ray(rayOrigin, rayDirection));
        assertTrue(actualT.isPresent());

        var actualIntersectionPoint = rayOrigin.add(rayDirection.multiplyBy(actualT.getAsDouble()));

        assertEquals(new Point(1.0, 2.0, 2.0), actualIntersectionPoint);
    }

    @Test
    public void testFindVisibleIntersectionWithRay_simpleNoIntersection() {
        var sphere = new Sphere(new Point(2, 2, 2), 1);

        var rayOrigin = new Point(0, 2, 2);
        var rayDirection = new Vector3(0, 0, 1);

        var actual = sphere.findVisibleIntersectionWithRay(new Ray(rayOrigin, rayDirection));
        assertFalse(actual.isPresent());
    }

    @Test
    public void testFindVisibleIntersectionWithRay_harderIntersection() {
        var sphere = new Sphere(new Point(2, 2, 1), 1.3);

        var rayOrigin = new Point(0, 0, 0);
        var rayDirection = new Vector3(1, 1, 1);

        var actual = sphere.findVisibleIntersectionWithRay(new Ray(rayOrigin, rayDirection));
        assertTrue(actual.isPresent());
        assertTrue(Math.abs(actual.getAsDouble() - 1.08261948) < 0.0001);
    }



}