package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.shapes.Triangle;

public interface ITriangleFactory {

    Triangle make(Vector3[] vertices, Vector3[] normals); // todo: replace with points and normals

}
