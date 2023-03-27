package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.algorithm.IPrimitive;

import java.util.ArrayList;

record LeafNode(
        ArrayList<IPrimitive> leafPrimitives,
        NodeDebugInfo info//,
        /*BoundingBox aabb*/) implements INode {


    @Override
    public NodeDebugInfo debugInfo() {
        return info;
    }

}
