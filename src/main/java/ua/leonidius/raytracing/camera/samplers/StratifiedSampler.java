package ua.leonidius.raytracing.camera.samplers;

import ua.leonidius.raytracing.camera.ISampler;
import ua.leonidius.raytracing.entities.Point2d;

/**
 * Stratified means subdividing pixel into a grid and taking a random point from each cell
 */
public class StratifiedSampler implements ISampler {

    private final int samplesNumber;

    private final int width;
    private final int height;

    private final double cellWidth;
    private final double cellHeight;

    private final Point2d topLeftPixelCenterOffset;

    public StratifiedSampler(int samplesNumber) {
        this.samplesNumber = samplesNumber;
        this.width = (int) Math.sqrt(samplesNumber);
        this.height = samplesNumber / this.width;
        var lostSamples = samplesNumber - this.width * this.height;
        if (lostSamples > 0) {
            throw new IllegalArgumentException("Samples number must be a square of an integer");
        }
        this.cellWidth = 1.0 / this.width;
        this.cellHeight = 1.0 / this.height;
        this.topLeftPixelCenterOffset = new Point2d(-0.5 + cellWidth / 2, -0.5 + cellHeight / 2);
    }

    @Override
    public Point2d[] samplePointsForPixel(Point2d pixelCoordinates) {
        var points = new Point2d[samplesNumber];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var x = pixelCoordinates.x() + topLeftPixelCenterOffset.x() + i * cellWidth;
                var y = pixelCoordinates.y() + topLeftPixelCenterOffset.y() + j * cellHeight;
                // randomize
                x += Math.random() * (cellWidth / 2 - 0.0001);
                y += Math.random() * (cellHeight / 2 - 0.0001);
                points[i * width + j] = new Point2d(x, y);
            }
        }

        return points;
    }

}
