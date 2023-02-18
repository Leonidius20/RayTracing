package ua.leonidius.raytracing;

import lombok.Getter;

public class Camera {

    @Getter final Point focusPoint;

    /**
     * Normalized vector of camera direction (where it is pointing to)
     */
    @Getter Vector3 direction = new Vector3(0, 1, 0).normalize(); // TODO: support others

    /**
     * Distance between the focus point and the sensor plane
     */
    @Getter double focusDistance;

    // final Point sensorCenter;
    @Getter final int sensorHeight;
    @Getter final int sensorWidth;

    @Getter final double pixelHeight;
    @Getter final double pixelWidth;

    public Camera(Point focusPoint, /*Vector3 cameraDirection,*/ double focusDistance, int sensorHeight, int sensorWidth, double pixelHeight, double pixelWidth) {
        this.focusPoint = focusPoint;
        //this.cameraDirection = cameraDirection.normalize();
        this.focusDistance = focusDistance;
        this.sensorHeight = sensorHeight;
        this.sensorWidth = sensorWidth;
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
    }

    public Vector3 findSensorCenter() {
        return direction.multiplyBy(focusDistance).add(focusPoint);
    }

    public Vector3 findTopLeftPixelCenter() {
        var sensorCenter = findSensorCenter();

        final double realSensorWidth = sensorWidth * pixelWidth;
        final double realSensorHeight = sensorHeight * pixelHeight;

        // TODO: the plane isn't always vetical. Gotta find top left corner through vector operations

        Vector3 offsetXY = new Vector3((-realSensorWidth  + pixelWidth) / 2.0,
                0,
                (realSensorHeight - pixelHeight) / 2.0);

        return sensorCenter.add(offsetXY);
    }

}
