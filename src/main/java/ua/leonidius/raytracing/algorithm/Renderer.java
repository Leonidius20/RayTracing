package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Instance;
import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.enitites.Color;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.enitites.Vector3;

import java.util.Optional;
import java.util.OptionalDouble;

public class Renderer {
    // TODO: what if we move this code to Camera lol? i mean, it is camera that
    // captures the image with it's sensor...

    private final Scene scene;
    private final IIntersectionVisualizer pixelRenderer;

    public Renderer(Scene scene, IIntersectionVisualizer pixelRenderer) {
        this.scene = scene;
        this.pixelRenderer = pixelRenderer;
    }

    /**
     * Render scene and return pixel values
     * @return array of pixel values (camera sensor width x height)
     */
    public Color[][] render() {
        ICamera camera = scene.getActiveCamera();

        final int imageWidth = camera.sensorWidth();
        final int imageHeight = camera.sensorHeight();

        Color[][] pixels = new Color[imageHeight][imageWidth];
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

                var intersectionOptional = findClosestIntersection(ray, scene);
                if (intersectionOptional.isEmpty()) continue;
                var intersection = intersectionOptional.get();


                // outer array contains rows (Y value), inner array contains cells from left to right (X value)
                pixels[pixelY][pixelX] = pixelRenderer.renderPixel(intersection);
            }
        }

        return pixels;
    }



    static Optional<Intersection> findAnyIntersection(Ray ray, Scene scene) {
        throw new RuntimeException("not implemented");
    }

    /* private */ static Optional<Intersection> findClosestIntersection(Ray ray, Scene scene) {
        Instance closestObject = null;
        Double closestIntersectionTparam = null;

        /*OptionalDouble closestT = scene.getObjects().stream()

                .map(obj -> obj.findVisibleIntersectionWithRay(ray)) // get intersections
                .filter(OptionalDouble::isPresent)
                .mapToDouble(OptionalDouble::getAsDouble)
                .min();*/

        for (var object : scene.getObjects()) {
            OptionalDouble intersectionTparam = object.getGeometry()
                    .findVisibleIntersectionWithRay(ray);
            if (intersectionTparam.isEmpty()) continue;
            if (closestIntersectionTparam == null
                    || intersectionTparam.getAsDouble() < closestIntersectionTparam) {
                closestIntersectionTparam = intersectionTparam.getAsDouble();
                closestObject = object;
            }
        }

        if (closestObject == null) {
            return Optional.empty();
        } else {
            var point = ray.getXyzOnRay(closestIntersectionTparam);
            return Optional.of(new Intersection(ray, closestObject, closestIntersectionTparam, point));
        }
    }

}
