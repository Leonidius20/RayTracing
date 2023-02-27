package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.algorithm.IShape3d;

import java.io.IOException;
import java.util.ArrayList;

public interface ParsedGeometryFile {

    ArrayList<IShape3d> shapes(ITriangleFactory triangleFactory) throws IOException, ParsingException;

}
