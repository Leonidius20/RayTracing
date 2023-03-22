package ua.leonidius.raytracing.shapes.triangle;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void findVisibleIntersectionWithRay() {
        var triangle = new Triangle(
                new Point(0,0,1),
                new Point(0,0,0),
                new Point(1,0,0)
        );

        // existing intersection
        var ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(0,1,0));
        double expectedT = 2;

        OptionalDouble actual = triangle.findVisibleIntersectionWithRay(ray);
        assertTrue(actual.isPresent());
        assertEquals(expectedT, actual.getAsDouble(), 1e-7); // todo: with precision

        // intersection on opposite direction
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(0,-1,0));
        assertTrue(triangle.findVisibleIntersectionWithRay(ray).isEmpty());

        // no intersection (ray to the right of the triangle)
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(1,0.1,0).normalize());
        assertTrue(triangle.findVisibleIntersectionWithRay(ray).isEmpty());

        // no intersection (ray to the bottom of the triangle)
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(0,0.5,-0.5).normalize());
        assertTrue(triangle.findVisibleIntersectionWithRay(ray).isEmpty());

        // no intersection (ray to the left of the triangle)
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(-1,0.1,0).normalize());
        assertTrue(triangle.findVisibleIntersectionWithRay(ray).isEmpty());

        // ray parallel to triangle
        ray = new Ray(new Point(0.5, -2, 0.1), new Vector3(1,0,0));
        assertTrue(triangle.findVisibleIntersectionWithRay(ray).isEmpty());

        // TODO: test that uncovered condition
    }

    @Test
    void testBarycentricCoordinates() { // todo remove
        var triangle = new Triangle(
                new Point(0, 0, 0),
                new Point(0, 0, 1),
                new Point(1, 0, 0)
        );

        double[] expected = new double[] {0.3, 0.3, 0.4};

        var point = new Point(0.3, 0, 0.3);

        assertArrayEquals(expected, triangle.getBarycentricCoordinates(point), 1e-7);
    }

    /*@Test
    void getSmoothShadingNormalAt() {
        var normal1 = new Normal(4, 5, 6).normalize();
        var normal2 = new Normal(5, -9, 0).normalize();
        var normal3 = new Normal(-5, 6, 8.7).normalize();

        var triangle = new Triangle(
                new Point[]{
                    new Point(0, 0, 0),
                    new Point(0, 0, 1),
                    new Point(1, 0, 0)
                },
                new Normal[]{ normal1, normal2, normal3 }
        );

        var point = new Point(0.3, 0, 0.3);

        double expectedU = 0.3;
        double expectedV = 0.3;
        double expectedW = 0.4;

        var expected = normal1.multiplyBy(expectedU)
                .add(normal2.multiplyBy(expectedV))
                .add(normal3.multiplyBy(expectedW))
                .normalize();

        assertEquals(expected, triangle.getInterpolatedNormalAt(point));
    }*/

    @Test
    void getFlatShadingNormalAt() {
    }
}