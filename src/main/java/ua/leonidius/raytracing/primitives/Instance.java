package ua.leonidius.raytracing.primitives;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.IShadingModel;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;

import java.util.Optional;

/**
 * Instance of geometry with some material and some shading
 */
public class Instance implements IPrimitive {

    @Getter private final IShape3d geometry;

    // shader?

    @Getter private final IShadingModel shadingModel;

    public Instance(IShape3d geometry, IShadingModel shadingModel) {
        this.geometry = geometry;
        this.shadingModel = shadingModel;
    }

    public Normal getNormal(Point point) {
        return shadingModel.getNormal(geometry, point);
    }

    @Override
    public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
        var tParamOptional = geometry.findVisibleIntersectionWithRay(ray);
        if (tParamOptional.isEmpty()) return Optional.empty();

        double t = tParamOptional.getAsDouble();
        var point = ray.getXyzOnRay(t);

        return Optional.of(new Intersection(ray, this, t, point));
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return geometry.computeBoundingBox();
    }
}
