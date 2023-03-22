package ua.leonidius.raytracing.transformations;

import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.shapes.IAffineTransform3d;
import ua.leonidius.raytracing.enitites.Point;

import java.util.Arrays;

/**
 * A base class for affine transformation matrices in 3D space
 */
public class AffineTransform3d implements IAffineTransform3d {

    protected final double[][] data;
    private static final int MATRIX_DIMENSION = 4;

    public AffineTransform3d() {
        this.data = new double[MATRIX_DIMENSION][MATRIX_DIMENSION];
        this.data[MATRIX_DIMENSION - 1][MATRIX_DIMENSION - 1] = 1; // last row 0 0 0 1
    }

    protected AffineTransform3d(double[][] data) {
        this.data = data;
    }

    public AffineTransform3d combineWith(AffineTransform3d other) {
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

    @Override
    public Point applyTo(Point vector) {
        var other = new double[] {vector.x, vector.y, vector.z, 1};

        double[] ans = new double[MATRIX_DIMENSION];

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int k = 0; k < MATRIX_DIMENSION; k++) {
                ans[i] += this.data[i][k] * other[k];
            }
        }

        return new Point(
                ans[0] / ans[3],
                ans[1] / ans[3],
                ans[2] / ans[3]);
    }


    @Override
    public Normal applyTo(Normal normal) {
        // compute inverse transpose
        return new Normal(normal.x, normal.y, normal.z); // TODO: apply reverse transposed matrix
        // to the normal vector
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
        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                if (Math.abs(this.data[i][j] - that.data[i][j]) > 0.0001) {
                    return false;
                }
            }
        }
        // return Arrays.deepEquals(data, that.data);
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }


    private IAffineTransform3d inverseTranspose() {
        return null; // TODO;
    }

}
