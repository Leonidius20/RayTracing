package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.*;

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
        double[] uvw = getBarycentricCoordinates(point);
        double u = uvw[0];
        double v = uvw[1];
        double w = uvw[2];
        final Normal normalA = normals[0];
        final Normal normalB = normals[1];
        final Normal normalC = normals[2];
        return normalA.multiplyBy(u)
                .add(normalB.multiplyBy(v))
                .add(normalC.multiplyBy(w))
                .normalize();
    }

    /* private */ double[] getBarycentricCoordinates(Point point) {
        /*
        Barycentric coordinates are also known as areal coordinates.
        Although not very commonly used, this term indicates that
        the coordinates u, v, and w are proportional to the area
        of the three sub-triangles defined by P,
        the point located on the triangle,
         and the triangle's vertices (A, B, C).
         These three sub-triangles are denoted ABP, BCP, and CAP.
         */
        Point a = vertices[0];
        Point b = vertices[1];
        Point c = vertices[2];

        Vector3 ab = b.subtract(a);
        Vector3 ac = c.subtract(a);


        // 1. compute area of the triangle
        double fullTriangleArea = ab.crossProduct(ac).calculateLength() / 2.0;

        // 2. compute area of sub-triangle
        Vector3 ap = point.subtract(a);
        double areaPAC = ap.crossProduct(ac).calculateLength() / 2.0;
        double areaPAB = ap.crossProduct(ab).calculateLength() / 2.0;

        Vector3 cp = point.subtract(c);
        Vector3 cb = b.subtract(c);
        double areaCPB = cp.crossProduct(cb).calculateLength() / 2.0;

        // 3. divide
        double u = areaPAC / fullTriangleArea;
        double v = areaPAB / fullTriangleArea;
        double w = areaCPB / fullTriangleArea;

        return new double[] {u, v, w};
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

    public Triangle applyTransform(IAffineTransform3d transformation) {
        var newVertices = new Point[3];
        var newNormals = new Normal[3];

        // todo: actual transformation
        for (int i = 0; i < vertices.length; i++) {

            newVertices[i] = transformation.applyTo(vertices[i]);
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
