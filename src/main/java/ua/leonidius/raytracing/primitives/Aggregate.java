package ua.leonidius.raytracing.primitives;

import ua.leonidius.raytracing.BoundingBox;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.enitites.Point;

import java.util.ArrayList;

public abstract class Aggregate implements IPrimitive {

    protected final ArrayList<IPrimitive> primitives;

    protected Aggregate(ArrayList<IPrimitive> primitives) {
        this.primitives = primitives;
    }

    // todo: test
    protected BoundingBox calcBoundingBox() {
        if (primitives.size() == 0)
            return new BoundingBox(new Point(0, 0, 0), new Point(0, 0, 0));

        var bounds = primitives.get(0).computeBoundingBox();

        for (int i = 1; i < primitives.size(); i++) {
            bounds = bounds.combineWith(primitives.get(i).computeBoundingBox());
        }

        return bounds;
    }

}
