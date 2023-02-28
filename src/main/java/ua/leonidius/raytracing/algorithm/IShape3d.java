package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.transformations.*;

import java.util.OptionalDouble;

public interface IShape3d {

    OptionalDouble findVisibleIntersectionWithRay(Ray ray);

    Normal getNormalAt(Point point, ShadingModel shading);

    // TODO: watch lecture about transformations once more
    // and find out what should be transformed and how

}
