package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;

public interface IAffineTransform3d {

    Point applyTo(Point point);

    Normal applyTo(Normal normal);

    // TODO: applyTo(Normal) separately
    // is this where inverse transpose matrix is used?

}
