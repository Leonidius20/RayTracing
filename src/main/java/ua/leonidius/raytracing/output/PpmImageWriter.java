package ua.leonidius.raytracing.output;

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
    public void writeImage(double[][] pixels) throws IOException {
        var writer = Files.newBufferedWriter(Path.of(outputFileName));

        if (pixels.length == 0) {
            System.out.println("Image empty");
            return;
        }

        // header
        writer.write("P3\n");
        writer.write(pixels[0].length + " " + pixels.length + '\n'); // width and height
        writer.write("255\n");


        for (double[] pixelLine : pixels) {
            for (double pixel : pixelLine) {
                Vector3 rgb = new Vector3(255, 255, 255);
                rgb = rgb.multiplyBy(pixel);

                writer.write(rgb.x + " " + rgb.y + " " + rgb.z + '\n');
            }

        }


        writer.close();
    }

}
