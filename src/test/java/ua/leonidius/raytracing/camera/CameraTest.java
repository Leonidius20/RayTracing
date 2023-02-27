package ua.leonidius.raytracing.camera;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.camera.Camera;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    void findSensorCenter_defaultDirection() {
        Point focusPoint = new Point(-2, 3, 6);
        double focusDistance = 1.5;
        int resolutionHeight = 20;
        int resolutionWidth = 20;
        double pixelHeight = 2;
        double pixelWidth = 2;

        Camera camera = new Camera(
                focusPoint, focusDistance,
                resolutionHeight, resolutionWidth,
                pixelHeight, pixelWidth);

        var expected = new Point(-2, 4.5, 6);

        assertEquals(expected, camera.findSensorCenter());

        // additional
        focusPoint = new Point(0, 0.1, 0.2);
        focusDistance = 1.5;
        resolutionHeight = 2;
        resolutionWidth = 3;
        pixelHeight = 1.1;
        pixelWidth = 1.2;
        camera = new Camera(
                focusPoint, focusDistance,
                resolutionHeight, resolutionWidth,
                pixelHeight, pixelWidth);

        expected = new Point(0, 1.6, 0.2);
        assertEquals(expected, camera.findSensorCenter());
    }

    @Test
    void findTopLeftPixelCenter_defaultDirection() {
        Point focusPoint = new Point(-2, 3, 6);
        double focusDistance = 1.5;
        int resolutionHeight = 20;
        int resolutionWidth = 20;
        double pixelHeight = 2;
        double pixelWidth = 2;

        Camera camera = new Camera(
                focusPoint, focusDistance,
                resolutionHeight, resolutionWidth,
                pixelHeight, pixelWidth);

        var expected = new Point(-21, 4.5, 25);
        var actual = camera.findTopLeftPixelCenter();

        assertEquals(expected, actual);

        // additional
        focusPoint = new Point(0, 0.1, 0.2);
        focusDistance = 1.5;
        resolutionHeight = 2;
        resolutionWidth = 3;
        pixelHeight = 1.1;
        pixelWidth = 1.2;
        camera = new Camera(
                focusPoint, focusDistance,
                resolutionHeight, resolutionWidth,
                pixelHeight, pixelWidth);

        expected = new Point(-1.2, 1.6, 0.75);
        assertEquals(expected, camera.findTopLeftPixelCenter());
    }

}