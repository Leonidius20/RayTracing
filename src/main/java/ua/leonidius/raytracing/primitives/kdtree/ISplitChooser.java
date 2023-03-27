package ua.leonidius.raytracing.primitives.kdtree;

import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.entities.Axis;
import ua.leonidius.raytracing.entities.BoundingBox;

import java.util.ArrayList;

/**
 * An interface for an object that can choose where to split the box
 * (e.g. in the middle, according to SAH and so on)
 */
public interface ISplitChooser {

    double splitCoordinate(BoundingBox box, ArrayList<IPrimitive> primitives, Axis splitAxis);


}
