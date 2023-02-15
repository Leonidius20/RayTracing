package ua.leonidius.raytracing;

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

}
