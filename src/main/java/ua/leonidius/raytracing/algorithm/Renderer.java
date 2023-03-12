package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Instance;
import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.enitites.Color;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.Optional;
import java.util.OptionalDouble;

public class Renderer {

    private final Scene scene;
    private final IPixelRenderer pixelRenderer;

    public Renderer(Scene scene, IPixelRenderer pixelRenderer) {
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

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) { // row
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {

               var ray = camera.getRayForPixel(pixelX, pixelY);

                var intersectionOptional =
                        findClosestIntersection(ray, scene);
                if (intersectionOptional.isEmpty()) {
                    pixels[pixelY][pixelX] = scene.getBackgroundColor();
                    continue;
                };
                var intersection = intersectionOptional.get();

                // outer array contains rows (Y value), inner array contains cells from left to right (X value)
                pixels[pixelY][pixelX] = pixelRenderer.renderIntersection(scene, intersection);
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
