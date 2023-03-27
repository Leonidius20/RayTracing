package ua.leonidius.raytracing.light;

import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Vector3;

public class DirectionalLightSource implements ILightSource {

    private final Vector3 invertedDirection; /* direction opposite to the dir. in which light is falling */
    private final ISpectrum color;
    private final double intensity;

    @Deprecated
    public DirectionalLightSource(Vector3 invertedDirection, ISpectrum color) {
        this(invertedDirection, color, 1.0);
    }

    public DirectionalLightSource(Vector3 invertedDirection, ISpectrum color, double intensity) {
        this.invertedDirection = invertedDirection.normalize();
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public Vector3 directionFromPoint(Point point) {
        return invertedDirection;
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
