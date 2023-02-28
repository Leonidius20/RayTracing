package ua.leonidius.raytracing;

import org.apache.commons.cli.*;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.arguments.CliArguments;
import ua.leonidius.raytracing.arguments.MissingCliParameterException;
import ua.leonidius.raytracing.camera.Camera;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.input.ParsedWavefrontFile;
import ua.leonidius.raytracing.input.ParsingException;
import ua.leonidius.raytracing.light.DirectionalLightSource;
import ua.leonidius.raytracing.output.PngImageWriter;
import ua.leonidius.raytracing.shapes.Sphere;
import ua.leonidius.raytracing.shapes.Triangle;
import ua.leonidius.raytracing.shapes.factories.TriangleFactory;
import ua.leonidius.raytracing.transformations.AffineTransform3d;
import ua.leonidius.raytracing.transformations.RotationZMatrix;
import ua.leonidius.raytracing.transformations.ScaleTransform3d;
import ua.leonidius.raytracing.transformations.TranslationMatrix3d;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    private static final int IMAGE_WIDTH = 854;
    private static final int IMAGE_HEIGHT = 480;

    public static void main(String[] args) throws IOException {
        // parsing CLI parameters
        IProgramArguments arguments;

        try {
            arguments = new CliArguments(args);
        } catch (ParseException exp) {
            System.err.println("Parsing CLI args failed.  Reason: " + exp.getMessage());
            return;
        } catch (MissingCliParameterException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("raytracing", e.getOptions());
            return;
        }

        // parsing input file
        ArrayList<IShape3d> shapes = null;
        try {
            shapes = new ParsedWavefrontFile(
                    Files.newBufferedReader(
                            Paths.get(arguments.inputFile())))
                    .shapes(new TriangleFactory());
        } catch (ParsingException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        // applying transformations
       // AffineTransform3d rotation = new RotationZMatrix(0);
       /* AffineTransform3d rotation = new TranslationMatrix3d(0.1, 0.1, 0.1);
        //rotation = rotation.multiplyBy(new TranslationMatrix3d(0.1, 0.1, 0.1));
        rotation = new ScaleTransform3d(1.5, 1, 1);
        rotation = rotation.multiplyBy(new RotationZMatrix(-90));
        rotation = rotation.multiplyBy(new TranslationMatrix3d(0, 0.1, 0));

        for (int i = 0; i < shapes.size(); i++) {
            var s = shapes.get(i);
            if (s instanceof Triangle t) {
                shapes.set(i, t.applyTransform(rotation));
            }
        }*/

        // adding a sphere
        var sphere = new Sphere(new Point(0.7, -0.5, 0), 1);
        shapes.add(sphere);

        // creating a scene
        var camera = new Camera(new Point(0, -1, 0), 30, IMAGE_HEIGHT, IMAGE_WIDTH, 0.0625, 0.0625);
        var lightSource = new DirectionalLightSource(new Vector3(1, -1, 0).normalize());
        var scene = new Scene(camera, lightSource, shapes);

        // rendering
        System.out.println("Read scene file (" + shapes.size() + " objects), starting to render");
        var pixels = new Renderer(scene, ShadingModel.FLAT).render();

        // writing result to file
        (new PngImageWriter(arguments.outputFile())).writeImage(pixels);
    }

    /*private static Scene createScene() {
        var sphere = new Sphere(new Point(0, 0, 0), 6.5);
        // TODO: test coordinates, see whether they mean what they are supposed to mean

        var sphereBehind = new Sphere(new Point(9 + 6, 2, 0), 4);
        var sphereBehind2 = new Sphere(new Point(9 - 32, 4, 0 + 16), 4);

        var plane = new Plane(new Point(0, 8, 0), new Vector3(0, -1, 1));

        var camera = new Camera(new Point(0, -28, 0), 30, IMAGE_HEIGHT, IMAGE_WIDTH, 0.125, 0.125);

        var lightSource = new DirectionalLightSource(new Vector3(-1, -1, 0).normalize());
        // why is y = -1? itn't the light shining into camera this way?

        var triangle = new Triangle(
                new Point(0, 0, 0),
                new Point(1, 0, 0),
                new Point(0, 0, 1)
        );

        var scene = new Scene(camera, lightSource);
        //scene.addObject(sphere);
        //scene.addObject(sphereBehind);
        //scene.addObject(sphereBehind2);
        //scene.addObject(plane);
        scene.add(triangle);

        return scene;
    }*/

}
