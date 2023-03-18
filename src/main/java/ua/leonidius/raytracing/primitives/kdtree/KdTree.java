package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.ArrayList;
import java.util.Optional;

public class KdTree implements IPrimitive {

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

    private INode root;
    private BoundingBox boundingBox;
    private ArrayList<IPrimitive> primitives; // todo remove

    private KdTree() {}

    public static KdTree buildFor(ArrayList<IPrimitive> primitives) {
        var tree = new KdTree();
        tree.primitives = primitives;
        tree.boundingBox = tree.calcBoundingBox(primitives);
        tree.root = new LeafNode(primitives);
        return tree;
    }

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        var intersectionWithAABB
                = boundingBox.findVisibleIntersectionWith(ray);
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

    private BoundingBox calcBoundingBox(ArrayList<IPrimitive> primitives) {
        if (primitives.size() == 0)
            return new BoundingBox(new Point(0, 0, 0), new Point(0, 0, 0));

        var bounds = primitives.get(0).computeBoundingBox();

        for (int i = 1; i < primitives.size(); i++) {
            bounds = bounds.combineWith(primitives.get(i).computeBoundingBox());
        }

        return bounds;
    }
}
