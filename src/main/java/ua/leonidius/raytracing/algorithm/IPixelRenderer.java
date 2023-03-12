package ua.leonidius.raytracing.algorithm;

import ua.leonidius.raytracing.Scene;
import ua.leonidius.raytracing.enitites.Color;

/**
 * Determines how an intersection between a primary ray and an object
 * is drawn (true color, false color etc.)
 */
public interface IPixelRenderer {

    Color renderIntersection(Scene scene, Intersection intersection);

}
