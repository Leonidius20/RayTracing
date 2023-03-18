package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.Optional;

public interface IPrimitive {

    Optional<Intersection> findVisibleIntersectionWithRay(Ray ray);

    BoundingBox computeBoundingBox();

}
