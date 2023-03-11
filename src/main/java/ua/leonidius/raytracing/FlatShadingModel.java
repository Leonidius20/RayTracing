package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;

public class FlatShadingModel implements IShadingModel {

    @Override
    public Normal getNormal(IShape3d geometry, Point point) {
        return geometry.getRealNormalAt(point);
    }

}
