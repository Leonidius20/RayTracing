package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.DepthMapPixelRenderer;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.algorithm.TrueColorPixelRenderer;
import ua.leonidius.raytracing.arguments.CliArgsParseException;
import ua.leonidius.raytracing.arguments.CliArguments;
import ua.leonidius.raytracing.arguments.MissingCliParameterException;
import ua.leonidius.raytracing.camera.PerspectiveCamera;
import ua.leonidius.raytracing.enitites.Point;
import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.input.ParsedWavefrontFile;
import ua.leonidius.raytracing.input.ParsingException;
import ua.leonidius.raytracing.light.DirectionalLightSource;
import ua.leonidius.raytracing.output.PngImageWriter;
import ua.leonidius.raytracing.shapes.factories.TriangleFactory;
import ua.leonidius.raytracing.shapes.triangle.TriangleMesh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    private static final int IMAGE_WIDTH = 854;
    private static final int IMAGE_HEIGHT = 480;

    public static void main(String[] args) throws IOException {
        // parsing CLI parameters
        IProgramArguments arguments;

        try {
            arguments = new CliArguments(args);
        } catch (CliArgsParseException exp) {
            System.err.println("Parsing CLI args failed.  Reason: " + exp.getMessage());
            return;
        } catch (MissingCliParameterException e) {
            return;
        }

        // parsing input file
        TriangleMesh mesh = null;
        try {
            mesh = new ParsedWavefrontFile(
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

        var shapes = new ArrayList<IShape3d>(mesh.getFaces());
        //var sphere = new Sphere(new Point(0, 0, 0), 1);

        //shapes.add(sphere);
       // shapes.add(new Sphere(new Point(1, -2, 2 ), 0.5));

        // creating a scene
        var camera = new PerspectiveCamera(new Point(0, -2.4, 0), 0.8, IMAGE_HEIGHT, IMAGE_WIDTH, 0.0005, 0.0005);
        var lightSource = new DirectionalLightSource(new Vector3(0.5, -1, 1).normalize());
        var flatShading = new PhongShadingModel();
        var instances = shapes.stream().map(shape -> new Instance(shape, flatShading)).collect(Collectors.toCollection(ArrayList::new));
        var scene = new Scene(camera, lightSource, instances);

        // rendering
        System.out.println("Read scene file (" + shapes.size() + " objects), starting to render");
        var pixelRenderer = new TrueColorPixelRenderer();
        var pixels = new Renderer(scene, pixelRenderer).render();

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
