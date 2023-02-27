package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.enitites.Vector3;

import java.util.OptionalDouble;

public class Renderer {
    // TODO: what if we move this code to Camera lol? i mean, it is camera that
    // captures the image with it's sensor...

    private final Scene scene;
    private final ShadingModel shading;

    public Renderer(Scene scene, ShadingModel shading) {
        this.scene = scene;
        this.shading = shading;
    }

    /**
     * Render scene and return pixel values
     * @return array of pixel values (camera sensor width x height)
     */
    public double[][] render() {
        ICamera camera = scene.getActiveCamera();

        final int imageWidth = camera.sensorWidth();
        final int imageHeight = camera.sensorHeight();

        double[][] pixels = new double[imageHeight][imageWidth];
        // set bg color?

        final double pixelWidth = camera.pixelWidth();
        final double pixelHeight = camera.pixelHeight();

        Point focusPoint = camera.focusPoint();

        var topLeftPixelCenter = camera.findTopLeftPixelCenter();

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) { // row
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {

                // todo: remove vector creation for better performance
                Vector3 offset = new Vector3(pixelX * pixelWidth, 0, -pixelY * pixelHeight);
                // todo: offset is also dependent on rotation of camera. Maybe add different basises?

                var pixelCenter = topLeftPixelCenter.add(offset);

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

    /* private */ double calculateLightAt(IShape3d object, Point point) {
        var normal = object.getNormalAt(point, shading);
        var value = normal.dotProduct(scene.getLightSource().invertedDirection());
        return Math.max(0.0, value);
    }

    record ObjectAndRayIntersection(Double tParam, IShape3d object) {};

    /* private */ static ObjectAndRayIntersection findClosestIntersection(Ray ray, Scene scene) {
        IShape3d closestObject = null;
        Double closestIntersectionTparam = null;

        for (IShape3d object : scene.getObjects()) {
            OptionalDouble intersectionTparam = object.findVisibleIntersectionWithRay(ray);
            if (intersectionTparam.isEmpty()) continue;
            if (closestIntersectionTparam == null
                    || intersectionTparam.getAsDouble() < closestIntersectionTparam) {
                closestIntersectionTparam = intersectionTparam.getAsDouble();
                closestObject = object;
            }
        }

        return new ObjectAndRayIntersection(closestIntersectionTparam, closestObject);
    }

}
