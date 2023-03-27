package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.entities.Axis;
import ua.leonidius.raytracing.entities.BoundingBox;

import java.util.ArrayList;

public class MiddleSplitChooser implements ISplitChooser {

    @Override
    public double splitCoordinate(BoundingBox box, ArrayList<IPrimitive> primitives, Axis splitAxis) {
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
        return min + (max - min) / 2.0;
    }

}
