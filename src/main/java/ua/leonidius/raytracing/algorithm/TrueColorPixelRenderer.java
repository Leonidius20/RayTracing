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
        var instance = intersection.object();
        var point = intersection.point();
        var normal = instance.getNormal(point);

        var secondaryRayDirection = instance.material().getSecondaryRayDirection(intersection.ray().getDirection(), normal);

        ISpectrum color;
        if (secondaryRayDirection == null) {
            color = instance.material().brdf(intersection.ray(), intersection, null);
        } else {
            var secondaryRay = new Ray(point, secondaryRayDirection);

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

        // light source specific calculations


        var cosine = normal.dotProduct(scene.getLightSources().get(0).directionFromPoint(point));
        cosine = Math.max(0.0, cosine); // todo: replace with abs ?
        // todo: let the material calculate the cosine??
        // i don't think mirror material should be affected by cosine



        if (shadowsEnabled) {
            if (cosine > 1e-7) {
                // send shadow ray
                var shadowRay = new Ray(point, scene.getLightSources().get(0).directionFromPoint(point));
                // find any intersection, if found, return 0

                for (var primitive : scene.getObjects()) {
                    var shadowIntersection = primitive.findAnyIntersectionWithRay(shadowRay);
                    if (shadowIntersection.isPresent()) return new RGBSpectrum(0, 0, 0);
                }
            }
        }

        return new RGBSpectrum(0,0,0).add(color.multiplyBy(scene.getLightSources().get(0).color()).multiplyBy(cosine));
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
