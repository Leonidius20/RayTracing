package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public class Sphere implements IShape3d {

    Point center;
    double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     *
     * @param ray
     * @return null if no intersection, otherwise 't' parameter of the point of intersection (according
     * to vector formula of a line p = o + dt, where o is origin, d is direction, p is point
     */
    public OptionalDouble findVisibleIntersectionWithRay(Ray ray) {
        Vector3 originAsVector = new Vector3(ray.getOrigin().x, ray.getOrigin().y, ray.getOrigin().z);

        Vector3 k = originAsVector.subtract(center);

        // parameters of a quadratic equation
        double a = ray.getDirection().square();
        double b = 2 * ray.getDirection().dotProduct(k);
        double c = k.square() - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return OptionalDouble.empty(); // no intersection

        // find two points of intersection and see whether any of them are >= 0 (i.e. in front of
        // camera as opposed to behind it)

        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

        if (t1 < 0 && t2 < 0) {
            return OptionalDouble.empty(); // both are invisible
        }
        return OptionalDouble.of(Math.min(t1, t2));

        // return t1 >= 0 || t2 >= 0;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point, ShadingModel shading) {
        return point.subtract(center).normalize();
    }
}
