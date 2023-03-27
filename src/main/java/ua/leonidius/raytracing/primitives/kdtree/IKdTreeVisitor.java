package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.RayFragment;

public abstract class IKdTreeVisitor<T> {

    public T visit(INode node, Ray ray, RayFragment fragment) {
        if (node instanceof InteriorNode interiorNode) {
            return visit(interiorNode, ray, fragment);
        } else if (node instanceof LeafNode leafNode) {
            return visit(leafNode, ray, fragment);
        }
        throw new RuntimeException("Unknown node type");
    }

    protected abstract T visit(InteriorNode node, Ray ray, RayFragment fragment);

    protected abstract T visit(LeafNode node, Ray ray, RayFragment fragment);

}
