package ua.leonidius.raytracing.camera;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.ICamera;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;

public class Camera implements ICamera {

    final Point focusPoint;

    /**
     * Normalized vector of camera direction (where it is pointing to)
     */
    @Getter
    Vector3 direction = new Vector3(0, 1, 0).normalize(); // TODO: support others

    /**
     * Distance between the focus point and the sensor plane
     */
    @Getter double focusDistance;

    // final Point sensorCenter;
    final int sensorHeight;
    final int sensorWidth;

    final double pixelHeight;
    final double pixelWidth;

    public Camera(Point focusPoint, /*Vector3 cameraDirection,*/ double focusDistance, int sensorHeight, int sensorWidth, double pixelHeight, double pixelWidth) {
        this.focusPoint = focusPoint;
        //this.cameraDirection = cameraDirection.normalize();
        this.focusDistance = focusDistance;
        this.sensorHeight = sensorHeight;
        this.sensorWidth = sensorWidth;
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
    }

    /* private */ Point findSensorCenter() {
        return focusPoint.add(direction.multiplyBy(focusDistance));
    }

    @Override
    public Point findTopLeftPixelCenter() {
        var sensorCenter = findSensorCenter();

        final double realSensorWidth = sensorWidth * pixelWidth;
        final double realSensorHeight = sensorHeight * pixelHeight;

        // TODO: the plane isn't always vetical. Gotta find top left corner through vector operations

        var offsetXY = new Vector3((-realSensorWidth  + pixelWidth) / 2.0,
                0,
                (realSensorHeight - pixelHeight) / 2.0);

        return sensorCenter.add(offsetXY);
    }

    @Override
    public int sensorHeight() {
        return sensorHeight;
    }

    @Override
    public int sensorWidth() {
        return sensorWidth;
    }

    @Override
    public double pixelHeight() {
        return pixelHeight;
    }

    @Override
    public double pixelWidth() {
        return pixelWidth;
    }

    @Override
    public Point focusPoint() {
        return focusPoint;
    }
}
