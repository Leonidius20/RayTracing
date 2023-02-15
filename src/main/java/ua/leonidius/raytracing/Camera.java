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

    public Camera(Point focusPoint, Vector3 cameraDirection, double focusDistance, int sensorHeight, int sensorWidth) {
        this.focusPoint = focusPoint;
        this.cameraDirection = cameraDirection.normalize();
        this.focusDistance = focusDistance;
        this.sensorHeight = sensorHeight;
        this.sensorWidth = sensorWidth;
    }

    /**
     * Get real-world coordinates of the center of a pixel specified by x and y coordinates
     * on the camera sensor's plane.
     * @param x pixel's x coordinate (goes up from left to right)
     * @param y pixel's y coordinate (goes up from top to bottom)
     * @return center of the pixel in the real world
     */
    public Point getPixelCoordinates(int x, int y) {
        // let's say
        Point sensorCenter = focusPoint.add(0, focusDistance, 0);
    }

}
