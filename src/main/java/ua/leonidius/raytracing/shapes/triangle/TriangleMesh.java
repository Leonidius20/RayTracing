package ua.leonidius.raytracing.shapes.triangle;

import lombok.Getter;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.shapes.IAffineTransform3d;

import java.util.ArrayList;

public class TriangleMesh {

    private final ArrayList<Point> vertices;
    private final ArrayList<Normal> normals;
    @Getter private final ArrayList<Triangle> faces;

    public TriangleMesh(ArrayList<Point> vertices, ArrayList<Normal> normals, ArrayList<Triangle> faces) {
        this.vertices = vertices;
        this.normals = normals;
        this.faces = faces;
    }

    public void applyTransformDestructive(IAffineTransform3d transform) {
        for (Point vertex : vertices) {
            vertex.absorb(transform.applyTo(vertex));
        }
        for (var normal : normals) {
            normal.absorb(transform.applyTo(normal));
        }
    }

}
