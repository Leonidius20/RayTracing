package ua.leonidius.raytracing;

/**
 * Contains the max and the mix values of a ray's T parameter. This corresponds to
 * the fragment of a ray that intersects a bounding box, for example.
 */
public record RayFragment(double tMin, double tMax) {

}
