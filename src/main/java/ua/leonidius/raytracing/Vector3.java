package ua.leonidius.raytracing;

import ua.leonidius.raytracing.math.TransformMatrix3d;

import java.util.Objects;

public class Vector3 {

    public final double x;
    public final double y;
    public final double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double square() {
        return dotProduct(this);
    }

    /**
     * Adds given vector to this one and returns a new resulting vector
     * @param other vector to add to this one
     * @return new vector which is the result of the addition
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 add(Point other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 subtract(Point other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public double calculateLength() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     *
     * @return new vector which is a normalized version of this one
     */
    public Vector3 normalize() {
        double length = calculateLength();
        return new Vector3(x / length, y / length, z / length);
    }

    public Vector3 multiplyBy(double scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;

        double epsilon = 0.0001; // comparison precision

        return Math.abs(vector3.x - x) < epsilon
                && Math.abs(vector3.y - y) < epsilon
                && Math.abs(vector3.z - z) < epsilon;

        // return Double.compare(vector3.x, x) == 0 && Double.compare(vector3.y, y) == 0 && Double.compare(vector3.z, z) == 0;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public Vector3 crossProduct(Vector3 other) {
        return new Vector3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x
        );
    }

}
