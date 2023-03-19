package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.primitives.Aggregate;

import java.util.ArrayList;
import java.util.Optional;

public class KdTree extends Aggregate {

    interface INode {

    }

    private enum SplitAxis {
        X, Y, Z
    }

    static class InteriorNode implements INode {
        LeafNode leftChild;
        LeafNode rightChild;
        SplitAxis splitAxis;
    }

    record LeafNode(
            ArrayList<IPrimitive> primitives) implements INode {

    }

    private final INode root;
    private final BoundingBox boundingBox;

    // private KdTree() {}

    public KdTree(ArrayList<IPrimitive> primitives) {
        super(primitives);
        boundingBox = calcBoundingBox();
        root = new LeafNode(primitives);
    }

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        var intersectionWithAABB
                = boundingBox.findVisibleIntersectionWithRay(ray);
        if (intersectionWithAABB.isEmpty()) return Optional.empty();

        // dumb code next
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
        // end of dum code
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return boundingBox;
    }


}
