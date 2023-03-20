package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.enitites.Axis;

import java.util.ArrayList;

public class MiddleSplitChooser implements KdTree.SplitChooser {

    @Override
    public double splitPosition(BoundingBox box, ArrayList<IPrimitive> primitives, Axis splitAxis) {
        double min, max;
        switch (splitAxis) {
            case X -> {
                min = box.getMinPoint().x;
                max = box.getMaxPoint().x;
            }
            case Y -> {
                min = box.getMinPoint().y;
                max = box.getMaxPoint().y;
            }
            case Z -> {
                min = box.getMinPoint().z;
                max = box.getMaxPoint().z;
            }
            default -> throw new RuntimeException("Axis other than X Y or Z");
        }
        return (max - min) / 2.0;
    }

}
