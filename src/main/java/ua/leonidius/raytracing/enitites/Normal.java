package ua.leonidius.raytracing.enitites;

public class Normal extends OrderedXyzTriple {

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
