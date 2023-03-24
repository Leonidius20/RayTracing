package ua.leonidius.raytracing;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;
import ua.leonidius.raytracing.shapes.Plane;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaneTest {

    @Test
    void findVisibleIntersectionWithRay_intersectionExists() {
        var plane = new Plane(new Point(0, 0, -6), new Normal(0, 0.707106781, 0.707106781));

        var ray = new Ray(new Point(0, -20, 0), new Vector3(0, 1, 0));

        var t = plane.findVisibleIntersectionWithRay(ray);
        assertTrue(t.isPresent());
        assertTrue(Math.abs(t.getAsDouble() - 14) < 0.0001);
    }

    @Test
    void findVisibleIntersectionWithRay_noIntersection() {
        var plane = new Plane(new Point(0, 0, -6), new Normal(0, 0, 1));

        var ray = new Ray(new Point(0, -20, 0), new Vector3(0, 1, 0));

        assertNull(plane.findVisibleIntersectionWithRay(ray));
    }

    @Test
    void findVisibleIntersectionWithRay_intersectionBehindCamera() {
        var plane = new Plane(new Point(0, 0, -6), new Normal(0, 0.707106781, 0.707106781));

        var ray = new Ray(new Point(0, -20, 0), new Vector3(0, -1, 0));

        assertNull(plane.findVisibleIntersectionWithRay(ray));
    }

}