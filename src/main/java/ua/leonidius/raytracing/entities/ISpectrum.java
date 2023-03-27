package ua.leonidius.raytracing.entities;

import ua.leonidius.raytracing.entities.spectrum.RGBSpectrum;

/**
 * A spectrum representation based on samples
 */
public interface ISpectrum {

    Color toRGB();

    ISpectrum multiplyBy(double factor);

    ISpectrum multiplyBy(ISpectrum spectrum);

    ISpectrum add(ISpectrum rgbSpectrum);
}
