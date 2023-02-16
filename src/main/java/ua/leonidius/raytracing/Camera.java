package ua.leonidius.raytracing;

import lombok.Getter;

public class Camera {

    @Getter final Point focusPoint;

    /**
     * Normalized vector of camera direction (where it is pointing to)
     */
    @Getter Vector3 direction = new Vector3(0, 1, 0); // TODO: support others

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

}
