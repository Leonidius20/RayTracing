package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.*;

public class Renderer {

    private final Scene scene;

    public Renderer(Scene scene) {
        this.scene = scene;
    }

    /**
     * Render scene and return pixel values
     * @return array of pixel values (camera sensor width x height)
     */
    public double[][] render() {
        Camera camera = scene.getActiveCamera();

        final int imageWidth = camera.getSensorWidth();
        final int imageHeight = camera.getSensorHeight();

        double[][] pixels = new double[imageHeight][imageWidth];

        final double pixelWidth = camera.getPixelWidth();
        final double pixelHeight = camera.getPixelHeight();

        Point focusPoint = camera.getFocusPoint();

        Vector3 topLeftPixelCenter = camera.findTopLeftPixelCenter();

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) { // row
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {

                // todo: remove vector creation for better performance
                Vector3 offset = new Vector3(pixelX * pixelWidth, 0, -pixelY * pixelHeight);
                // todo: offset is also dependent on rotation of camera. Maybe add different basises?

                Vector3 pixelCenter = topLeftPixelCenter.add(offset);

                // vector from focus point to pixel center
                Vector3 rayDirection = pixelCenter.subtract(focusPoint)
                        .normalize();

                Ray ray = new Ray(focusPoint, rayDirection);

                var closestIntersection = findClosestIntersection(ray, scene);

                if (closestIntersection.object != null) {
                    // calculate lighting
                    var intersectionPoint =
                            ray.getXyzOnRay(closestIntersection.tParam);

                    var lightValue = calculateLightAt(
                            closestIntersection.object, intersectionPoint);

                    // outer array contains rows (Y value), inner array contains cells from left to right (X value)
                    pixels[pixelY][pixelX] = lightValue;
                }
            }
        }

        return pixels;
    }

    /* private */ double calculateLightAt(Shape3d object, Vector3 point) {
        var normal = object.getNormalAt(point);
        return scene.getLightSource().getDirection()
                .dotProduct(normal);
    }

    record ObjectAndRayIntersection(Double tParam, Shape3d object) {};

    /* private */ static ObjectAndRayIntersection findClosestIntersection(Ray ray, Scene scene) {
        Shape3d closestObject = null;
        Double closestIntersectionTparam = null;

        for (Shape3d object : scene.getObjects()) {
            Double intersectionTparam = object.findVisibleIntersectionWithRay(ray);
            if (intersectionTparam == null) continue;
            if (closestIntersectionTparam == null
                    || intersectionTparam < closestIntersectionTparam) {
                closestIntersectionTparam = intersectionTparam;
                closestObject = object;
            }
        }

        return new ObjectAndRayIntersection(closestIntersectionTparam, closestObject);
    }

}
