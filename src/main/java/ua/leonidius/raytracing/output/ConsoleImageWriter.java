package ua.leonidius.raytracing.output;

import ua.leonidius.raytracing.entities.Color;

public class ConsoleImageWriter implements ImageWriter {

    @Override
    public void writeImage(Color[][] pixels) {
        if (pixels.length == 0) {
            System.out.println("Empty image");
            return;
        }

        for (int i = 0; i < pixels[0].length + 2; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (var pixelLine : pixels) {
            System.out.print("|");
            for (Color pixelC : pixelLine) {
                float pixel = pixelC.toGrayscale() / 255.0f;
                if (pixel <= 0) System.out.print(" ");
                else if (pixel > 0 && pixel <= 0.2) System.out.print(".");
                else if (pixel > 0.2 && pixel < 0.5) System.out.print("*");
                else if (pixel >= 0.5 && pixel < 0.8) System.out.print("O");
                else System.out.print("█");

                // System.out.print(pixel > 0 ? "█" : " ");
            }
            System.out.println("|");
        }

        for (int i = 0; i < pixels[0].length + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

}
