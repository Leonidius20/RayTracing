package ua.leonidius.raytracing;

public class Sphere implements Shape3d {

    Point center;
    double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point findIntersectionWithRay(Point origin, Vector3 direction) {

    }

}
