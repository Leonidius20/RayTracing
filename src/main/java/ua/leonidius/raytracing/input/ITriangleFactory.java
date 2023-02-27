package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.shapes.Triangle;

public interface ITriangleFactory {

    Triangle make(Point[] vertices, Normal[] normals); // todo: replace with points and normals

}
