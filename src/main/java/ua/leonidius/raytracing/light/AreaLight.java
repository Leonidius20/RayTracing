package ua.leonidius.raytracing.light;

import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.entities.ISpectrum;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Vector3;

public class AreaLight implements ILightSource {

    private final ISpectrum color;
    private final double intensity;

    private static final Normal UP = new Normal(0, 0, 1);

    public AreaLight(ISpectrum color, double intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public Vector3 directionFromPoint(Point point) {
        // random from upper hemisphere
        var x = Math.random() * 2 - 1;
        var y = Math.random() * 2 - 1;
        var z = Math.random() * 2 - 1;

        return new Vector3(x, y, z).normalize();
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
