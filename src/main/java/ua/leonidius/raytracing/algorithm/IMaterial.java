package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.*;

public interface IMaterial {

    Vector3 getSecondaryRayDirection(Vector3 incidentDirection, Normal normal);

    // brdf
    ISpectrum brdf(Ray ray, Intersection intersection, ISpectrum secondaryRayResult);

}
