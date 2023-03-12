package ua.leonidius.raytracing.algorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.leonidius.raytracing.*;
import ua.leonidius.raytracing.camera.PerspectiveCamera;
import ua.leonidius.raytracing.enitites.*;
import ua.leonidius.raytracing.light.DirectionalLightSource;
import ua.leonidius.raytracing.shapes.Sphere;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RendererTest {

    @Test
    public void testRaysShooting() {
        IShape3d shape = mock(IShape3d.class);

        var focusPoint = new Point(0.0, 0.1, 0.2);
        double focusDistance = 1.5;
        int heightInPixels = 2;
        int widthInPixels = 3;
        double pixelHeight = 1.1;
        double pixelWidth = 1.2;

        Scene scene = new Scene(
                new PerspectiveCamera(
                        focusPoint,focusDistance, heightInPixels, widthInPixels, pixelHeight, pixelWidth),
                new DirectionalLightSource(new Vector3(0, 0, 0)));
        scene.add(new Instance(shape, new FlatShadingModel()));

        // do render
        new Renderer(scene, new TrueColorPixelRenderer(), (p, j , jj, jjj, jjjj) -> {}).render();

        // expected ray vectors (directions) (normalized)
        var a = (new Point(-1.2, 1.6, 0.75)).subtract(focusPoint).normalize();
        var b = new Point(0, 1.6, 0.75).subtract(focusPoint).normalize();
        var c = new Point(1.2, 1.6, 0.75).subtract(focusPoint).normalize();
        var d = new Point(-1.2, 1.6, -0.35).subtract(focusPoint).normalize();
        var e = new Point(0, 1.6, -0.35).subtract(focusPoint).normalize();
        var f = new Point(1.2, 1.6, -0.35).subtract(focusPoint).normalize();

        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, a));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, b));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, c));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, d));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, e));
        verify(shape).findVisibleIntersectionWithRay(new Ray(focusPoint, f));
    }

    @Test
    public void testLightCalculation() {
        var lVectorUnnormalized = new Vector3(1.0, 2.0, 3.0);

        var scene = new Scene(null, new DirectionalLightSource(lVectorUnnormalized));
        var sphere = new Sphere(new Point(2, 3, 4), 3);
        var instance = new Instance(sphere, new FlatShadingModel());
        scene.add(instance);

        var pointInQuestion = new Point(2, 3, 7);

        var pixelRenderer = new TrueColorPixelRenderer();

        double expected = 3 / lVectorUnnormalized.calculateLength();

        assertEquals(expected,
                pixelRenderer.calculateLightAt(instance, pointInQuestion, scene), 1e-7);
    }

    @Test
    public void testNearestVisibleObjectSelection() {
        var flatShading = new FlatShadingModel();

        var sphere = new Sphere(new Point(0, 0, 0), 1);
        var sphereI = new Instance(sphere, flatShading);

        var sphereBehind = new Sphere(new Point(0, 5, 0), 1);
        var sphereBehindI = new Instance(sphereBehind, flatShading);

        var ray = new Ray(new Point(0, -3, 0), new Vector3(0, 1, 0));

        var scene = new Scene(null, null);
        scene.add(sphereBehindI);
        scene.add(sphereI);

        var intersection = Renderer.findClosestIntersection(ray, scene);
        assertTrue(intersection.isPresent());
        assertEquals(sphereI, intersection.get().object());
        assertEquals(2, intersection.get().tParam()); // (0, -1, 0)
    }

    @Test
    public void onePixelSceneWithSmoothShadingAndDirectionalLight() {
        var shapes = new ArrayList<Instance>(1);

        // add triangle with normals
        var vertex1 = new Point(0, 0, 0);
        var vertex2 = new Point(0, 0, 1);
        var vertex3 = new Point(1, 1, 0);

        var normal1 = new Normal(0.5, -0.5, -0.1).normalize();
        var normal2 = new Normal(0.5, 0.1, 0.4).normalize();
        var normal3 = new Normal(0.5, -0.5, 0).normalize();

        var triangle = new Triangle(
                new Point[]{vertex1, vertex2, vertex3},
                new Normal[]{normal1, normal2, normal3}
        );

        shapes.add(new Instance(triangle, new FlatShadingModel()));

        var invertedLightDirection = new Vector3(0, -1, 0);
        var light = new DirectionalLightSource(invertedLightDirection);

        // camera
        var focusPoint = new Point(0.3, -1, 0.3);
        var camera = new PerspectiveCamera(focusPoint, 0.1, 1, 1, 1, 1);

        // scene
        var scene = new Scene(camera, light, shapes);

        // calc expected pixel value
        var expectedNormal = normal1.multiplyBy(0.3)
                .add(normal2.multiplyBy(0.3))
                .add(normal3.multiplyBy(0.4))
                .normalize(); // todo order of normals?

        Color expectedPixel = Color.grayscale(
                expectedNormal.dotProduct(invertedLightDirection));

        // actual pixel value
        Color[][] pixels = (new Renderer(scene,
                new TrueColorPixelRenderer(), (p, j , jj, jjj, jjjj) -> {})).render();
        Color actualPixelValue = pixels[0][0];

        assertEquals(expectedPixel, actualPixelValue);
    }

}