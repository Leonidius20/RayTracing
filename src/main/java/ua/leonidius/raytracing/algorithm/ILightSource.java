package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;

// light source abstraction for Renderer to depend on
public interface ILightSource {

    Ray buildRayFrom(Point point);

    Vector3 invertedDirection(); // TODO: remove, it's temp shitcode

    ISpectrum color();

}
