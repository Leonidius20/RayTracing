package ua.leonidius.raytracing.input;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.Vector3;

import java.security.Policy;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WavefrontSceneReaderTest {

    @Test
    void read() {
    }

    @Test
    void parseVectorDeclaration() {
        var reader = new WavefrontSceneReader(null);

        // "v" vertex or "vn" normal
        String badLine = "v 5.76 7.67";
        assertThrows(RuntimeException.class, () -> {
            reader.parseVectorDeclaration(badLine);
        });

        String line = "v 5.76 7.67 -6.66";
        var expected = new Vector3(5.76, 7.67, -6.66);
        assertEquals(expected, reader.parseVectorDeclaration(line));
    }

    @Test
    void parsePolygonDeclaration() {
        var reader = new WavefrontSceneReader(null);
        // options:
        /*
                f 1 2 3      (only vertices)
                f 3/1 4/2 5/3 (vetrex + texture)
                f 6/4/1 3/5/3 7/6/5 (vetrex + texture + normal)
                f 7//1 8//2 9//3 (vertex + normal)
         */

        String line = "f 1 2 3";

        var expected = new WavefrontSceneReader.PolygonRecord(new int[] {1, 2, 3}, new int[] {-1,-1,-1});

        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));


        line = "f 3/1 4/2 5/3";
        expected = new WavefrontSceneReader.PolygonRecord(new int[] {3, 4, 5}, new int[] {-1,-1,-1});
        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));

        line = "f 6/4/1 3/5/3 7/6/5";
        expected = new WavefrontSceneReader.PolygonRecord(new int[] {6, 3, 7}, new int[] {1,3,5});
        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));

        line = "f 7//1 8//2 9//3";
        expected = new WavefrontSceneReader.PolygonRecord(new int[] {7, 8, 9}, new int[] {1,2,3});
        assertTrue(polygonRecordsAreEqual(expected, reader.parsePolygonDeclaration(line)));
    }

    boolean polygonRecordsAreEqual(WavefrontSceneReader.PolygonRecord r1,
                                   WavefrontSceneReader.PolygonRecord r2) {
        if (r1 == r2) return true;
        if (r1 == null || r2 == null) return false;
        return Arrays.equals(r1.vertexIndices(), r2.vertexIndices()) && Arrays.equals(r2.normalIndices(), r1.normalIndices());
    }
}