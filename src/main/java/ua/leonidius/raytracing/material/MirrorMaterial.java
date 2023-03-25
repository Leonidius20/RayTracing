package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.algorithm.IMaterial;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;

public class MirrorMaterial implements IMaterial {

    private final ISpectrum color;

    /**
     * Create mirror material that only reflects the given color
     * @param color color to reflect
     */
    public MirrorMaterial(ISpectrum color) {
        this.color = color;
    }

    @Override
    public Ray[] createSecondaryRays(Ray ray, Intersection intersection) {
        var normal = intersection.object().getNormal(intersection.point());
        var inverseIncidentDirection = ray.getDirection().multiplyBy(-1);

        var outgoingDirection = normal
                .multiplyBy(2 * normal.dotProduct(inverseIncidentDirection))
                .toVector()
                .subtract(inverseIncidentDirection);

        // trace ray into the outgoing direction
        var reflectedRay = new Ray(intersection.point(), outgoingDirection);
        return new Ray[] { reflectedRay };
    }

    @Override
    public ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum[] secondaryRayResults) {
        return secondaryRayResults[0].multiplyBy(color);
    }

}
