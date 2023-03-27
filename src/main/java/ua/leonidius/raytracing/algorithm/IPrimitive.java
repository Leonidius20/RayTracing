package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Ray;

import java.util.Optional;

public interface IPrimitive {

    Optional<Intersection> findVisibleIntersectionWithRay(Ray ray);

    /**
     * Find any intersection with a ray. Not necessarily the closest one.
     * Used for shadow rays. Returned intersection cannot be behind the ray origin.
     * @param ray ray to check intersection with
     * @return intersection if found, empty optional otherwise
     */
    Optional<Intersection> findAnyIntersectionWithRay(Ray ray);

    BoundingBox computeBoundingBox();

}
