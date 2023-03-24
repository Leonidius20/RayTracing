package ua.leonidius.raytracing.shading;

import ua.leonidius.raytracing.algorithm.IShadingModel;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;

public class FlatShadingModel implements IShadingModel {

    @Override
    public Normal getNormal(IShape3d geometry, Point point) {
        return geometry.getRealNormalAt(point);
    }

}
