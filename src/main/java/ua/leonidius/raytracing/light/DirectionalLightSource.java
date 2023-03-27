package ua.leonidius.raytracing.light;

import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.entities.Vector3;

public class DirectionalLightSource implements ILightSource {

    private final Vector3 invertedDirection; /* direction opposite to the dir. in which light is falling */
    private final ISpectrum color;

    public DirectionalLightSource(Vector3 invertedDirection, ISpectrum color) {
        this.invertedDirection = invertedDirection.normalize();
        this.color = color;
    }

    @Override
    public Ray buildRayFrom(Point point) {
        return new Ray(point, invertedDirection); // todo: rey factory?
    }

    @Override
    public Vector3 directionFromPoint(Point point) {
        return invertedDirection;
    }

    @Override
    public ISpectrum color() {
        return color;
    }
}
