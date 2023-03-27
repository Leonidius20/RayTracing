package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.entities.Axis;

record InteriorNode(INode leftChild,
                           INode rightChild,
                           Axis splitAxis, double splitCoordinate,
                           //BoundingBox aabb,
                           NodeDebugInfo info) implements INode {

    @Override
    public NodeDebugInfo debugInfo() {
        return info;
    }

}
