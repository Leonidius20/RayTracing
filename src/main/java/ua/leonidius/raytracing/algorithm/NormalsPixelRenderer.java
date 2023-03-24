package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.entities.Color;

public class NormalsPixelRenderer implements IPixelRenderer {

    @Override
    public Color renderIntersection(Scene scene, Intersection intersection) {
        var normal = intersection.object().getNormal(intersection.point());
        // normal is a unit vector, so each component is between -1 and 1
        // first, we map components to 0 to 2, then -- to 0 to 255
        return new Color(
                (int) ((255 / 2) * (normal.x + 1)),
                (int) ((255 / 2) * (normal.y + 1)),
                (int) ((255 / 2) * (normal.z + 1))
        );
    }

}
