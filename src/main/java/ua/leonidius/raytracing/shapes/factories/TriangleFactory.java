package ua.leonidius.raytracing.shapes.factories;

import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.input.ITriangleFactory;
import ua.leonidius.raytracing.shapes.Triangle;

public class TriangleFactory implements ITriangleFactory {

    @Override
    public Triangle make(Vector3[] vertices, Vector3[] normals) {
        return new Triangle(vertices, normals);
    }

}
