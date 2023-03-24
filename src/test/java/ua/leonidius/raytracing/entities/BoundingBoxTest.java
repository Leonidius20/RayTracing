package ua.leonidius.raytracing.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoundingBoxTest {

    @Test
    void testConstructorFindingMinAndMax() {
        var point1 = new Point(1, 2, 3);
        var point2 = new Point(2, -7, 3);

        var bb = new BoundingBox(point1, point2);
        
        var expectedMin = new Point(1, -7, 3);
        var expectedMax = new Point(2, 2, 3);

        assertEquals(bb.minPoint(), expectedMin);
        assertEquals(bb.maxPoint(), expectedMax);
    }

    @Test
    void testCombineWith() {
        var bb1 = new BoundingBox(new Point(1, -7, 3), new Point(2, 2, 3));
        var bb2 = new BoundingBox(new Point(1, -8, 4), new Point(2, 2, 4));

        var expectedBB = new BoundingBox(new Point(1, -8, 3), new Point(2, 2, 4));

        assertEquals(bb1.combineWith(bb2), expectedBB);
    }

    @Test
    void testFindVisibleIntersectionWith_noIntersection() {
        var bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        var ray = new Ray(new Point(0, 0, 0), new Vector3(0, 1, 0));

        var intersection = bb.findVisibleIntersectionWithRay(ray);

        assertTrue(intersection.isEmpty());
    }

    @Test
    void testFindVisibleIntersectionWith_intersectionExists() {
        var bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        var ray = new Ray(new Point(1.5, 0, 1.5), new Vector3(0, 1, 0));

        assertTrue(bb.findVisibleIntersectionWithRay(ray).isPresent());
    }

    @Test
    void testFindVisibleIntersectionWith_parallelRay() {
        var bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        var ray = new Ray(new Point(500, 0, 2), new Vector3(0, 1, 0));

        var intersection = bb.findVisibleIntersectionWithRay(ray);
        var result
                = intersection.isEmpty();
        assertTrue(result);

        bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        ray = new Ray(new Point(2, 0, 500), new Vector3(0, 1, 0));

        intersection = bb.findVisibleIntersectionWithRay(ray);

        assertTrue(intersection.isEmpty());

        bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        ray = new Ray(new Point(2, 0, 2), new Vector3(0, 1, 0));

        intersection = bb.findVisibleIntersectionWithRay(ray);

        assertFalse(intersection.isEmpty());
    }

    /*@Test
    void testFindVisibleIntersectionWith_rayStartsInside() {
        var bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        var ray = new Ray(new Point(1.5, 1.5, 1.5), new Vector3(0, 1, 0));

        var intersection = bb.findVisibleIntersectionWithRay(ray);

        assertTrue(intersection.isEmpty());
    }*/

    @Test
    void testFindVisibleIntersectionWith_edgeCase() {
        var bb = new BoundingBox(new Point(1, 1, 1), new Point(2, 2, 2));
        var ray = new Ray(new Point(1, 0, 1), new Vector3(0, 1, 0));

        var intersection = bb.findVisibleIntersectionWithRay(ray);

        assertTrue(intersection.isPresent());

        ray = new Ray(new Point(0, 0, 0), new Vector3(1, 1, 1).normalize());
        intersection = bb.findVisibleIntersectionWithRay(ray);
        assertTrue(intersection.isPresent());
    }

    @Test
    void testIncludePoint() {
        var bb = new BoundingBox(new Point(1, -7, 3), new Point(2, 2, 3));
        
        var additionalPoint = new Point(1.5, -8, 4);

        var expectedBB = new BoundingBox(new Point(1, -8, 3), new Point(2, 2, 4));

        assertEquals(bb.includePoint(additionalPoint), expectedBB);
    }

    @Test
    void maximumExtentTest() {
        var bb = new BoundingBox(new Point(1, 2, 3), new Point(6, 6, 4));
        assertEquals(Axis.X, bb.maximumExtentAxis());
    }

}
