package ua.leonidius.raytracing.primitives;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.ArrayList;
import java.util.Optional;

public class DumbAggregate extends Aggregate {

    private final BoundingBox boundingBox;

    public DumbAggregate(ArrayList<IPrimitive> primitives) {
        super(primitives);
        this.boundingBox = calcBoundingBox();
    }

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        if (boundingBox.findVisibleIntersectionWithRay(ray).isPresent()) {
            Optional<Intersection> closestIntersection = Optional.empty();

            for (var object : primitives) {
                var intersection = object
                        .findVisibleIntersectionWithRay(ray);
                if (intersection.isEmpty()) continue;

                if (closestIntersection.isEmpty()
                        || intersection.get().tParam() < closestIntersection.get().tParam()) {
                    closestIntersection = intersection;
                }
            }

            return closestIntersection;
        } else return Optional.empty();
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return null;
    }

}
