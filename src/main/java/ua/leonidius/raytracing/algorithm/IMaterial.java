package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Ray;

public interface IMaterial {

    Ray createSecondaryRay(Ray ray, Intersection intersection);

    // brdf
    ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum secondaryRayResult);

}
