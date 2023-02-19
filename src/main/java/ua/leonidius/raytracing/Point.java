package ua.leonidius.raytracing;

import java.util.Objects;

public class Point {

    public final double x;
    public final double y;
    public final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Add specified values to the coordinates of this point and return a new one with
     * these new values
     *
     * @return
     */
    public Point add(double deltaX, double deltaY, double deltaZ) {
        return new Point(x + deltaX, y + deltaY, z + deltaZ);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0 && Double.compare(point.z, z) == 0;
    }

    public Vector3 toVector() {
        return new Vector3(x, y, z);
    }

}
