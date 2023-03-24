package ua.leonidius.raytracing.shapes.factories;

import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.input.ITriangleFactory;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

public class TriangleFactory implements ITriangleFactory {

    @Override
    public Triangle make(Point[] vertices, Normal[] normals) {
        return new Triangle(vertices, normals);
    }

}
