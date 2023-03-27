package ua.leonidius.raytracing.input;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.entities.Normal;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Vector3;
import ua.leonidius.raytracing.shapes.factories.TriangleFactory;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ParsedWavefrontFileTest {

    @Test
    void read() throws IOException, ParsingException {
        String file = """
                # comment
                v 0.123 0.234 0.345
                v 4 5 6
                v 7 4 6
                
                vn 0.707 0 0.707
                vn 1 0 4
                vn -0.707 2 1
                
                f 1//3 2//1 3//2""";

        var reader = new BufferedReader(new StringReader(file));
        var shapes = new ParsedWavefrontFile(reader).shapes(new TriangleFactory());

        var expected = new ArrayList<IShape3d>();
        var expectedVertices = new Point[] {
                new Point(0.123, 0.234, 0.345),
                new Point(4, 5, 6),
                new Point(7, 4, 6),
        };
        var expectedNormals = new Normal[] {
                new Normal(-0.707, 2, 1).normalize(),
                new Normal(0.707, 0, 0.707).normalize(),
                new Normal( 1, 0, 4).normalize(),
        };
        expected.add(new Triangle(expectedVertices, expectedNormals));

        assertEquals(expected, shapes.getFaces());
    }

    @Test
    void parseBadVertexDeclaration() {
        String badLine = "v 5.76 7.67";
        var reader1 = new ParsedWavefrontFile(new BufferedReader(new StringReader(badLine)));

        assertThrows(ParsingException.class, () -> {
            reader1.shapes(mock(ITriangleFactory.class));
        });

        badLine = "v 5.76 7.67 8.9 9.0";
        var reader2 = new ParsedWavefrontFile(new BufferedReader(new StringReader(badLine)));

        assertThrows(ParsingException.class, () -> {
            reader2.shapes(mock(ITriangleFactory.class));
        });

        badLine = "v 5.76 7.67 5text";
        var reader3 = new ParsedWavefrontFile(new BufferedReader(new StringReader(badLine)));

        assertThrows(ParsingException.class, () -> {
            reader3.shapes(mock(ITriangleFactory.class));
        });

        badLine = "vn 5.76 7.67 4text";
        var reader4 = new ParsedWavefrontFile(new BufferedReader(new StringReader(badLine)));

        assertThrows(ParsingException.class, () -> {
            reader4.shapes(mock(ITriangleFactory.class));
        });

        badLine = "vn 5.76 7.67";
        var reader5 = new ParsedWavefrontFile(new BufferedReader(new StringReader(badLine)));

        assertThrows(ParsingException.class, () -> {
            reader5.shapes(mock(ITriangleFactory.class));
        });
    }

    @Test
    void testTrianglesCount() throws URISyntaxException, IOException, ParsingException {
        var fileUrl = getClass().getClassLoader().getResource("cow.obj");
        var reader = new ParsedWavefrontFile(Files.newBufferedReader(Path.of(fileUrl.toURI())));
        var triangles = reader.shapes(new TriangleFactory());
        int expectedCount = 5144;
        assertEquals(expectedCount, triangles.getFaces().size());
    }

}