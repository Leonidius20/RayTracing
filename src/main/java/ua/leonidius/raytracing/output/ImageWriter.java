package ua.leonidius.raytracing.output;

import ua.leonidius.raytracing.enitites.Color;

import java.io.IOException;

// TODO: rename appropriately
public interface ImageWriter {

    void writeImage(Color[][] pixels) throws IOException;

}
