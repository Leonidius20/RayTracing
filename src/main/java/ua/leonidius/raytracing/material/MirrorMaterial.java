package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.algorithm.IMaterial;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.*;

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
    public Vector3 getSecondaryRayDirection(Vector3 incidentDirection, Normal normal) {
        var inverseIncidentDirection = incidentDirection.multiplyBy(-1);

        return normal
                .multiplyBy(2 * normal.dotProduct(inverseIncidentDirection))
                .toVector()
                .subtract(inverseIncidentDirection)
                .normalize();
    }

    @Override
    public ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum secondaryRayResult) {
        return secondaryRayResult.multiplyBy(color);
    }

}
