package ua.leonidius.raytracing.enitites;

import java.util.Objects;

public class Color {

    private final int r;
    private final int g;
    private final int b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Create an RGD color from a grayscale value
     * @param value grayscale color value from 0 (black) to 1 (white)
     * @return color in rgb format
     */
    public static Color grayscale(double value) {
        return new Color((int) (255 * value), (int) (255 * value), (int) (255 * value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return r == color.r && g == color.g && b == color.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

}
