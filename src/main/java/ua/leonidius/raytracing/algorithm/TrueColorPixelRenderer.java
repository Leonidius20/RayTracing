package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.entities.Color;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.spectrum.RGBSpectrum;

public class TrueColorPixelRenderer implements IPixelRenderer {

    private final boolean shadowsEnabled;

    public TrueColorPixelRenderer(boolean shadowsEnabled) {
        this.shadowsEnabled = shadowsEnabled;
    }

    @Override
    public Color renderIntersection(Scene scene, Intersection intersection) {
        var lightValue = calculateLightAt(
                fixFloatingPointImprecision(intersection),
                scene
        );

        return lightValue.toRGB();
    }

    /**
     * Handling of primary ray hitting a surface
     */
    /* private */ ISpectrum calculateLightAt(Intersection intersection, Scene scene) {
        var instance = intersection.object();
        var point = intersection.point();
        var normal = instance.getNormal(point);

        var cosine = normal.dotProduct(scene.getLightSource().invertedDirection());
        cosine = Math.max(0.0, cosine);

        var spectrum =
                instance.material().brdf(intersection.ray(), intersection)
                        .multiplyBy(scene.getLightSource().color())
                        .multiplyBy(cosine);

        if (shadowsEnabled) {
            if (cosine > 1e-7) {
                // send shadow ray
                var shadowRay = new Ray(point, scene.getLightSource().invertedDirection());
                // find any intersection, if found, return 0

                for (var shape : scene.getObjects()) {
                    var shadowIntersection = shape.findVisibleIntersectionWithRay(shadowRay);
                    // todo: replace with 'find any intersection' instead of finding the closest one
                    if (shadowIntersection.isPresent()) return new RGBSpectrum(0, 0, 0);
                }
            }
        }

        return spectrum;
    }

    /**
     * Fixes the situation when the intersection point ends up being under the triangle
     * because of floating point imprecision
     * @param intersection intersection to fix
     * @return fixed intersection
     */
    private Intersection fixFloatingPointImprecision(Intersection intersection) {
        var intersectionPoint = intersection.point();

        intersectionPoint = intersectionPoint.add(
                intersection.object().geometry().getRealNormalAt(
                        intersectionPoint).toVector().multiplyBy(1e-6));

        return new Intersection(
                intersection.ray(),
                intersection.object(),
                intersection.tParam(),
                intersectionPoint
        );
    }

}
