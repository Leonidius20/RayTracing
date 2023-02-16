package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.*;

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

        final double realSensorWidth = imageWidth * camera.getSensorWidth();
        final double realSensorHeight = imageHeight * camera.getSensorHeight();

        final double pixelWidth = camera.getPixelWidth();
        final double pixelHeight = camera.getPixelHeight();

        Point focusPoint = camera.getFocusPoint();

        Vector3 topLeftPixelCenter = findCameraSensorCenter(camera);

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                Vector3 offset = new Vector3(pixelX * pixelWidth, 0, -pixelY * pixelHeight);

                Vector3 pixelCenter = topLeftPixelCenter.add(offset);

                // vector from focus point to pixel center
                Vector3 rayVector = new Vector3(pixelCenter.x - focusPoint.x, pixelCenter.y - focusPoint.y, pixelCenter.z - focusPoint.z);
                // origin point -- focus point

                for (Shape3d object : scene.getObjects()) {
                    // todo: choose the closest one
                    // and check if it intersects with object
                    Point intersection = object.findIntersectionWithRay(focusPoint, rayVector);

                    // check that intersection is not behind camera

                    // pixels[pixelX][pixelY] = intersection == null ? 0 : 1;
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

        final double realSensorWidth = imageWidth * camera.getSensorWidth();
        final double realSensorHeight = imageHeight * camera.getSensorHeight();

        final double pixelWidth = camera.getPixelWidth();
        final double pixelHeight = camera.getPixelHeight();

        Point focusPoint = camera.getFocusPoint();

        // TODO: the plane isn't always vetical. Gotta find top left corner through vector operations
        // camera dir vector (denormalized) + left perpendicular vector + to top perp vector

        Vector3 horizontalOffset = new Vector3(-realSensorWidth / 2.0 + pixelWidth / 2.0, 0, 0);
        Vector3 verticalOffset = new Vector3(0, 0, realSensorHeight / 2.0 - pixelHeight / 2.0);

        // TODO: maybe subtract C (focus point)?

        return sensorCenter.add(horizontalOffset).add(verticalOffset);
    }

}
