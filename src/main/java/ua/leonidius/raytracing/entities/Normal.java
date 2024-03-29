package ua.leonidius.raytracing.entities;

public class Normal extends OrderedXyzTriple implements Cloneable {

    public Normal(double x, double y, double z) {
        super(x, y, z);
    }

    public Normal normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        return new Normal(x / length, y / length, z / length);
    }

    public double dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Normal multiplyBy(double scalar) {
        return new Normal(x * scalar, y * scalar, z * scalar);
    }

    public Normal add(Normal other) {
        return new Normal(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 toVector() {
        return new Vector3(x, y, z);
    }
    
    /*
    operations:
    - invert
    - add another normal
    - cross product with vector
    - multiply by scalar and return normal
    - add vector and return vector
    -
     */

}
