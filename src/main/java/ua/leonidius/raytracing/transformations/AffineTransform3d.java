package ua.leonidius.raytracing.transformations;

import ua.leonidius.raytracing.enitites.OrderedXyzTriple;

import java.util.Arrays;

/**
 * A base class for affine transformation matrices in 3D space
 */
public class AffineTransform3d {

    protected final double[][] data;
    private static final int MATRIX_DIMENSION = 4;

    public AffineTransform3d() {
        this.data = new double[MATRIX_DIMENSION][MATRIX_DIMENSION];
        this.data[MATRIX_DIMENSION - 1][MATRIX_DIMENSION - 1] = 1; // last row 0 0 0 1
    }

    protected AffineTransform3d(double[][] data) {
        this.data = data;
    }

    public AffineTransform3d multiplyBy(AffineTransform3d other) {
        double[][] ans = new double[MATRIX_DIMENSION][MATRIX_DIMENSION];

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                for (int k = 0; k < MATRIX_DIMENSION; k++) {
                    ans[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return new AffineTransform3d(ans);
    }

    public double[] multiplyBy(OrderedXyzTriple vector) {
        var other = new double[] {vector.x, vector.y, vector.z, 1};

        double[] ans = new double[MATRIX_DIMENSION];

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int k = 0; k < MATRIX_DIMENSION; k++) {
                ans[i] += this.data[i][k] * other[k];
            }
        }

        return ans;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                sb.append(String.format("%-20.2f", this.data[i][j]));
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass() && getClass() != o.getClass().getSuperclass())) return false;
        AffineTransform3d that = (AffineTransform3d) o;
        return Arrays.deepEquals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }
    
}
