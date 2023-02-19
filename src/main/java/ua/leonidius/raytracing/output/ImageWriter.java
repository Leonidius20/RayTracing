package ua.leonidius.raytracing.output;

import java.io.IOException;

public interface ImageWriter {

    void writeImage(double[][] pixels) throws IOException;

}
