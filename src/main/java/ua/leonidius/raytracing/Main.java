package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.output.PngImageWriter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var sphere = new Sphere(new Point(0, 0, 0), 6);

        var sphereBehind = new Sphere(new Point(5, 2, 0), 4);

        var plane = new Plane(new Point(0, 0, -6), new Vector3(0, -0.1, 1));

        var camera = new Camera(new Point(0, -20, 0), 10, 40, 40, 0.25, 0.25);

        var lightSource = new DirectionalLightSource(new Vector3(-0.4, -1, 0).normalize());
        // why is y = -1? itn't the light shining into camera this way?

        var scene = new Scene(camera, lightSource);
        scene.addObject(sphere);
        scene.addObject(sphereBehind);
        scene.addObject(plane);

        var pixels = new Renderer(scene).render();

        (new PngImageWriter("output.png")).writeImage(pixels);
    }

}
