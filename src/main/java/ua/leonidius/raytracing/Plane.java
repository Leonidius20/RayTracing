package ua.leonidius.raytracing;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.Ray;

public class Plane implements Shape3d {

    @Getter private final Point center;
    @Getter private final Vector3 normal;

    public Plane(Point center, Vector3 normal) {
        this.center = center;
        this.normal = normal.normalize();
    }

    @Override
    public Double findVisibleIntersectionWithRay(Ray ray) {
        Vector3 k = ray.getOrigin().toVector().subtract(center);

        double numerator = - k.dotProduct(normal);
        double denominator = ray.getDirection().dotProduct(normal);

        if (denominator == 0) {
            // ray is parallel to the plane
            return null;
        }

        double t = numerator / denominator;

        if (t < 0) return null;

        return t;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return normal;
    }

}
