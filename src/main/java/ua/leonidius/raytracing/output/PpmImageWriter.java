package ua.leonidius.raytracing.output;

import ua.leonidius.raytracing.enitites.Color;
import ua.leonidius.raytracing.enitites.Vector3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PpmImageWriter implements ImageWriter {

    private final String outputFileName;

    public PpmImageWriter(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    @Override
    public void writeImage(Color[][] pixels) throws IOException {
        var writer = Files.newBufferedWriter(Path.of(outputFileName));

        if (pixels.length == 0) {
            System.out.println("Image empty");
            return;
        }

        // header
        writer.write("P3\n");
        writer.write(pixels[0].length + " " + pixels.length + '\n'); // width and height
        writer.write("255\n");


        for (var pixelLine : pixels) {
            for (var pixel : pixelLine) {
                writer.write(pixel.r() + " " + pixel.g() + " " + pixel.b() + '\n');
            }

        }


        writer.close();
    }

}
