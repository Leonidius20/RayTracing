package ua.leonidius.raytracing;

import org.apache.commons.cli.*;
import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.output.PngImageWriter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // parsing CLI arguments
        var options = new Options();

        var outputOption = Option.builder("output")
                .argName("file")
                .hasArg()
                .desc("output file path")
                .build();

        options.addOption(outputOption);

        var parser = new GnuParser();
        CommandLine line;
        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing CLI args failed.  Reason: " + exp.getMessage());
            return;
        }

        String outputFileName;
        if (line.hasOption("output")) {
            outputFileName = line.getOptionValue("output");
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("raytracing", options);
            return;
        }

        var scene = createScene();
        var pixels = new Renderer(scene).render();
        (new PngImageWriter(outputFileName)).writeImage(pixels);
    }

    private static Scene createScene() {
        var sphere = new Sphere(new Point(0, 0, 0), 6.5);

        var sphereBehind = new Sphere(new Point(7, 2, 0), 4);

        var plane = new Plane(new Point(0, 0, -6), new Vector3(0, -0.1, 1));

        var camera = new Camera(new Point(0, -20, 0), 10, 100, 100, 0.25, 0.25);

        var lightSource = new DirectionalLightSource(new Vector3(-0.4, -1, 0).normalize());
        // why is y = -1? itn't the light shining into camera this way?

        var scene = new Scene(camera, lightSource);
        scene.addObject(sphere);
        scene.addObject(sphereBehind);
        scene.addObject(plane);

        return scene;
    }

}
