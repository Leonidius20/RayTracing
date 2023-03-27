package ua.leonidius.raytracing.entities;

public class Point extends OrderedXyzTriple {

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Get vector that goes from this point to another point
     * @param other vector destination point
     * @return constructed vector
     */
    public Vector3 subtract(Point other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Point add(Vector3 vector) {
        return new Point(x + vector.x, y + vector.y, z + vector.z);
    }

    /**
     * Returns a new point with the same coordinates as this point,
     * but with the value of the specified axis replaced
     * @param axis axis on which to replace the value
     * @param newValue new value for the axis
     * @return new point
     */
    public Point replaceValue(Axis axis, double newValue) {
        switch (axis) {
            case X -> {
                return new Point(newValue, y, z);
            }
            case Y -> {
                return new Point(x, newValue, z);
            }
            case Z -> {
                return new Point(x, y, newValue);
            }
        }
        throw new RuntimeException(); // unreachable
    }

}
