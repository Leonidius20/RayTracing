package ua.leonidius.raytracing.primitives;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.*;
import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;

import java.util.Optional;

/**
 * Instance of geometry with some material and some shading
 */
public class Instance implements IInstance {

    private final IShape3d geometry;

    // shader?

    @Getter private final IShadingModel shadingModel;

    @Getter private final IMaterial material;

    public Instance(IShape3d geometry, IShadingModel shadingModel, IMaterial material) {
        this.geometry = geometry;
        this.shadingModel = shadingModel;
        this.material = material;
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
    public Optional<Intersection> findAnyIntersectionWithRay(Ray ray) {
        return findVisibleIntersectionWithRay(ray);
    }

    @Override
    public BoundingBox computeBoundingBox() {
        return geometry.computeBoundingBox();
    }

    @Override
    public IMaterial material() {
        return material;
    }

    @Override
    public IShape3d geometry() {
        return geometry;
    }
}
