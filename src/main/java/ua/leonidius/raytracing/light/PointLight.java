package ua.leonidius.raytracing.light;

import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;

public class PointLight implements ILightSource {

    private final Point position;
    private final ISpectrum color;
    // intensity?

    public PointLight(Point position, ISpectrum color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public Ray buildRayFrom(Point point) {
        return new Ray(point, directionFromPoint(point)); // todo: rey factory or remove altogether?
    }

    @Override
    public Vector3 directionFromPoint(Point point) {
        return position.subtract(point).normalize();
    }

    @Override
    public ISpectrum color() {
        return color;
    }

}
