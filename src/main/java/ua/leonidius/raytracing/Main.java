package ua.leonidius.raytracing;

import ua.leonidius.raytracing.algorithm.Renderer;
import ua.leonidius.raytracing.algorithm.*;
import ua.leonidius.raytracing.arguments.CliArgsParseException;
import ua.leonidius.raytracing.arguments.CliArguments;
import ua.leonidius.raytracing.arguments.MissingCliParameterException;
import ua.leonidius.raytracing.camera.PerspectiveCamera;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Vector3;
import ua.leonidius.raytracing.input.ParsedWavefrontFile;
import ua.leonidius.raytracing.input.ParsingException;
import ua.leonidius.raytracing.light.DirectionalLightSource;
import ua.leonidius.raytracing.output.PngImageWriter;
import ua.leonidius.raytracing.primitives.Instance;
import ua.leonidius.raytracing.primitives.kdtree.KdTree;
import ua.leonidius.raytracing.primitives.kdtree.KdTreeRecursiveIntersectionFinder;
import ua.leonidius.raytracing.primitives.kdtree.KdTreeValidator;
import ua.leonidius.raytracing.primitives.kdtree.MiddleSplitChooser;
import ua.leonidius.raytracing.shading.FlatShadingModel;
import ua.leonidius.raytracing.shapes.BoxOutline;
import ua.leonidius.raytracing.shapes.Sphere;
import ua.leonidius.raytracing.shapes.factories.TriangleFactory;
import ua.leonidius.raytracing.shapes.triangle.TriangleMesh;
import ua.leonidius.raytracing.transformations.RotationX;
import ua.leonidius.raytracing.transformations.RotationZ;
import ua.leonidius.raytracing.transformations.Translation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main implements IMonitoringCallback {

    private static final int IMAGE_WIDTH = 1280;
    private static final int IMAGE_HEIGHT = 720;

    private static final boolean ACCELERATE = true;
    private static final boolean SHADOWS_ENABLED = true;

    private static JLabel jLabel;
    private static BufferedImage img; // for monitoring

    private static JFrame frame;

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

        if (arguments.demoMode()) {
            System.out.println("Launching in demo mode");
        } else {
            System.out.println("Launching in regular mode");
        }

        // parsing input file
        var mesh = parseMesh(arguments.inputFile());

        // applying transformations
        applyDestructiveTransforms(mesh);

        var shapes = new ArrayList<IShape3d>(mesh.getFaces());        

        System.out.println("Read scene file (" + shapes.size() + " objects), starting to render");
        var pixelRenderer = new TrueColorPixelRenderer(SHADOWS_ENABLED);

        if (!arguments.demoMode()) {
             // creating a scene
            var scene = createScene(shapes, ACCELERATE);

            // showing gui
            showGUI();

            // rendering

            long startTime = System.nanoTime();
            var pixels = new Renderer(scene, pixelRenderer, new Main()).render();
            long endTime = System.nanoTime();

            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
            System.out.println("Time elapsed: " + duration / 1000000 + " ms");

            System.out.println("Done.");
            closeGui();

            // writing result to file
            (new PngImageWriter(arguments.outputFile())).writeImage(pixels);
        } else {
            // creating a non-accelerated scene
            var scene = createScene(shapes, false);
            // showing gui
            showGUI();

            var monitor = new Main();

            // rendering without acceleration
            long startTime = System.nanoTime();
            new Renderer(scene, pixelRenderer, monitor).render();
            long endTime = System.nanoTime();

            

            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

            System.out.println("Slow result: " + duration / 1000000 + " ms");

            // clear the canvas
            clearBufferedImage(img);

            // creating an accelerated scene
            scene = createScene(shapes, true);

            // rendering with acceleration
            startTime = System.nanoTime();
            new Renderer(scene, pixelRenderer, monitor).render();
            endTime = System.nanoTime();

            duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

            System.out.println("Fast result: " + duration / 1000000 + " ms");

            closeGui();
        }
    }

    @Override
    public void shareProgress(ua.leonidius.raytracing.entities.Color[][] pixels, int startX, int startY, int endX, int endY) {
        for (int x = startX; x <= endX; x++) {
            for (int y = 0; y < pixels.length; y++)
            {
                var color = pixels[y][x];

                int col = (color.r() << 16) | (color.g() << 8) | color.b();
                img.setRGB(x, y, col);
            }
        }
        jLabel.repaint();
    }

    private static void showGUI() {
        frame = new JFrame("Ray Tracing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D    graphics = img.createGraphics();

        graphics.setColor ( new Color( 20, 20, 20 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
        graphics.dispose();
        var imgIcon = new ImageIcon(img);
        jLabel = new JLabel(imgIcon);


        // imgIcon.setImage(ImageIO.read(new File("results/lab2.png")));

        frame.add(jLabel);

        frame.pack();
        frame.setVisible(true);
    }

    private static void closeGui() {
        frame.setVisible(false);
        frame.dispose();
    }

    private static TriangleMesh parseMesh(String inputFile) throws IOException {
        TriangleMesh mesh = null;
        try {
            mesh = new ParsedWavefrontFile(
                    Files.newBufferedReader(
                            Paths.get(inputFile)))
                    .shapes(new TriangleFactory());

            for (var shape : mesh.getFaces() ){
                 if (shape.getVertices()[0].equals(shape.getVertices()[1]) && shape.getVertices()[1].equals(shape.getVertices()[2])){
                     System.out.println("Found degenerate triangle");
                 }

            }
        } catch (ParsingException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return mesh;
    }

    private static void applyDestructiveTransforms(TriangleMesh mesh) {
        // combine with works in reverse way
        /*AffineTransform3d transform = new RotationX(90);
        mesh.applyTransformDestructive(transform);

       transform =new RotationZ(-45);
        mesh.applyTransformDestructive(transform);

        transform = new Translation(0, 3, -0.5);
        mesh.applyTransformDestructive(transform);*/

        var rotationX = new RotationX(90);
        var rotationZ = new RotationZ(-45);
        var translation = new Translation(0, 3, -0.5);

        var transform = translation.combineWith(rotationZ).combineWith(rotationX);
               // rotationX.combineWith(rotationZ).combineWith(translation);

        //transform = transform.combineWith(new Translation(0, 3, -0.5));
     //   transform = transform.combineWith(

        mesh.applyTransformDestructive(transform);

    }

    private static Scene createScene(ArrayList<IShape3d> shapes, boolean accelerate) {
        var sphere = new Sphere(new Point(0, 3, 0), 1);

        //shapes.add(sphere);
        // shapes.add(new Sphere(new Point(1, -2, 2 ), 0.5));

        var camera = new PerspectiveCamera(new Point(0, -3.4, 0), 0.7, IMAGE_HEIGHT, IMAGE_WIDTH, 0.00025, 0.00025);
        var lightSource = new DirectionalLightSource(new Vector3(0.5, -1, 1).normalize());
        var flatShading = new FlatShadingModel();
        ArrayList<IPrimitive> instances = shapes.stream().map(shape -> new Instance(shape, flatShading)).collect(Collectors.toCollection(ArrayList::new));
        var scene = new Scene(camera, lightSource);
        if (accelerate) {
            var kdTree = new KdTree(instances, new MiddleSplitChooser(), new KdTreeRecursiveIntersectionFinder());

            var validator = new KdTreeValidator();
            // validator.validate(kdTree);
       
            scene.add(kdTree); // todo get back


            Queue<KdTree.INode> queue = new LinkedList<>();
            queue.add(kdTree.root);
            while (!queue.isEmpty()) {
                var node = queue.poll();
                if (node instanceof KdTree.InteriorNode interiorNode) {
                    queue.add(interiorNode.leftChild());
                    queue.add(interiorNode.rightChild());
                } else if (node instanceof KdTree.LeafNode leafNode) {
                    var box = leafNode.debugInfo().aabb;
                    var boxOutline = BoxOutline.fromAABB(box);
                    //scene.add(new Instance(boxOutline, flatShading));
                }
            }

            var box1 = ((KdTree.InteriorNode)((KdTree.InteriorNode)((KdTree.InteriorNode)kdTree.root).rightChild()).rightChild()).leftChild() .debugInfo().aabb;
            var box2 = ((KdTree.InteriorNode)((KdTree.InteriorNode)((KdTree.InteriorNode)kdTree.root).rightChild()).rightChild()).rightChild() .debugInfo().aabb;

//            box2.getMaxPoint().z -= 0.33;
  //          box2.getMinPoint().z -= 0.33;

            System.out.println("Split by " + (((KdTree.InteriorNode)((KdTree.InteriorNode)((KdTree.InteriorNode) kdTree.root).rightChild()).rightChild())).splitAxis().toString());


           // scene.add(new Instance(BoxOutline.fromAABB(sphere.computeBoundingBox()), flatShading));
           // scene.add(new Instance(sphere, flatShading));
            //scene.add(new Instance(BoxOutline.fromAABB(box1), flatShading));
            //scene.add(new Instance(BoxOutline.fromAABB(box2), flatShading));
        } else {
            instances.forEach(scene::add);
        }
        return scene;
    }

    private static class EmptyRenderingMonitor implements IMonitoringCallback {
        @Override
        public void shareProgress(ua.leonidius.raytracing.entities.Color[][] pixels, int startX, int startY, int endX, int endY) {
        }
    }

    private static void clearBufferedImage(BufferedImage img) {
        Graphics2D graphics = img.createGraphics();

        graphics.setPaint ( new Color ( 0, 0, 0 ) );
        graphics.fillRect ( 0, 0, img.getWidth(), img.getHeight() );

        graphics.dispose();
    }

}
