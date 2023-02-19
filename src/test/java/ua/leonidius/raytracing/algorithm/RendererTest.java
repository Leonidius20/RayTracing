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
        when(shape.getNormalAt(any())).thenReturn(new Vector3(0, 0, 0)); // to avoid NPE

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
        var scene = new Scene(null, new DirectionalLightSource(new Vector3(1.0, 2.0, 3.0)));
        var sphere = new Sphere(new Point(2, 3, 4), 3);
        scene.addObject(sphere);

        var pointInQuestion = new Vector3(2, 3, 7);

        var renderer = new Renderer(scene);

        assertEquals(3, renderer.calculateLightAt(sphere, pointInQuestion));
    }

    @Test
    public void testNearestVisibleObjectSelection() {
        var sphere = new Sphere(new Point(0, 0, 0), 1);

        var sphereBehind = new Sphere(new Point(0, 5, 0), 1);

        var ray = new Ray(new Point(0, -3, 0), new Vector3(0, 1, 0));

        var scene = new Scene(null, null);
        scene.addObject(sphereBehind);
        scene.addObject(sphere);

        var intersection = Renderer.findClosestIntersection(ray, scene);
        assertEquals(sphere, intersection.object());
        assertEquals(2, intersection.tParam()); // (0, -1, 0)
    }

}