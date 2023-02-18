package ua.leonidius.raytracing.algorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.leonidius.raytracing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

        expected = new Vector3(0, 1.6, 0.2);
        assertEquals(expected, Renderer.findCameraSensorCenter(camera));
    }

    @Test
    public void testFindTopLeftPixelCenter_defaultDirection() {
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

        Vector3 expected = new Vector3(-21, 4.5, 25);
        Vector3 actual = Renderer.findTopLeftPixelCenter(camera);

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

        expected = new Vector3(-1.2, 1.6, 0.75);
        assertEquals(expected, Renderer.findTopLeftPixelCenter(camera));
    }

    @Test
    public void testRaysShooting() {
        Shape3d shape = mock(Shape3d.class);
        // when(shape.findVisibleIntersectionWithRay(any(Point.class), any(Vector3.class))).thenReturn(true);

        var focusPoint = new Point(0.0, 0.1, 0.2);
        double focusDistance = 1.5;
        int heightInPixels = 2;
        int widthInPixels = 3;
        double pixelHeight = 1.1;
        double pixelWidth = 1.2;

        Scene scene = new Scene(
                new Camera(
                        focusPoint,focusDistance, heightInPixels, widthInPixels, pixelHeight, pixelWidth),
                null);
        scene.addObject(shape);

        // do render
        Renderer.render(scene);

        // expected ray vectors (directions) (normalized)
        var a = (new Vector3(-1.2, 1.6, 0.75)).subtract(focusPoint).normalize();
        var b = new Vector3(0, 1.6, 0.75).subtract(focusPoint).normalize();
        var c = new Vector3(1.2, 1.6, 0.75).subtract(focusPoint).normalize();
        var d = new Vector3(-1.2, 1.6, -0.35).subtract(focusPoint).normalize();
        var e = new Vector3(0, 1.6, -0.35).subtract(focusPoint).normalize();
        var f = new Vector3(1.2, 1.6, -0.35).subtract(focusPoint).normalize();

        verify(shape).findVisibleIntersectionWithRay(focusPoint, a);
        verify(shape).findVisibleIntersectionWithRay(focusPoint, b);
        verify(shape).findVisibleIntersectionWithRay(focusPoint, c);
        verify(shape).findVisibleIntersectionWithRay(focusPoint, d);
        verify(shape).findVisibleIntersectionWithRay(focusPoint, e);
        verify(shape).findVisibleIntersectionWithRay(focusPoint, f);
    }

}