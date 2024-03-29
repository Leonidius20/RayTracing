package ua.leonidius.raytracing.light;

import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;

public class PointLight implements ILightSource {

    private final Point position;
    private final ISpectrum color;
    private final double intensity;
    // intensity?

    @Deprecated
    public PointLight(Point position, ISpectrum color) {
        this(position, color, 1.0);
    }

    public PointLight(Point position, ISpectrum color, double intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public Vector3 directionFromPoint(Point point) {
        return position.subtract(point).normalize();
    }

    @Override
    public ISpectrum color() {
        return color;
    }

    @Override
    public double intensity() {
        return intensity;
    }
}
