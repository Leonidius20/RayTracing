package ua.leonidius.raytracing.output;

import ua.leonidius.raytracing.entities.Color;

import java.io.IOException;

// TODO: rename appropriately
public interface ImageWriter {

    void writeImage(Color[][] pixels) throws IOException;

}
