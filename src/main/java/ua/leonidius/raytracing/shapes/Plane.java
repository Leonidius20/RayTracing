package ua.leonidius.raytracing.shapes;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.enitites.Vector3;

import java.util.OptionalDouble;

public class Plane implements IShape3d {

    @Getter private final Point center;
    @Getter private final Normal normal;

    public Plane(Point center, Normal normal) {
        this.center = center;
        this.normal = normal.normalize();
    }

    @Override
    public OptionalDouble findVisibleIntersectionWithRay(Ray ray) {
        Vector3 k = ray.getOrigin().subtract(center);

        double numerator = - normal.dotProduct(k);
        double denominator = normal.dotProduct(ray.getDirection());

        if (denominator == 0) {
            // ray is parallel to the plane
            return OptionalDouble.empty();
        }

        double t = numerator / denominator;

        if (t < 0) return OptionalDouble.empty();

        return OptionalDouble.of(t);
    }

    @Override
    public Normal getRealNormalAt(Point point) {
        return normal;
    }

    @Override
    public Normal getInterpolatedNormalAt(Point point) {
        return normal;
    }
}
