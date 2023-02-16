package ua.leonidius.raytracing;

public class Sphere implements Shape3d {

    Point center;
    double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     *
     * @param origin
     * @param direction
     * @return null if no intersection, otherwise 't' parameter of the point of intersection (according
     * to vector formula of a line p = o + dt, where o is origin, d is direction, p is point
     */
    public boolean findVisibleIntersectionWithRay(Point origin, Vector3 direction) {
        Vector3 originAsVector = new Vector3(origin.x, origin.y, origin.z);

        Vector3 k = originAsVector.subtract(center);

        // parameters of a quadratic equation
        double a = direction.square();
        double b = 2 * direction.dotProduct(k);
        double c = k.square() - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return false; // no intersection

        // find two points of intersection and see whether any of them are >= 0 (i.e. in front of
        // camera as opposed to behind it)

        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

        return t1 >= 0 || t2 >= 0;
    }

}
