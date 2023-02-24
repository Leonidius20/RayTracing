package ua.leonidius.raytracing;

import org.apache.commons.cli.*;
import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.input.WavefrontParser;
import ua.leonidius.raytracing.output.PngImageWriter;
import ua.leonidius.raytracing.shapes.Plane;
import ua.leonidius.raytracing.shapes.Sphere;
import ua.leonidius.raytracing.shapes.Triangle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final int IMAGE_WIDTH = 854;
    private static final int IMAGE_HEIGHT = 480;

    public static void main(String[] args) throws IOException {
        // parsing CLI arguments
        var options = new Options();

        var outputOption = Option.builder("output")
                .argName("file")
                .hasArg()
                .desc("output file path")
                .build();

        var inputOption = Option.builder("input")
                .argName("file")
                .hasArg()
                .desc("input file path")
                .build();

        options.addOption(outputOption);
        options.addOption(inputOption);

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

        String inputFileName;
        if (line.hasOption("input")) {
            inputFileName = line.getOptionValue("input");
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("raytracing", options);
            return;
        }

        var shapes = new WavefrontParser(Files.newBufferedReader(Paths.get(inputFileName))).parse();
        var camera = new Camera(new Point(0, -1, 0), 30, IMAGE_HEIGHT, IMAGE_WIDTH, 0.0625, 0.0625);
        var lightSource = new DirectionalLightSource(new Vector3(1, -1, 0).normalize());
        var scene = new Scene(camera, lightSource);
        scene.setObjects(shapes);

        System.out.println("Read scene file, starting to render");
        var pixels = new Renderer(scene, ShadingModel.FLAT).render();

        (new PngImageWriter(outputFileName)).writeImage(pixels);
    }

    private static Scene createScene() {
        var sphere = new Sphere(new Point(0, 0, 0), 6.5);
        // TODO: test coordinates, see whether they mean what they are supposed to mean

        var sphereBehind = new Sphere(new Point(9 + 6, 2, 0), 4);
        var sphereBehind2 = new Sphere(new Point(9 - 32, 4, 0 + 16), 4);

        var plane = new Plane(new Point(0, 8, 0), new Vector3(0, -1, 1));

        var camera = new Camera(new Point(0, -28, 0), 30, IMAGE_HEIGHT, IMAGE_WIDTH, 0.125, 0.125);

        var lightSource = new DirectionalLightSource(new Vector3(-1, -1, 0).normalize());
        // why is y = -1? itn't the light shining into camera this way?

        var triangle = new Triangle(
                new Vector3(0, 0, 0),
                new Vector3(1, 0, 0),
                new Vector3(0, 0, 1)
        );

        var scene = new Scene(camera, lightSource);
        //scene.addObject(sphere);
        //scene.addObject(sphereBehind);
        //scene.addObject(sphereBehind2);
        //scene.addObject(plane);
        scene.add(triangle);

        return scene;
    }

}
