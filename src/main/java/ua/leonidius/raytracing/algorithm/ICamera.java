package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.enitites.Point;

/**
 * This can be any kind of camera and we wanna make the renderer(?) depend on this abstraction
 * and not on actual camera class
 */
public interface ICamera {

    Point focusPoint();

    Point findTopLeftPixelCenter();

    int sensorWidth();

    int sensorHeight();

    double pixelWidth();

    double pixelHeight();

}
