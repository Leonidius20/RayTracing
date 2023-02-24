package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.shapes.Shape3d;

import java.io.IOException;
import java.util.ArrayList;

public interface GeometryFileParser {

    public ArrayList<Shape3d> parse() throws IOException;

}
