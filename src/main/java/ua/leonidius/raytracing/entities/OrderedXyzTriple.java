package ua.leonidius.raytracing.entities;

import java.util.Objects;

public abstract class OrderedXyzTriple {

    private static final double COMPARISON_PRECISION = 1e-7;

    public double x;
    public double y;
    public double z;

    public OrderedXyzTriple(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedXyzTriple that = (OrderedXyzTriple) o;

        double epsilon = COMPARISON_PRECISION;

        return Math.abs(that.x - x) < epsilon
                && Math.abs(that.y - y) < epsilon
                && Math.abs(that.z - z) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "OrderedXyzTriple{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public void absorb(OrderedXyzTriple other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public double getValue(Axis axis) {
        switch (axis) {
            case X -> {
                return x;
            }
            case Y -> {
                return y;
            }
            case Z -> {
                return z;
            }
        }
        throw new RuntimeException("unknown axis");
    }

}
