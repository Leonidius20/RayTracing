package ua.leonidius.raytracing.input;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.Vector3;
import ua.leonidius.raytracing.shapes.Shape3d;
import ua.leonidius.raytracing.shapes.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WavefrontParserTest {

    @Test
    void read() throws IOException, ParserException {
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
        var shapes = new WavefrontParser(reader).parse();

        var expected = new ArrayList<Shape3d>();
        var expectedVertices = new Vector3[] {
                new Vector3(0.123, 0.234, 0.345),
                new Vector3(4, 5, 6),
                new Vector3(7, 4, 6),
        };
        var expectedNormals = new Vector3[] {
                new Vector3(-0.707, 2, 1).normalize(),
                new Vector3(0.707, 0, 0.707).normalize(),
                new Vector3( 1, 0, 4).normalize(),
        };
        expected.add(new Triangle(expectedVertices, expectedNormals));

        assertEquals(expected, shapes);
    }

    @Test
    void parseVectorDeclaration() throws ParserException {
        var reader = new WavefrontParser(null);

        // "v" vertex or "vn" normal
        String badLine = "v 5.76 7.67";
        assertThrows(ParserException.class, () -> {
            reader.parseVectorDeclaration(badLine);
        });

        String line = "v 5.76 7.67 -6.66";
        var expected = new Vector3(5.76, 7.67, -6.66);
        assertEquals(expected, reader.parseVectorDeclaration(line));
    }

    @Test
    void parsePolygonDeclaration() throws ParserException {
        var reader = new WavefrontParser(null);
        // options:
        /*
                f 1 2 3      (only vertices)
                f 3/1 4/2 5/3 (vetrex + texture)
                f 6/4/1 3/5/3 7/6/5 (vetrex + texture + normal)
                f 7//1 8//2 9//3 (vertex + normal)
         */

        String line = "f 1 2 3";

        var expected = new WavefrontParser.PolygonRecord(new int[] {1, 2, 3}, new int[] {-1,-1,-1});

        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));


        line = "f 3/1 4/2 5/3";
        expected = new WavefrontParser.PolygonRecord(new int[] {3, 4, 5}, new int[] {-1,-1,-1});
        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));

        line = "f 6/4/1 3/5/3 7/6/5";
        expected = new WavefrontParser.PolygonRecord(new int[] {6, 3, 7}, new int[] {1,3,5});
        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));

        line = "f 7//1 8//2 9//3";
        expected = new WavefrontParser.PolygonRecord(new int[] {7, 8, 9}, new int[] {1,2,3});
        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));
    }

    boolean polygonRecordsAreEqual(WavefrontParser.PolygonRecord r1,
                                   WavefrontParser.PolygonRecord r2) {
        if (r1 == r2) return true;
        if (r1 == null || r2 == null) return false;
        return Arrays.equals(r1.vertexIndices(), r2.vertexIndices()) && Arrays.equals(r2.normalIndices(), r1.normalIndices());
    }
}