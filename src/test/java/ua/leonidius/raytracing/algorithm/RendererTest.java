package ua.leonidius.raytracing.algorithm;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.Camera;
import ua.leonidius.raytracing.Point;
import ua.leonidius.raytracing.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class RendererTest {

    @Test
    public void testFindCameraSensorCenter_defaultDirection() {
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

        Vector3 expected = new Vector3(-2, 4.5, 6);

        assertEquals(expected, Renderer.findCameraSensorCenter(camera));
    }

    @Test
    public void testFindTopLeftPixelCenter_defaultDirection() {

    }

}