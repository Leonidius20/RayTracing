package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.enitites.Axis;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.primitives.Aggregate;

import java.util.ArrayList;
import java.util.Optional;

public class KdTree extends Aggregate {

    public interface INode {
        // Optional<Intersection> checkIntersection(Ray ray, RayFragment fragment);
        NodeDebugInfo debugInfo();
        BoundingBox boundingBox();
    }

    public record InteriorNode(INode leftChild,
                        INode rightChild,
                        Axis splitAxis, double splitCoordinate,
                        BoundingBox aabb,
                        NodeDebugInfo info) implements INode {

        /*@Override
        public Optional<Intersection> checkIntersection(Ray ray, RayFragment fragment) {
            this.info.traversed = true;

            // idk when this happens but who knows maybe it does
            if (fragment.tMin() > fragment.tMax()) {
                return Optional.empty();
            }

            // compute distance to split plane along ray
            double tSplit = (splitCoordinate - ray.getOrigin().getValue(splitAxis))
                    / ray.getDirection().getValue(splitAxis); // todo: can there be a division by zero?

            boolean isLeftChildFirst = ray.getOrigin().getValue(splitAxis) < splitCoordinate
                    || (ray.getOrigin().getValue(splitAxis) == splitCoordinate && ray.getDirection().getValue(splitAxis) <= 0);

            INode firstChild = isLeftChildFirst ? leftChild : rightChild;
            INode secondChild = isLeftChildFirst ? rightChild : leftChild;

            // check whether we need to check both children or just one

            // if the ray enters and exits only the left sub-box
            // or if it goes in inverse direction starting from the split plane
            if (tSplit > fragment.tMax() || tSplit <= 0) {
                return firstChild.checkIntersection(ray, fragment);
            } else if (tSplit < fragment.tMin()) { // if the ray enters and exits only the right sub box
                return secondChild.checkIntersection(ray, fragment);
            } else {
                // if the ray enters both sub-boxes
                // check the first child
                var intersection = firstChild.checkIntersection(ray, new RayFragment(fragment.tMin(), tSplit));
                if (intersection.isPresent()) {
                    return intersection;
                }

                // check the second child
                return secondChild.checkIntersection(ray, new RayFragment(tSplit, fragment.tMax()));
            }
        }*/

        @Override
        public NodeDebugInfo debugInfo() {
            return info;
        }

        @Override
        public BoundingBox boundingBox() {
            return aabb;
        }
    }

    record LeafNode(
            ArrayList<IPrimitive> leafPrimitives,
            NodeDebugInfo info, BoundingBox aabb) implements INode {

        /*@Override
        public Optional<Intersection> checkIntersection(Ray ray, RayFragment fragment) {
            this.info.traversed = true;

            Optional<Intersection> closestIntersection = Optional.empty();

            // again idk if this is needed
            if (fragment.tMin() > fragment.tMax()) {
                return Optional.empty();
            }

            for (var object : leafPrimitives) {
                var intersection = object
                        .findVisibleIntersectionWithRay(ray);
                if (intersection.isEmpty()) continue;

                if (closestIntersection.isEmpty()
                        || intersection.get().tParam() < closestIntersection.get().tParam()) {
                    closestIntersection = intersection;
                }
            }

            return closestIntersection;
        }*/

        @Override
        public NodeDebugInfo debugInfo() {
            return info;
        }

        @Override
        public BoundingBox boundingBox() {
            return aabb;
        }
    }

    static class NodeDebugInfo {
        final int depth;
        final String type;
        final int primitivesNumber;
        boolean traversed;

        private NodeDebugInfo(int depth, String type, int primitivesNumber) {
            this.depth = depth;
            this.type = type;
            this.primitivesNumber = primitivesNumber;
            this.traversed = false;
        }
    }

    /**
     * An interface for an object that can choose where to split the box
     * (e.g. in the middle, according to SAH and so on)
     */
    interface SplitChooser {
        double splitCoordinate(BoundingBox box, ArrayList<IPrimitive> primitives, Axis splitAxis);
    }

    public final INode root;
    private final BoundingBox boundingBox;
    private final SplitChooser splitChooser;
    private final IKdTreeVisitor<Optional<Intersection>> visitor;

    int maxDepth;
    int minPrimitivesNumberInLeaf;

    // private KdTree() {}

    public KdTree(ArrayList<IPrimitive> primitives, SplitChooser splitChooser, IKdTreeVisitor<Optional<Intersection>> visitor) {
        super(primitives);
        this.splitChooser = splitChooser;
        this.visitor = visitor;
        boundingBox = calcBoundingBox();

        maxDepth = 6;// todo remove
        //maxDepth = (int) (8 + 1.3 * Math.log(primitives.size())) / 2;
        // i've added /2

        minPrimitivesNumberInLeaf = 50; //  todo choose

        root = buildTree(primitives, 0, boundingBox);


    }

    INode buildTree(ArrayList<IPrimitive> themPrimitives, int currentDepth, BoundingBox currentAABB) {
        // return new LeafNode(primitives);

        if (themPrimitives.size() < minPrimitivesNumberInLeaf
                || currentDepth == maxDepth) { // todo: maybe add a minimum box size for splitting?
            // create a leaf node
            return createLeaf(themPrimitives, currentDepth, currentAABB);
        } else {
            // create an intermediate node

            // choose split axis
            var splitAxis = currentAABB.maximumExtentAxis();
            double splitCoordinate = splitChooser.splitCoordinate(currentAABB, themPrimitives, splitAxis);

            if (splitCoordinate == -1) { // good split not found
                // create a leaf instead
                return createLeaf(themPrimitives, currentDepth, currentAABB);
            }

            var leftAABB = new BoundingBox(currentAABB.getMinPoint(),
                    replaceValueInPoint(currentAABB.getMaxPoint(), splitAxis, splitCoordinate));

            var rightAABB = new BoundingBox(replaceValueInPoint(currentAABB.getMinPoint(), splitAxis, splitCoordinate),
                    currentAABB.getMaxPoint());

            // create children
            // check who is on the left and who is on the right
            var primitivesOnTheLeft = new ArrayList<IPrimitive>();
            var primitivesOnTheRight = new ArrayList<IPrimitive>();

            for (var primitive : themPrimitives) {
                if (primitive.computeBoundingBox().getMinPoint().getValue(splitAxis) <= splitCoordinate) {
                    primitivesOnTheLeft.add(primitive);
                }
                // NOTE: if value is equal it's added in both
                if (primitive.computeBoundingBox().getMaxPoint().getValue(splitAxis) >= splitCoordinate) {
                    primitivesOnTheRight.add(primitive);
                }
            }

            //todo

            var leftChild = buildTree(primitivesOnTheLeft, currentDepth + 1, leftAABB);
            var rightChild = buildTree(primitivesOnTheRight, currentDepth + 1, rightAABB);

            return new InteriorNode(leftChild, rightChild, splitAxis, splitCoordinate, currentAABB,
                    new NodeDebugInfo(currentDepth, "interior", themPrimitives.size()));
        }
    }

    /* private */ static BoundingBox createLeftSubBox(BoundingBox box, Axis splitAxis, double splitCoordinate) {
        return new BoundingBox(box.getMinPoint(),
                replaceValueInPoint(box.getMaxPoint(), splitAxis, splitCoordinate));
    }

    /* private */ static BoundingBox createRightSubBox(BoundingBox box, Axis splitAxis, double splitCoordinate) {
        return new BoundingBox(replaceValueInPoint(box.getMinPoint(), splitAxis, splitCoordinate),
                box.getMaxPoint());
    }

    private static LeafNode createLeaf(ArrayList<IPrimitive> themPrimitives, int currentDepth, BoundingBox box) {
        return new LeafNode(themPrimitives, new NodeDebugInfo(currentDepth, "leaf", themPrimitives.size()), box);
    }

    private static Point replaceValueInPoint(Point point, Axis axis, double newValue) {
        switch (axis) {
            case X -> {
                return new Point(newValue, point.y, point.z);
            }
            case Y -> {
                return new Point(point.x, newValue, point.z);
            }
            case Z -> {
                return new Point(point.x, point.y, newValue);
            }
        }
        throw new RuntimeException(); // unreachable
    }

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        var intersectionWithAABB
                = boundingBox.findVisibleIntersectionWithRay(ray);
        if (intersectionWithAABB.isEmpty()) return Optional.empty();
        var fragment = intersectionWithAABB.get();

        // return root.checkIntersection(ray, fragment);
        return visitor.visit(root, ray, fragment);
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return boundingBox;
    }


}
