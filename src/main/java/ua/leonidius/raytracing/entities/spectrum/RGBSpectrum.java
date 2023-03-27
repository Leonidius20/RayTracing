package ua.leonidius.raytracing.entities.spectrum;

import ua.leonidius.raytracing.entities.Color;
import ua.leonidius.raytracing.entities.ISpectrum;

public class RGBSpectrum implements ISpectrum {

    private final double[] values;

    public RGBSpectrum(double r, double g, double b) {
        values = new double[]{r, g, b};
    }

    @Override
    public ISpectrum multiplyBy(double factor) {
        return new RGBSpectrum(r() * factor, g() * factor, b() * factor);
    }

    @Override
    public ISpectrum multiplyBy(ISpectrum spectrum) {
        if (spectrum instanceof RGBSpectrum rgbSpectrum) {
            return new RGBSpectrum(r() * rgbSpectrum.r(), g() * rgbSpectrum.g(), b() * rgbSpectrum.b());
        }
        throw new UnsupportedOperationException("Not implemented yet");
        // todo: implement in a better way
    }

    @Override
    public ISpectrum add(ISpectrum spectrum) {
        if (spectrum instanceof RGBSpectrum rgbSpectrum) {
            return new RGBSpectrum(
                    Math.min(1.0, r() + rgbSpectrum.r()),
                    Math.min(1.0, g() + rgbSpectrum.g()),
                    Math.min(1.0, b() + rgbSpectrum.b()));
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public double r() {
        return values[0];
    }

    public double g() {
        return values[1];
    }

    public double b() {
        return values[2];
    }

    @Override
    public Color toRGB() {
        return Color.fromFloatUnitInterval(r(), g(), b());
    }

    public static RGBSpectrum fromRGB(Color color) {
        return new RGBSpectrum(color.r(), color.g(), color.b());
    }

}
