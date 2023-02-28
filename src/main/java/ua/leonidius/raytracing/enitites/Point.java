package ua.leonidius.raytracing.enitites;

import ua.leonidius.raytracing.transformations.AffineTransform3d;
import ua.leonidius.raytracing.transformations.TranslationMatrix3d;

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

    public Point applyTransform(AffineTransform3d transformation) {
        var result = transformation.multiplyBy(this);
        double newX = result[0] / result[3];
        double newY = result[1] / result[3];
        double newZ = result[2] / result[3];
        return new Point(newX, newY, newZ);
    }

    /**
     * Add specified values to the coordinates of this point and return a new one with
     * these new values
     *
     * @return
     */
    /*public Point add(double deltaX, double deltaY, double deltaZ) {
        return new Point(x + deltaX, y + deltaY, z + deltaZ);
    }*/

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0 && Double.compare(point.z, z) == 0;
    }*/

    /*public Vector3 toVector() {
        return new Vector3(x, y, z);
    }*/

    /*
    OPERATIONS:
    - convert to location vector
    - add or subtract vector to/from point (move point by vector)
    - subtract point from point (get vector between them)
    - distance between 2 points
    - multiply by a scalar
     */

}
