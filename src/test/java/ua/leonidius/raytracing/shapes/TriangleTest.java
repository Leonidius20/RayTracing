package ua.leonidius.raytracing.shapes;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.Point;
import ua.leonidius.raytracing.Vector3;
import ua.leonidius.raytracing.algorithm.Ray;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void findVisibleIntersectionWithRay() {
        var triangle = new Triangle(
                new Vector3(0,0,1),
                new Vector3(0,0,0),
                new Vector3(1,0,0)
        );

        // existing intersection
        var ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(0,1,0));
        double expectedT = 2;
        assertEquals(expectedT, triangle.findVisibleIntersectionWithRay(ray));

        // intersection on opposite direction
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(0,-1,0));
        assertNull(triangle.findVisibleIntersectionWithRay(ray));

        // no intersection (ray to the right of the triangle)
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(1,0.1,0).normalize());
        assertNull(triangle.findVisibleIntersectionWithRay(ray));

        // no intersection (ray to the bottom of the triangle)
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(0,0.5,-0.5).normalize());
        assertNull(triangle.findVisibleIntersectionWithRay(ray));

        // no intersection (ray to the left of the triangle)
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(-1,0.1,0).normalize());
        assertNull(triangle.findVisibleIntersectionWithRay(ray));

        // ray parallel to triangle
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(1,0,0));
        assertNull(triangle.findVisibleIntersectionWithRay(ray));

        // TODO: test that uncovered condition
    }

    @Test
    void getSmoothShadingNormalAt() {
    }

    @Test
    void getFlatShadingNormalAt() {
    }
}