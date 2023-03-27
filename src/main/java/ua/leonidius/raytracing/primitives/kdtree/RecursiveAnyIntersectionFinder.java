package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.RayFragment;

import java.util.Optional;

public class RecursiveAnyIntersectionFinder extends RecursiveClosestIntersectionFinder {

    @Override
    protected Optional<Intersection> visit(LeafNode node, Ray ray, RayFragment fragment) {
        node.info().traversed = true;

        // again idk if this is needed
        if (fragment.tMin() > fragment.tMax()) {
            return Optional.empty();
        }

        for (var object : node.leafPrimitives()) {
            var intersection = object
                    .findVisibleIntersectionWithRay(ray);
            if (intersection.isEmpty()) continue;
            if (intersection.get().tParam() > fragment.tMax()
                    || intersection.get().tParam() < fragment.tMin()) continue;

            return intersection;
        }

        return Optional.empty();
    }
}
