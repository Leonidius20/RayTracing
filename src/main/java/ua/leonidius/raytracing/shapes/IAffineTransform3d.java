package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.enitites.Point;

public interface IAffineTransform3d {

    Point applyTo(Point point);

    // TODO: applyTo(Normal) separately

}
