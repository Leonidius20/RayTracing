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
                new DirectionalLightSource(new Vector3(0, 0, 0)));
        scene.addObject(shape);

        // do render
        new Renderer(scene).render();

        // expected ray vectors (directions) (normalized)
        var a = (new Vector3(-1.2, 1.6, 0.75)).subtract(focusPoint).normalize();
        var b = new Vector3(0, 1.6, 0.75).subtract(focusPoint).normalize();
        var c = new Vector3(1.2, 1.6, 0.75).subtract(focusPoint).normalize();
        var d = new Vector3(-1.2, 1.6, -0.35).subtract(focusPoint).normalize();
        var e = new Vector3(0, 1.6, -0.35).subtract(focusPoint).normalize();
        var f = new Vector3(1.2, 1.6, -0.35).subtract(focusPoint).normalize();

        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, a));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, b));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, c));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, d));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, e));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, f));
    }

    @Test
    public void testLightCalculation() {
        // todo
    }

    @Test
    public void testNearestVisibleObjectSelection() {
        // todo
    }

}