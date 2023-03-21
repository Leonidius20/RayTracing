package ua.leonidius.raytracing;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Axis;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.shapes.Plane;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

/**
 * AABB (axis-aligned bounding box). Represented by two opposite vertices
 */
public class BoundingBox {

    @Getter private Point minPoint;
    @Getter private Point maxPoint;

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

    public Optional<RayFragment> findVisibleIntersectionWithRay(Ray ray) {
        //throw new RuntimeException("not impl");

        /*Plane parallelToXoZMin = new Plane(minPoint, new Normal(0, -1, 0));
        Plane parallelToXoZMax = new Plane(maxPoint, new Normal(0, 1, 0));

        var parallelToYoZMin = new Plane(minPoint, new Normal(-1, 0, 0));
        var parallelToYoZMax = new Plane(maxPoint, new Normal(1, 0, 0));

        var parallelToXoYMin = new Plane(minPoint, new Normal(0, 0, -1));
        var parallelToXoYMax = new Plane(maxPoint, new Normal(0, 0, 1));

        var tXoZMin = parallelToXoZMin.findVisibleIntersectionWithRay(ray);
        var tXoZMax = parallelToXoZMax.findVisibleIntersectionWithRay(ray);

        var tYoZMin = parallelToYoZMin.findVisibleIntersectionWithRay(ray);
        var tYoZMax = parallelToYoZMax.findVisibleIntersectionWithRay(ray);
        
        var tXoYMin = parallelToXoYMin.findVisibleIntersectionWithRay(ray);
        var tXoYMax = parallelToXoYMax.findVisibleIntersectionWithRay(ray);


        double tealTmin = Stream.of(tXoZMin, tXoYMin, tYoZMin)
                .filter(OptionalDouble::isPresent)
                .mapToDouble(OptionalDouble::getAsDouble)
                .min()
                .orElse(Double.NEGATIVE_INFINITY);

        double tealTmax = Stream.of(tXoZMax, tXoYMax, tYoZMax)
                .filter(OptionalDouble::isPresent)
                .mapToDouble(OptionalDouble::getAsDouble)
                .max()
                .orElse(Double.POSITIVE_INFINITY);

        if (tealTmin > tealTmax) {
            return Optional.empty();
        }       

        return Optional.of(new Intersection(ray, null, tealTmin, ray.getXyzOnRay(tealTmin)));*/

        double tmin = Double.NEGATIVE_INFINITY, tmax = Double.POSITIVE_INFINITY;

        //if (ray.getDirection().x != 0.0) {
            double tx1 = (minPoint.x - ray.getOrigin().x)/ray.getDirection().x;
            double tx2 = (maxPoint.x - ray.getOrigin().x)/ray.getDirection().x;

            tmin = Math.max(tmin, Math.min(tx1, tx2));
            tmax = Math.min(tmax, Math.max(tx1, tx2));
        //}

        //if (ray.getDirection().y != 0.0) {
            double ty1 = (minPoint.y - ray.getOrigin().y)/ray.getDirection().y;
            double ty2 = (maxPoint.y - ray.getOrigin().y)/ray.getDirection().y;

            tmin = Math.max(tmin, Math.min(ty1, ty2));
            tmax = Math.min(tmax, Math.max(ty1, ty2));
        //}

        //if (ray.getDirection().z != 0.0) {
            double tz1 = (minPoint.z - ray.getOrigin().z)/ray.getDirection().z;
            double tz2 = (maxPoint.z - ray.getOrigin().z)/ray.getDirection().z;

            tmin = Math.max(tmin, Math.min(tz1, tz2));
            tmax = Math.min(tmax, Math.max(tz1, tz2));
        //}
        if (tmax >= Math.max(0.0, tmin)) { // it has to be this way because of NaN behavior
            return Optional.of(new RayFragment(tmin, tmax));
        } else {
            return Optional.empty();
        }

        /*if (tmax < 0) {
            return Optional.empty();
        }

        if (tmin > tmax) {
            return Optional.empty();
        }

        // return Optional.of(new Intersection(ray, null, tmin, ray.getXyzOnRay(tmin)));
        return Optional.of(new RayFragment(tmin, tmax));*/
    }

    Point minPoint() {
        return minPoint;
    }

    Point maxPoint() {
        return maxPoint;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BoundingBox other = (BoundingBox) obj;
        return this.minPoint.equals(other.minPoint) && this.maxPoint.equals(other.maxPoint);
    }

    @Override
    public int hashCode() {
        return minPoint.hashCode() + maxPoint.hashCode();
    }

    public Axis maximumExtentAxis() {
        var currentMaxExtent = Axis.X;
        var currentMaxEdge = maxPoint.x - minPoint.x;

        // y
        var yEdge = maxPoint.y - minPoint.y;
        if (yEdge > currentMaxEdge) {
            currentMaxEdge = yEdge;
            currentMaxExtent = Axis.Y;
        }

        var zEdge = maxPoint.z - minPoint.z;
        if (zEdge > currentMaxEdge) {
            // currentMaxEdge = zEdge;
            currentMaxExtent = Axis.Z;
        }

        return currentMaxExtent;
    }



}
