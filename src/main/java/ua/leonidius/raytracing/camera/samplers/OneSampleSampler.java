package ua.leonidius.raytracing.camera.samplers;

import ua.leonidius.raytracing.camera.ISampler;
import ua.leonidius.raytracing.entities.Point2d;

public class OneSampleSampler implements ISampler {

    @Override
    public Point2d[] samplePointsForPixel(Point2d pixelCoordinates) {
        return new Point2d[] { pixelCoordinates };
    }

}
