package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.Color;

public interface IMonitoringCallback {

    void shareProgress(Color[][] pixels, int startX, int startY, int endX, int endY);

}
