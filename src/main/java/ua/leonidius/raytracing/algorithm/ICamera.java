package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.Ray;

/**
 * This can be any kind of camera and we wanna make the renderer(?) depend on this abstraction
 * and not on actual camera class
 */
public interface ICamera {

    int sensorWidth();

    int sensorHeight();

    Ray getRayForPixel(int pixelX, int pixelY);

}
