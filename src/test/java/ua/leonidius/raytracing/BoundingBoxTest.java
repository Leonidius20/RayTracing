package ua.leonidius.raytracing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Axis;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.enitites.Vector3;

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
