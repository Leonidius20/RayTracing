package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.algorithm.IMaterial;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;

public class MatteMaterial implements IMaterial {

    ISpectrum color;

    public MatteMaterial(ISpectrum color) {
        this.color = color;
    }

    @Override
    public Ray createSecondaryRay(Ray ray, Intersection intersection) {
        return null; // todo: random ray
    }

    @Override
    public ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum secondaryRayResult) {
        return color;
    }

}
