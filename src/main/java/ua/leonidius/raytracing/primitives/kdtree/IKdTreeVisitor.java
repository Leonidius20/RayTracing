package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.RayFragment;

public abstract class IKdTreeVisitor<T> {

    public T visit(KdTree.INode node, Ray ray, RayFragment fragment) {
        if (node instanceof KdTree.InteriorNode interiorNode) {
            return visit(interiorNode, ray, fragment);
        } else if (node instanceof KdTree.LeafNode leafNode) {
            return visit(leafNode, ray, fragment);
        }
        throw new RuntimeException("Unknown node type");
    }

    protected abstract T visit(KdTree.InteriorNode node, Ray ray, RayFragment fragment);

    protected abstract T visit(KdTree.LeafNode node, Ray ray, RayFragment fragment);

}
