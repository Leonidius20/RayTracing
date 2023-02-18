package ua.leonidius.raytracing.algorithm;

import lombok.Getter;
import ua.leonidius.raytracing.Point;
import ua.leonidius.raytracing.Vector3;

import java.util.Objects;

public class Ray {

    @Getter private final Point origin;
    @Getter private final Vector3 direction;

    public Ray(Point origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Get world coordinates of a point defined by a provided value of the 't' parameter
     * in the line's vector equation (p = o + dt, where p - point on the line, o - origin,
     * d - direction)
     * @param tParam value of the 't' parameter from the equation
     * @return world coordinates of this point
     */
    public Vector3 getXyzOnRay(double tParam) {
        return direction // p = o + dt
                .multiplyBy(tParam)
                .add(origin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(origin, ray.origin) && Objects.equals(direction, ray.direction);
    }

}