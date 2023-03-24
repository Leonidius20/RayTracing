package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.entities.Color;
import ua.leonidius.raytracing.entities.Ray;

import java.util.Arrays;
import java.util.Optional;

public class Renderer {

    private final Scene scene;
    private final IPixelRenderer pixelRenderer;
    private final IMonitoringCallback monitor;

    public Renderer(Scene scene, IPixelRenderer pixelRenderer, IMonitoringCallback monitor) {
        this.scene = scene;
        this.pixelRenderer = pixelRenderer;
        this.monitor = monitor;
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
        for (Color[] row : pixels) {
            Arrays.fill(row, Color.BLACK);
        }
        // set bg color?

        int pixelCount = 0;
        int startX = 0;
        int startY = 0;// for monitoring

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) { // row
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                pixelCount++;
                if (pixelCount >= 400) {
                    pixelCount = 0;
                    monitor.shareProgress(pixels, startX, startY, pixelX - 1, pixelY - 1);
                    startX = pixelX;
                    startY = pixelY;
                }

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

        monitor.shareProgress(pixels, startX, startY, imageWidth - 1, imageHeight - 1);

        return pixels;
    }

    /* private */ static Optional<Intersection> findClosestIntersection(Ray ray, Scene scene) {
        Optional<Intersection> closestIntersection = Optional.empty();

        for (var object : scene.getObjects()) {
            var intersection = object
                    .findVisibleIntersectionWithRay(ray);
            if (intersection.isEmpty()) continue;

            if (closestIntersection.isEmpty()
                    || intersection.get().tParam() < closestIntersection.get().tParam()) {
                closestIntersection = intersection;
            }
        }

        return closestIntersection;
    }

}
