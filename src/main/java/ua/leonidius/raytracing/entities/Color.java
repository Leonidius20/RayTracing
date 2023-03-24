package ua.leonidius.raytracing.entities;

import java.util.Objects;

public record Color(int r, int g, int b) {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color PINK = new Color(255,192,203);

    /**
     * Create an RGD color from a grayscale value
     *
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

    public int toGrayscale() {
        return (int) (0.3 * r + 0.59 * g + 0.11 * b);
    }

}
