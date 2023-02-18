package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.Ray;

public interface Shape3d {

    Double findVisibleIntersectionWithRay(Ray ray);

    Vector3 getNormalAt(Vector3 point);

}
