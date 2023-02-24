package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.Vector3;
import ua.leonidius.raytracing.algorithm.Ray;

import java.util.Arrays;

public class Triangle implements Shape3d {

    private final Vector3[] vertices;
    private final Vector3[] normals;


    public Triangle(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3) {
        this.vertices = new Vector3[] {vertex1, vertex2, vertex3};
        this.normals = new Vector3[] {null, null, null};
    }

    public Triangle(Vector3[] vertices, Vector3[] normals) {
        this.vertices = vertices;
        this.normals = normals;
    }

    private static final double EPSILON = 0.0000001;

    @Override
    public Double findVisibleIntersectionWithRay(Ray ray) {
        final Vector3 vertex1 = vertices[0];
        final Vector3 vertex2 = vertices[1];
        final Vector3 vertex3 = vertices[2];

        Vector3 edge1 = vertex2.subtract(vertex1);
        Vector3 edge2 = vertex3.subtract(vertex1);

        Vector3 h = ray.getDirection().crossProduct(edge2);

        double a, f, u, v;


        a = edge1.dotProduct(h);

        if (a > -EPSILON && a < EPSILON) {
            return null;    // This ray is parallel to this triangle.
        }

        f = 1.0 / a;

        Vector3 s = ray.getOrigin().toVector().subtract(vertex1);

        u = f * (s.dotProduct(h));

        if (u < 0.0 || u > 1.0) {
            return null;
        }

        Vector3 q = s.crossProduct(edge1);

        v = f * ray.getDirection().dotProduct(q);

        if (v < 0.0 || u + v > 1.0) {
            return null;
        }

        // At this stage we can compute t to find out where the intersection point is on the line.
        double t = f * edge2.dotProduct(q);
        if (t > EPSILON) // ray intersection
        {
            return t;
        } else // This means that there is a line intersection but not a ray intersection.
        {
            return null;
        }
    }

    @Override
    public Vector3 getNormalAt(Vector3 point, ShadingModel shading) {
        if (shading == ShadingModel.SMOOTH) {
            return getSmoothShadingNormalAt(point);
        } else {
            return getFlatShadingNormal(Winding.CLOCKWISE);
        }
    }

    /* private */ Vector3 getSmoothShadingNormalAt(Vector3 point) {
        return getFlatShadingNormal(Winding.CLOCKWISE); // todo
    }

    enum Winding {
        CLOCKWISE,
        COUNTER_CLOCKWISE,
    }

    /* private */ Vector3 getFlatShadingNormal(Winding winding) {
        final Vector3 vertex1 = vertices[0];
        final Vector3 vertex2 = vertices[1];
        final Vector3 vertex3 = vertices[2];

        var edge1 = vertex1.subtract(vertex3);
        var edge2 = vertex2.subtract(vertex3);

        if (winding == Winding.CLOCKWISE) {
            return edge1.crossProduct(edge2).normalize();
        } else {
            return edge2.crossProduct(edge1).normalize();
        }

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

}
