package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.primitives.Instance;

public record Intersection (
        Ray ray,
        Instance object,
        double tParam,
        Point point
) {
}
