package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;

import java.util.OptionalDouble;

public interface IShape3d {

    OptionalDouble findVisibleIntersectionWithRay(Ray ray);

    Normal getRealNormalAt(Point point);

    Normal getInterpolatedNormalAt(Point point);

    BoundingBox computeBoundingBox();

}
