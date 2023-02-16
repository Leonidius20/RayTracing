package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.*;

import java.util.Arrays;

public class Renderer {

    /**
     * Render scene and return pixel values
     * @param scene scene to render
     * @return array of pixel values (camera sensor width x height)
     */
    public static double[][] render(Scene scene) {
        Camera camera = scene.getActiveCamera();

        final int imageWidth = camera.getSensorWidth();
        final int imageHeight = camera.getSensorHeight();

        double[][] pixels = new double[imageWidth][imageHeight];

        final double pixelWidth = camera.getPixelWidth();
        final double pixelHeight = camera.getPixelHeight();

        Point focusPoint = camera.getFocusPoint();

        Vector3 topLeftPixelCenter = findCameraSensorCenter(camera);

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                // todo: remove vector creation for better performance
                Vector3 offset = new Vector3(pixelX * pixelWidth, 0, -pixelY * pixelHeight);
                // todo: offset is also dependent on rotation of camera. Maybe add different basises?

                Vector3 pixelCenter = topLeftPixelCenter.add(offset);

                // vector from focus point to pixel center
                Vector3 rayVector = new Vector3(pixelCenter.x - focusPoint.x, pixelCenter.y - focusPoint.y, pixelCenter.z - focusPoint.z);
                // origin point -- focus point

                for (Shape3d object : scene.getObjects()) {
                    boolean intersectionExists = object.findVisibleIntersectionWithRay(focusPoint, rayVector);
                    if (intersectionExists) {
                        pixels[pixelX][pixelY] = 1.0;
                    }
                }
            }
        }

        return pixels;
    }

    static Vector3 findCameraSensorCenter(Camera camera) {
        return camera.getDirection().multiplyBy(camera.getFocusDistance()).add(camera.getFocusPoint());
    }

    /**
     * Calculate the real-world coordinates of the center of the top-left pixel on camera's sensor
     * @return
     */
    static Vector3 findTopLeftPixelCenter(Camera camera) {
        final int imageWidth = camera.getSensorWidth();
        final int imageHeight = camera.getSensorHeight();

        var sensorCenter = findCameraSensorCenter(camera);

        final double pixelWidth = camera.getPixelWidth();
        final double pixelHeight = camera.getPixelHeight();

        final double realSensorWidth = imageWidth * pixelWidth;
        final double realSensorHeight = imageHeight * pixelHeight;

        // TODO: the plane isn't always vetical. Gotta find top left corner through vector operations
        // camera dir vector (denormalized) + left perpendicular vector + to top perp vector

        // TODO: remove vector creations, just work with numbers (for better performance)

        Vector3 offsetXY = new Vector3(-realSensorWidth / 2.0 + pixelWidth / 2.0, 0, realSensorHeight / 2.0 - pixelHeight / 2.0);

        // TODO: maybe subtract C (focus point)?

        return sensorCenter.add(offsetXY);
    }

}
