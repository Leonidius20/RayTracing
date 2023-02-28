package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.transformations.AffineTransform3d;

import java.util.Arrays;
import java.util.OptionalDouble;

public class Triangle implements IShape3d {

    private final Point[] vertices;
    private final Normal[] normals;

    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        this.vertices = new Point[] {vertex1, vertex2, vertex3};
        this.normals = new Normal[] {null, null, null};
    }

    public Triangle(Point[] vertices, Normal[] normals) {
        this.vertices = vertices;
        this.normals = normals;
    }

    private static final double EPSILON = 0.0000001;

    @Override
    public OptionalDouble findVisibleIntersectionWithRay(Ray ray) {
        final Point vertex1 = vertices[0];
        final Point vertex2 = vertices[1];
        final Point vertex3 = vertices[2];

        Vector3 edge1 = vertex2.subtract(vertex1);
        Vector3 edge2 = vertex3.subtract(vertex1);

        Vector3 h = ray.getDirection().crossProduct(edge2);

        double a, f, u, v;


        a = edge1.dotProduct(h);

        if (a > -EPSILON && a < EPSILON) {
            return OptionalDouble.empty();    // This ray is parallel to this triangle.
        }

        f = 1.0 / a;

        Vector3 s = ray.getOrigin().subtract(vertex1);

        u = f * (s.dotProduct(h));

        if (u < 0.0 || u > 1.0) {
            return OptionalDouble.empty();
        }

        Vector3 q = s.crossProduct(edge1);

        v = f * ray.getDirection().dotProduct(q);

        if (v < 0.0 || u + v > 1.0) {
            return OptionalDouble.empty();
        }

        // At this stage we can compute t to find out where the intersection point is on the line.
        double t = f * edge2.dotProduct(q);
        if (t > EPSILON) // ray intersection
        {
            return OptionalDouble.of(t);
        } else // This means that there is a line intersection but not a ray intersection.
        {
            return OptionalDouble.empty();
        }
    }

    @Override
    public Normal getNormalAt(Point point, ShadingModel shading) {
        if (shading == ShadingModel.SMOOTH) {
            return getSmoothShadingNormalAt(point);
        } else {
            return getFlatShadingNormal(Winding.CLOCKWISE);
        }
    }

    /* private */ Normal getSmoothShadingNormalAt(Point point) {
        return getFlatShadingNormal(Winding.CLOCKWISE); // todo
    }

    enum Winding {
        CLOCKWISE,
        COUNTER_CLOCKWISE,
    }

    /* private */ Normal getFlatShadingNormal(Winding winding) {
        final Point vertex1 = vertices[0];
        final Point vertex2 = vertices[1];
        final Point vertex3 = vertices[2];

        var edge1 = vertex1.subtract(vertex3);
        var edge2 = vertex2.subtract(vertex3);

        if (winding == Winding.CLOCKWISE) {
            return edge1.crossProductN(edge2).normalize();
        } else {
            return edge2.crossProductN(edge1).normalize();
        }

    }

    public Triangle applyTransform(AffineTransform3d matrix) {
        var newVertices = new Point[3];
        var newNormals = new Normal[3];

        // todo: actual transformation
        for (int i = 0; i < vertices.length; i++) {

            newVertices[i] = vertices[i].applyTransform(matrix);
            newNormals[i] = normals[i].clone();

        }

        return new Triangle(newVertices, newNormals);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + Arrays.toString(vertices) +
                ", normals=" + Arrays.toString(normals) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Arrays.equals(vertices, triangle.vertices) && Arrays.equals(normals, triangle.normals);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(vertices);
        result = 31 * result + Arrays.hashCode(normals);
        return result;
    }
}
