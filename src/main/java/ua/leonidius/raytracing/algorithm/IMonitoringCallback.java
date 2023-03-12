package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.enitites.Color;

public interface IMonitoringCallback {

    void shareProgress(Color[][] pixels, int startX, int startY, int endX, int endY);

}
