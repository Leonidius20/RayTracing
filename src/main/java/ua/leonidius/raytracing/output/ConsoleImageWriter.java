package ua.leonidius.raytracing.output;

public class ConsoleImageWriter implements ImageWriter {

    @Override
    public void writeImage(double[][] pixels) {
        if (pixels.length == 0) {
            System.out.println("Empty image");
            return;
        }

        for (int i = 0; i < pixels[0].length + 2; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (double[] pixelLine : pixels) {
            System.out.print("|");
            for (double pixel : pixelLine) {
                System.out.print(pixel > 0 ? "█" : " ");
            }
            System.out.println("|");
        }

        for (int i = 0; i < pixels[0].length + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

}
