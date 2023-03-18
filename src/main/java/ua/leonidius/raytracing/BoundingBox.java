package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.Optional;

/**
 * AABB (axis-aligned bounding box). Represented by two opposite vertices
 */
public class BoundingBox {

    private Point minPoint;
    private Point maxPoint;

    private BoundingBox() {}

    public BoundingBox(Point point1, Point point2) {
        double xMin = Math.min(point1.x, point2.x);
        double xMax = Math.max(point1.x, point2.x);

        double yMin = Math.min(point1.y, point2.y);
        double yMax = Math.max(point1.y, point2.y);

        double zMin = Math.min(point1.z, point2.z);
        double zMax = Math.max(point1.z, point2.z);

        this.minPoint = new Point(xMin, yMin, zMin);
        this.maxPoint = new Point(xMax, yMax, zMax);
    }

    public BoundingBox combineWith(BoundingBox other) {
        var bb = new BoundingBox();
        bb.minPoint = new Point(
                Math.min(this.minPoint.x, other.minPoint.x),
                Math.min(this.minPoint.y, other.minPoint.y),
                Math.min(this.minPoint.z, other.minPoint.z)
        );
        bb.maxPoint = new Point(
                Math.max(this.maxPoint.x, other.maxPoint.x),
                Math.max(this.maxPoint.y, other.maxPoint.y),
                Math.max(this.maxPoint.z, other.maxPoint.z)
        );

        return bb;
    }

    public BoundingBox includePoint(Point point) {
        var bb = new BoundingBox();
        bb.minPoint = new Point(
                Math.min(this.minPoint.x, point.x),
                Math.min(this.minPoint.y, point.y),
                Math.min(this.minPoint.z, point.z)
        );
        bb.maxPoint = new Point(
                Math.max(this.maxPoint.x, point.x),
                Math.max(this.maxPoint.y, point.y),
                Math.max(this.maxPoint.z, point.z)
        );
        return bb;
    }

    public Optional<Intersection> findVisibleIntersectionWith(Ray ray) {
        throw new RuntimeException("not impl");
    }
}
