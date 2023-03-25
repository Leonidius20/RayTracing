package ua.leonidius.raytracing.material;

import ua.leonidius.raytracing.algorithm.IMaterial;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.Color;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.spectrum.RGBSpectrum;

public class LambertMaterial implements IMaterial {

    ISpectrum color;

    public LambertMaterial(ISpectrum color) {
        this.color = color;
    }

    @Override
    public Ray scatter(Ray ray, Intersection intersection) {
        return null;
    }

    @Override
    public ISpectrum brdf(Ray ray, Intersection intersection) {
        return color;
    }

}
