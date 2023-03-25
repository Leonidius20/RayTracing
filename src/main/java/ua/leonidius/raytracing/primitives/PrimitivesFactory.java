package ua.leonidius.raytracing.primitives;

import ua.leonidius.raytracing.algorithm.IShadingModel;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.spectrum.RGBSpectrum;
import ua.leonidius.raytracing.material.MatteMaterial;
import ua.leonidius.raytracing.shading.FlatShadingModel;
import ua.leonidius.raytracing.shapes.Sphere;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

// mostly used for unit tests
public final class PrimitivesFactory {

    private static final IShadingModel flatShadingModel = new FlatShadingModel();
    private static final MatteMaterial defaultMaterial = new MatteMaterial(new RGBSpectrum(1, 1, 1));

    public static Instance newTriangle(Point a, Point b, Point c) {
        return new Instance(new Triangle(a, b, c),
                flatShadingModel,
                defaultMaterial);
    }

    public static Instance newTriangle(Point[] points, Normal[] normals) {
        return new Instance(new Triangle(points, normals),
                flatShadingModel,
                defaultMaterial);
    }

    public static Instance newSphere(Point center, double radius) {
        return new Instance(new Sphere(center, radius),
                flatShadingModel, defaultMaterial);
    }

}
