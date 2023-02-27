package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.enitites.Ray;

// light source abstraction for Renderer to depend on
public interface ILightSource {

    Ray buildRayFrom(Point point);

    Vector3 invertedDirection(); // TODO: remove, it's temp shitcode

}
