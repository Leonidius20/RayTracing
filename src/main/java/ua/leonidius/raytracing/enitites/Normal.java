package ua.leonidius.raytracing.enitites;

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

    @Override
    public Normal clone() {
        try {
            return (Normal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone not supported");
        }
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
