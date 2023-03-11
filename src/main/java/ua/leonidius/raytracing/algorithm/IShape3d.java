package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public interface IShape3d {

    OptionalDouble findVisibleIntersectionWithRay(Ray ray);

    Normal getRealNormalAt(Point point);

    Normal getInterpolatedNormalAt(Point point);

    // TODO: watch lecture about transformations once more
    // and find out what should be transformed and how

}
