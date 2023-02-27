package ua.leonidius.raytracing.output;

import java.io.IOException;

// TODO: rename appropriately
public interface ImageWriter {

    void writeImage(double[][] pixels) throws IOException;

}
