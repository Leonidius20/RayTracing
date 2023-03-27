package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;

// light source abstraction for Renderer to depend on
public interface ILightSource {

    Ray buildRayFrom(Point point);

    /**
     * Returns a vector pointing from a given point to the light source
     * @param point point to calculate the direction from
     * @return direction vector
     */
    Vector3 directionFromPoint(Point point);

    /**
     * Returns the illumination at a given point from this light assuming no obstructions
     * @param point point to calculate the illumination at
     * @return illumination value
     */
    //double getIlluminationAtPoint(Point point); // do we need this?

    ISpectrum color();

}
