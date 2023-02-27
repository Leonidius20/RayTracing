package ua.leonidius.raytracing.shapes;

import lombok.Getter;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public class Plane implements IShape3d {

    @Getter private final Point center;
    @Getter private final Vector3 normal;

    public Plane(Point center, Vector3 normal) {
        this.center = center;
        this.normal = normal.normalize();
    }

    @Override
    public OptionalDouble findVisibleIntersectionWithRay(Ray ray) {
        Vector3 k = ray.getOrigin().toVector().subtract(center);

        double numerator = - k.dotProduct(normal);
        double denominator = ray.getDirection().dotProduct(normal);

        if (denominator == 0) {
            // ray is parallel to the plane
            return OptionalDouble.empty();
        }

        double t = numerator / denominator;

        if (t < 0) return OptionalDouble.empty();

        return OptionalDouble.of(t);
    }

    @Override
    public Vector3 getNormalAt(Vector3 point, ShadingModel shading) {
        return normal.normalize();
    }

}
