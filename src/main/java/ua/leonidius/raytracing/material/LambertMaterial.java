package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.algorithm.IMaterial;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;

public class LambertMaterial implements IMaterial {

    private final ISpectrum albedo;

    public LambertMaterial(ISpectrum albedo) {
        this.albedo = albedo;
    }

    @Override
    public Ray createSecondaryRay(Ray ray, Intersection intersection) {
        // randomise
        return null;
    }

    @Override
    public ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum secondaryRayResult) {
        return null;
    }

}
