package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

public interface ITriangleFactory {

    Triangle make(Point[] vertices, Normal[] normals);

}
