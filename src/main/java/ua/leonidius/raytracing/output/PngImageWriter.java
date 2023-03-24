package ua.leonidius.raytracing.output;

import ua.leonidius.raytracing.entities.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngImageWriter implements ImageWriter {

    private final String outputFileName;

    public PngImageWriter(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    @Override
    public void writeImage(Color[][] pixels) throws IOException {
        if (pixels.length == 0) {
            System.out.println("Empty image");
            return;
        }

        int width = pixels[0].length;
        int height = pixels.length;

        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[0].length; x++) {
                var color = pixels[y][x];

                int col = (color.r() << 16) | (color.g() << 8) | color.b();
                img.setRGB(x, y, col);
            }
        }

        File f = new File(outputFileName);
        ImageIO.write(img, "PNG", f);
    }
}
