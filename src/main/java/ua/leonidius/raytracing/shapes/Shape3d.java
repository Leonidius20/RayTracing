package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.Vector3;
import ua.leonidius.raytracing.algorithm.Ray;

public interface Shape3d {

    Double findVisibleIntersectionWithRay(Ray ray);

    Vector3 getNormalAt(Vector3 point, ShadingModel shading);

}
