package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.primitives.Instance;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Ray;

public record Intersection (
        Ray ray,
        Instance object,
        double tParam,
        Point point
) {
}
