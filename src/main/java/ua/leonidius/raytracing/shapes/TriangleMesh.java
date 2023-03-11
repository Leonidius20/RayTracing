package ua.leonidius.raytracing.shapes;

import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.enitites.Normal;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

import java.util.OptionalDouble;

public class TriangleMesh implements IShape3d {

    @Override
    public OptionalDouble findVisibleIntersectionWithRay(Ray ray) {
        return null;
    }

    @Override
    public Normal getRealNormalAt(Point point) {
        return null;
    }

    @Override
    public Normal getInterpolatedNormalAt(Point point) {
        return null;
    }
}
