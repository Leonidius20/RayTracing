package ua.leonidius.raytracing;

public interface Shape3d {

    Point findIntersectionWithRay(Point origin, Vector3 direction);

}
