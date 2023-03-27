package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.primitives.Aggregate;

import java.util.ArrayList;
import java.util.Optional;

public class KdTree extends Aggregate {

    public final INode root;
    private final BoundingBox boundingBox;
    private final ISplitChooser splitChooser;
    private final IKdTreeVisitor<Optional<Intersection>> visitor;
    private final IKdTreeVisitor<Optional<Intersection>> anyIntersectionFinder
            = new RecursiveAnyIntersectionFinder(); // todo remove dependency

    int maxDepth;
    int minPrimitivesNumberInLeaf;

    public KdTree(ArrayList<IPrimitive> primitives, ISplitChooser splitChooser, IKdTreeVisitor<Optional<Intersection>> visitor) {
        super(primitives);
        this.splitChooser = splitChooser;
        this.visitor = visitor;
        boundingBox = calcBoundingBox();

        maxDepth = (int) (8 + 1.3 * Math.log(primitives.size())) / 2;
        // i've added /2

        minPrimitivesNumberInLeaf = 50; //  todo choose

        root = buildTree(primitives, 0, boundingBox);
    }

    INode buildTree(ArrayList<IPrimitive> themPrimitives, int currentDepth,
                    BoundingBox currentAABB) {
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
                    currentAABB.getMaxPoint().replaceValue(splitAxis, splitCoordinate));

            var rightAABB = new BoundingBox(currentAABB.getMinPoint().replaceValue(splitAxis, splitCoordinate),
                    currentAABB.getMaxPoint());

            // create children
            // check who is on the left and who is on the right
            var primitivesOnTheLeft = new ArrayList<IPrimitive>();
            var primitivesOnTheRight = new ArrayList<IPrimitive>();

            boolean added = false;
            for (var primitive : themPrimitives) {
                added = false;
                if (primitive.computeBoundingBox().getMinPoint().getValue(splitAxis) <= splitCoordinate + 0.0001) {
                    primitivesOnTheLeft.add(primitive);
                    added = true;
                }
                // NOTE: if value is equal it's added in both
               if (primitive.computeBoundingBox().getMaxPoint().getValue(splitAxis) >= splitCoordinate - 0.0001) {
                    primitivesOnTheRight.add(primitive);
                    added = true;
               }
               if (!added) {
                   System.out.println("ERROR: primitive not added to any child");
                   primitivesOnTheLeft.add(primitive);
                   primitivesOnTheRight.add(primitive);
               }

            }

            var leftChild = buildTree(primitivesOnTheLeft, currentDepth + 1, leftAABB);
            var rightChild = buildTree(primitivesOnTheRight, currentDepth + 1, rightAABB);

            return new InteriorNode(leftChild, rightChild, splitAxis, splitCoordinate,
                    new NodeDebugInfo(currentDepth, "interior", themPrimitives.size(), currentAABB));
        }
    }

    private static LeafNode createLeaf(ArrayList<IPrimitive> themPrimitives, int currentDepth, BoundingBox box) {
        return new LeafNode(themPrimitives, new NodeDebugInfo(currentDepth, "leaf", themPrimitives.size(), box));
    }

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        var intersectionWithAABB
                = boundingBox.findVisibleIntersectionWithRay(ray);
        if (intersectionWithAABB.isEmpty()) return Optional.empty();
        var fragment = intersectionWithAABB.get();

        return visitor.visit(root, ray, fragment);
    }

    @Override
    public Optional<Intersection> findAnyIntersectionWithRay(Ray ray) {
        var intersectionWithAABB
                = boundingBox.findVisibleIntersectionWithRay(ray);
        if (intersectionWithAABB.isEmpty()) return Optional.empty();
        var fragment = intersectionWithAABB.get();

        return anyIntersectionFinder.visit(root, ray, fragment);
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return boundingBox;
    }

}
