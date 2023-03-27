package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.entities.Color;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.spectrum.RGBSpectrum;
import ua.leonidius.raytracing.material.MirrorMaterial;

public class TrueColorPixelRenderer implements IPixelRenderer {

    private final int MAX_RECURSION_DEPTH = 5;

    private final boolean shadowsEnabled;

    public TrueColorPixelRenderer(boolean shadowsEnabled) {
        this.shadowsEnabled = shadowsEnabled;
    }

    @Override
    public Color renderIntersection(Scene scene, Intersection intersection) {
        var lightValue = calculateLightAt(
                intersection,
                scene,
                0
        );

        return lightValue.toRGB();
    }

    /**
     * Handling of primary ray hitting a surface
     */
    /* private */ ISpectrum calculateLightAt(Intersection intersection, Scene scene, int recursionDepth) {
        if (recursionDepth > MAX_RECURSION_DEPTH) {
            return new RGBSpectrum(0, 0, 0);
        }

        intersection = fixFloatingPointImprecision(intersection);

        // todo account for recursion depth

        // todo multiple light sources
        // just add light values from all light sources
        // main loop going through all light sources

        var instance = intersection.object();
        var point = intersection.point();
        var normal = instance.getNormal(point);

        var cosine = normal.dotProduct(scene.getLightSources().get(0).directionFromPoint(point));
        cosine = Math.max(0.0, cosine); // todo: replace with abs ?
        // todo: let the material calculate the cosine??
        // i don't think mirror material should be affected by cosine


        var secondaryRay = instance.material().createSecondaryRay(intersection.ray(), intersection);
        ISpectrum color;
        if (secondaryRay == null) {
            color = instance.material().brdf(intersection.ray(), intersection, null);
        } else {
            // trace secondary rays
            ISpectrum secondaryRayResult;

            var secondaryIntersection = Renderer.findClosestIntersection(secondaryRay, scene); // todo refactor, eliminate dependency on Renderer
            if (secondaryIntersection.isPresent()) {
                secondaryRayResult = calculateLightAt(secondaryIntersection.get(), scene, recursionDepth + 1);
            } else {
                secondaryRayResult = new RGBSpectrum(0, 0, 0);
            }

            color = instance.material().brdf(intersection.ray(), intersection, secondaryRayResult);

            // todo remove, it's only a test
            if (instance.material() instanceof MirrorMaterial m) {
                return color; // no shadows and no cosine
            }
        }

        if (shadowsEnabled) {
            if (cosine > 1e-7) {
                // send shadow ray
                var shadowRay = new Ray(point, scene.getLightSources().get(0).directionFromPoint(point));
                // find any intersection, if found, return 0

                for (var shape : scene.getObjects()) {
                    var shadowIntersection = shape.findVisibleIntersectionWithRay(shadowRay);
                    // todo: replace with 'find any intersection' instead of finding the closest one
                    if (shadowIntersection.isPresent()) return new RGBSpectrum(0, 0, 0);
                }
            }
        }

        return color.multiplyBy(cosine);
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
