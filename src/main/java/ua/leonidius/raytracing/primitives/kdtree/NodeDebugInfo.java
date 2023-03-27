package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.entities.BoundingBox;

class NodeDebugInfo {

    final int depth;
    final String type;
    final int primitivesNumber;
    boolean traversed;
    public BoundingBox aabb;

    NodeDebugInfo(int depth, String type, int primitivesNumber, BoundingBox aabb) {
        this.depth = depth;
        this.type = type;
        this.primitivesNumber = primitivesNumber;
        this.traversed = false;
        this.aabb = aabb;
    }

}
