package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.output.ConsoleImageWriter;

public class Main {

    public static void main(String[] args) {
        var sphere = new Sphere(new Point(0, 0, 0), 6);
        var camera = new Camera(new Point(0, -20, 0), 10, 40, 40, 0.25, 0.25);

        var lightSource = new DirectionalLightSource(new Vector3(-0.4, -1, 0).normalize());

        var scene = new Scene(camera, lightSource);
        scene.addObject(sphere);

        var pixels = Renderer.render(scene);
        /*var pixels = new double[][] {
                new double[]{ 0.0, 1.0, 0.0 },
                new double[]{ 1.0, 1.0, 1.0 },
                new double[]{ 0.0, 1.0, 0.0 }
        };*/
        (new ConsoleImageWriter()).writeImage(pixels);
    }

}
