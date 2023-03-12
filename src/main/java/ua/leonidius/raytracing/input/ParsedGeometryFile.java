package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.shapes.triangle.TriangleMesh;

import java.io.IOException;
import java.util.ArrayList;

public interface ParsedGeometryFile {

    TriangleMesh shapes(ITriangleFactory triangleFactory) throws IOException, ParsingException;

}
