package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.enitites.Color;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.primitives.Instance;

public class TrueColorPixelRenderer implements IPixelRenderer {

    @Override
    public Color renderIntersection(Scene scene, Intersection intersection) {
        var intersectionPoint = intersection.point();

        intersectionPoint = intersectionPoint.add( // fixing float point under triangle
                intersection.object().getGeometry().getRealNormalAt(
                        intersectionPoint).toVector().multiplyBy(1e-6));


        var lightValue = calculateLightAt(
                intersection.object(), intersectionPoint, scene);

        return Color.grayscale(lightValue);
    }

    /* private */ double calculateLightAt(Instance instance, Point point, Scene scene) {
        var normal = instance.getNormal(point);
        var value = normal.dotProduct(scene.getLightSource().invertedDirection());
        value = Math.max(0.0, value);

       if (value > 1e-7) {
            // send shadow ray
            var shadowRay = new Ray(point, scene.getLightSource().invertedDirection());
            // find any intersection, if found, return 0

            for (var shape : scene.getObjects()) {
                var intersection = shape.findVisibleIntersectionWithRay(shadowRay);
                // todo: replace with 'find any intersection' instead of finding the closest one
                if (intersection.isPresent()) return 0.0;
            }
       }

        return Math.max(0.0, value);
    }

}
