package ua.leonidius.raytracing.camera;

import ua.leonidius.raytracing.entities.Point2d;

public interface ISampler {

    /**
     * Returns coordinates of points (in the 2d camera sensor coordinate system)
     * through which rays should be cast to render the given pixel
     * @param pixelCoordinates coordinates of the pixel to be rendered (integer values)
     * @return array of points (int the 2d camera sensor coordinate system,
     * floating point values)
     */
    Point2d[] samplePointsForPixel(Point2d pixelCoordinates);

}
