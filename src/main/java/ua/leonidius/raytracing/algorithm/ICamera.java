package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

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

    Ray getRayForPixel(int pixelX, int pixelY);

}
