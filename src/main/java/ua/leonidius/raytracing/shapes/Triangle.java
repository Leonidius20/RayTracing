package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.ShadingModel;
import ua.leonidius.raytracing.Vector3;
import ua.leonidius.raytracing.algorithm.Ray;

public class Triangle implements Shape3d {

    private final Vector3 vertex1;
    private final Vector3 vertex2;
    private final Vector3 vertex3;

    public Triangle(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    private static final double EPSILON = 0.0000001;

    @Override
    public Double findVisibleIntersectionWithRay(Ray ray) {
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
            return getFlatShadingNormalAt(point);
        }
    }

    /* private */ Vector3 getSmoothShadingNormalAt(Vector3 point) {
        return getFlatShadingNormalAt(point); // todo
    }

    /* private */ Vector3 getFlatShadingNormalAt(Vector3 point) {
        var edge1 = vertex2.subtract(vertex1);
        var edge2 = vertex3.subtract(vertex1);
        return edge1.crossProduct(edge2).normalize(); // todo check orientation?
    }

}