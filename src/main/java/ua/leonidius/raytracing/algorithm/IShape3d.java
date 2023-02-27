package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public interface IShape3d {

    OptionalDouble findVisibleIntersectionWithRay(Ray ray);

    Vector3 getNormalAt(Vector3 point, ShadingModel shading);

}
