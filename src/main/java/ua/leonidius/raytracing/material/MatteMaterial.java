package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.algorithm.IMaterial;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.*;

public class MatteMaterial implements IMaterial {

    ISpectrum color;

    public MatteMaterial(ISpectrum color) {
        this.color = color;
    }

    @Override
    public Vector3 getSecondaryRayDirection(Vector3 incidentDirection, Normal normal) {
        return null;
    }

    @Override
    public ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum secondaryRayResult) {
        return color;
    }

}
