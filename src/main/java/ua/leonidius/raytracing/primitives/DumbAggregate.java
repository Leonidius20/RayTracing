package ua.leonidius.raytracing.primitives;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.Optional;

public class DumbAggregate implements IPrimitive {

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        return Optional.empty();
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return null;
    }

}
