package ua.leonidius.raytracing;

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

}
