package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Ray;

import java.util.Optional;

public interface IPrimitive {

    Optional<Intersection> findVisibleIntersectionWithRay(Ray ray);

    BoundingBox computeBoundingBox();

}
