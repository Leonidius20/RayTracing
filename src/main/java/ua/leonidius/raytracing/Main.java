package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.*;
import ua.leonidius.raytracing.algorithm.Renderer;
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
import ua.leonidius.raytracing.primitives.Instance;
import ua.leonidius.raytracing.primitives.kdtree.KdTree;
import ua.leonidius.raytracing.shapes.factories.TriangleFactory;
import ua.leonidius.raytracing.shapes.triangle.TriangleMesh;
import ua.leonidius.raytracing.transformations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main implements IMonitoringCallback {

    private static final int IMAGE_WIDTH = 854;
    private static final int IMAGE_HEIGHT = 480;

    private static JLabel jLabel;
    private static BufferedImage img; // for monitoring

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
        AffineTransform3d transform = new Scaling(1.5, 1, 1);
        transform = transform.combineWith(new RotationZ(-110));
        transform = transform.combineWith(new RotationX(45));
        transform = transform.combineWith(new Translation(-0.5, -0.1, 0.1));

        mesh.applyTransformDestructive(transform);

        var shapes = new ArrayList<IShape3d>(mesh.getFaces());

        //var sphere = new Sphere(new Point(0, 0, 0), 1);

        //shapes.add(sphere);
       // shapes.add(new Sphere(new Point(1, -2, 2 ), 0.5));

        // creating a scene
        var camera = new PerspectiveCamera(new Point(0, -2.4, 0), 0.8, IMAGE_HEIGHT, IMAGE_WIDTH, 0.0005, 0.0005);
        var lightSource = new DirectionalLightSource(new Vector3(0.5, -1, 1).normalize());
        var flatShading = new FlatShadingModel();
        ArrayList<IPrimitive> instances = shapes.stream().map(shape -> new Instance(shape, flatShading)).collect(Collectors.toCollection(ArrayList::new));
        var kdTree = KdTree.buildFor(instances);
        var scene = new Scene(camera, lightSource);
        scene.add(kdTree);

        // showing gui
        var frame = new JFrame("Ray Tracing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(camera.sensorWidth(), camera.sensorHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D    graphics = img.createGraphics();

        graphics.setColor ( new Color( 20, 20, 20 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
        var imgIcon = new ImageIcon(img);
        jLabel = new JLabel(imgIcon);


        // imgIcon.setImage(ImageIO.read(new File("results/lab2.png")));

        frame.add(jLabel);

        frame.pack();
        frame.setVisible(true);

        // rendering
        System.out.println("Read scene file (" + shapes.size() + " objects), starting to render");
        var pixelRenderer = new TrueColorPixelRenderer();

        var pixels = new Renderer(scene, pixelRenderer, new Main()).render();

        System.out.println("Done.");
        frame.setVisible(false);
        frame.dispose();

        // writing result to file
        (new PngImageWriter(arguments.outputFile())).writeImage(pixels);

    }

    @Override
    public void shareProgress(ua.leonidius.raytracing.enitites.Color[][] pixels, int startX, int startY, int endX, int endY) {
        for (int x = startX; x <= endX; x++){
            for (int y = 0; y < pixels.length; y++)
            {
                var color = pixels[y][x];

                int col = (color.r() << 16) | (color.g() << 8) | color.b();
                img.setRGB(x, y, col);
            }
        }
        jLabel.repaint();
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
