package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Instance;
import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.IShadingModel;
import ua.leonidius.raytracing.enitites.Color;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public class TrueColorPixelRenderer implements IIntersectionVisualizer {

    private final Scene scene; // todo: remove both

    public TrueColorPixelRenderer(Scene scene) {
        this.scene = scene;
    }

    @Override
    public Color renderPixel(Intersection intersection) {
        var intersectionPoint = intersection.point();

        intersectionPoint = intersectionPoint.add( // fixing float point under triangle
                intersection.object().getGeometry().getRealNormalAt(
                        intersectionPoint).toVector().multiplyBy(1e-6));


        var lightValue = calculateLightAt(
                intersection.object(), intersectionPoint);

        return Color.grayscale(lightValue);
    }

    /* private */ double calculateLightAt(Instance instance, Point point) {
        var normal = instance.getNormal(point);
        var value = normal.dotProduct(scene.getLightSource().invertedDirection());
        value = Math.max(0.0, value);

        if (value > 1e-7) {
            // send shadow ray
            var shadowRay = new Ray(point, scene.getLightSource().invertedDirection());
            // find any intersection, if found, return 0

            for (var shape : scene.getObjects()) {
                OptionalDouble intersectionTparam = shape.getGeometry().findVisibleIntersectionWithRay(shadowRay);
                if (intersectionTparam.isPresent()) return 0.0;
            }
        }

        return Math.max(0.0, value);
    }

}
