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

    TriangleMesh applyTransform(IAffineTransform3d transform) {
        var newVertices = new ArrayList<Point>(vertices.size());
        var newNormals = new ArrayList<Normal>(normals.size());

        for (Point vertex : vertices) {
            newVertices.add(transform.applyTo(vertex));
        }
        for (var normal : normals) {
            newNormals.add(transform.applyTo(normal));
        }
        // todo note: the faces array is the same
        return new TriangleMesh(newVertices, newNormals, faces);
    }

}
