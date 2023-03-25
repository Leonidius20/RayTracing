package ua.leonidius.raytracing.entities;

/**
 * A spectrum representation based on samples
 */
public interface ISpectrum {

    Color toRGB();

    ISpectrum multiplyBy(double factor);

    ISpectrum multiplyBy(ISpectrum spectrum);

}
