package ua.leonidius.raytracing;

public interface Shape3d {

    boolean findVisibleIntersectionWithRay(Point origin, Vector3 direction);

}
