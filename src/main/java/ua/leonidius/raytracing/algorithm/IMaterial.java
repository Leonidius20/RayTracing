package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;

public interface IMaterial {

    Ray scatter(Ray ray, Intersection intersection);

    // brdf
    ISpectrum brdf(Ray ray, Intersection intersection);

}
