package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.RayFragment;

public class NodeFinder extends IKdTreeVisitor<InteriorNode> {

    int currentDepth = 0;
    int targetDepth;

    @Override
    protected InteriorNode visit(InteriorNode node, Ray ray, RayFragment fragment) {
        if (currentDepth == targetDepth) {
            return node;
        } else {
            currentDepth++;
            return visit(node.rightChild(), ray, fragment);
        }
    }

    @Override
    protected InteriorNode visit(LeafNode node, Ray ray, RayFragment fragment) {
        return null;
    }

    public InteriorNode find(KdTree tree, int depth) {
        currentDepth = 0;
        targetDepth = depth;
        return visit(tree.root, null, null);
    }

}
