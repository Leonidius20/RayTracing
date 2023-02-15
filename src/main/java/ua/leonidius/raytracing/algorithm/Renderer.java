package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;

public class Renderer {

    /**
     * Render scene and return pixel values
     * @param scene scene to render
     * @return array of pixel values (camera sensor width x height)
     */
    public static double[][] render(Scene scene) {
        int imageWidth = scene.getActiveCamera().getSensorWidth();
        int imageHeight = scene.getActiveCamera().getSensorHeight();

        double[][] pixels = new double[imageWidth][imageHeight];

        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                // get coordinates of the pixel on the camera's sensor


            }
        }
    }

}
