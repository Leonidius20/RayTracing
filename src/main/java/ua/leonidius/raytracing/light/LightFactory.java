package ua.leonidius.raytracing.light;

import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.entities.Vector3;
import ua.leonidius.raytracing.entities.spectrum.RGBSpectrum;

// used for unit tests
public class LightFactory {

    private static final RGBSpectrum white = new RGBSpectrum(1, 1, 1);

    public static ILightSource newDirectionalLight(Vector3 direction) {
        return new DirectionalLightSource(direction.multiplyBy(-1), white);
    }

}
