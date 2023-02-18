package ua.leonidius.raytracing;

public interface Shape3d {

    Double findVisibleIntersectionWithRay(Point origin, Vector3 direction);

    Vector3 getNormalAt(Vector3 point);

}
