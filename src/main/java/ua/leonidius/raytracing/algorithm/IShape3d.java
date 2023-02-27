package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public interface IShape3d {

    OptionalDouble findVisibleIntersectionWithRay(Ray ray);

    Normal getNormalAt(Point point, ShadingModel shading);

}
