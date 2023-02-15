package ua.leonidius.raytracing;

import lombok.Getter;

public class Camera {

    final Point focusPoint;

    /**
     * Normalized vector of camera direction (where it is pointing to)
     */
    Vector3 cameraDirection;

    /**
     * Distance between the focus point and the sensor plane
     */
    double focusDistance;

    // final Point sensorCenter;
    @Getter final int sensorHeight;
    @Getter final int sensorWidth;

    @Getter final int pixelHeight;
    @Getter final int pixelWidth;

    public Camera(Point focusPoint, Vector3 cameraDirection, double focusDistance, int sensorHeight, int sensorWidth, int pixelHeight, int pixelWidth) {
        this.focusPoint = focusPoint;
        this.cameraDirection = cameraDirection.normalize();
        this.focusDistance = focusDistance;
        this.sensorHeight = sensorHeight;
        this.sensorWidth = sensorWidth;
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
    }

    public Vector3 calculateSensorCenter() {
        return cameraDirection.multiplyBy(focusDistance).add(focusPoint);
    }

}
