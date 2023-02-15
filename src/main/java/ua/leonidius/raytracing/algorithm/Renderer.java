package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Camera;
import ua.leonidius.raytracing.Point;
import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.Vector3;

public class Renderer {

    /**
     * Render scene and return pixel values
     * @param scene scene to render
     * @return array of pixel values (camera sensor width x height)
     */
    public static double[][] render(Scene scene) {
        Camera camera = scene.getActiveCamera();

        int imageWidth = camera.getSensorWidth();
        int imageHeight = camera.getSensorHeight();

        double[][] pixels = new double[imageWidth][imageHeight];

        var sensorCenter = camera.calculateSensorCenter();

        double realSensorWidth = imageWidth * camera.getPixelWidth();
        double realSensorHeight = imageHeight * camera.getSensorHeight();

        // TODO: the plane isn't always vetical. Gotta find top left corner through vector operations
        // camera dir vector (denormalized) + left perpendicular vector + to top perp vector



        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                // get coordinates of the pixel on the camera's sensor


            }
        }
    }

}
