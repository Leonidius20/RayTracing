package ua.leonidius.raytracing;

import lombok.Getter;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;

/**
 * Instance of geometry with some material and some shading
 */
public class Instance {

    @Getter private final IShape3d geometry;

    // shader?

    @Getter private final IShadingModel shadingModel; // todo: remove, integrate with shader?

    public Instance(IShape3d geometry, IShadingModel shadingModel) {
        this.geometry = geometry;
        this.shadingModel = shadingModel;
    }

    public Normal getNormal(Point point) {
        return shadingModel.getNormal(geometry, point);
    }

}
