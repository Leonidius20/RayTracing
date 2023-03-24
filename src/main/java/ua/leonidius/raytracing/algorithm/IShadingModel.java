package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;

public interface IShadingModel {

    Normal getNormal(IShape3d geometry, Point point);

}

