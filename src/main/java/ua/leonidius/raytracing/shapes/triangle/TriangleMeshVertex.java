package ua.leonidius.raytracing.shapes.triangle;

import lombok.Getter;
import lombok.Setter;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.shapes.IAffineTransform3d;

import java.util.Objects;

public class TriangleMeshVertex {

    @Getter
    @Setter
    private Point point;
    @Getter @Setter private Normal normal;

    public TriangleMeshVertex(Point point, Normal normal) {
        this.point = point;
        this.normal = normal;
    }

    public TriangleMeshVertex applyTransform(IAffineTransform3d transform) {
        var newPoint = transform.applyTo(point);
        var newNormal = normal.clone(); // todo: implement
        return new TriangleMeshVertex(newPoint, newNormal);
    }

    @Override
    public String toString() {
        return "TriangleMeshVertex{" +
                "point=" + point.toString() +
                ", normal=" + normal.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriangleMeshVertex that = (TriangleMeshVertex) o;
        return Objects.equals(point, that.point) && Objects.equals(normal, that.normal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, normal);
    }

}
