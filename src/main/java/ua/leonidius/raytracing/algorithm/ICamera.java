package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;

/**
 * This can be any kind of camera and we wanna make the renderer(?) depend on this abstraction
 * and not on actual camera class
 */
public interface ICamera {

    Point focusPoint();

    Vector3 findTopLeftPixelCenter(); // TODO: replace with point or something

    int sensorWidth();

    int sensorHeight();

    double pixelWidth();

    double pixelHeight();

}
