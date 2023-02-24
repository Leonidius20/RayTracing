package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.Vector3;
import ua.leonidius.raytracing.shapes.Shape3d;
import ua.leonidius.raytracing.shapes.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WavefrontParser implements GeometryFileParser {

    private final BufferedReader reader;

    public WavefrontParser(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public ArrayList<Shape3d> parse() throws IOException, RuntimeException {
        String line;

        var allVertices = new ArrayList<Vector3>(); // starts with 0 instead of 1, keep in mind
        var allNormals = new ArrayList<Vector3>();
        var shapes = new ArrayList<Shape3d>();

        while ((line = reader.readLine()) != null) {
            if (line.isBlank() || line.length() < 2) continue;

            var firstTwoChars = line.substring(0, 2);
            switch (firstTwoChars) {
                case "v " ->
                        // vertex
                        allVertices.add(parseVectorDeclaration(line));
                case "vn" ->
                        // normals
                        allNormals.add(parseVectorDeclaration(line).normalize());
                case "f " -> {
                    // face (triangle or polygon)
                    var record = parsePolygonDeclaration(line);
                    Vector3[] vertices = new Vector3[3];
                    Vector3[] normals = new Vector3[3];
                    for (int i = 0; i < 3; i++) {
                        vertices[i] = allVertices.get(record.vertexIndices[i] - 1);
                        var normalIndex = record.normalIndices[i];
                        normals[i] = normalIndex != -1 ? allNormals.get(normalIndex - 1) : null;
                    }
                    shapes.add(new Triangle(vertices, normals));
                }
                default -> {
                    // ignore for now
                }
            }


        }

        return shapes;
    }

    // just test the end function?
    /* private */ Vector3 parseVectorDeclaration(String line) {
        var parts = line.split(" ");

        if (parts.length < 4) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING,
                    "error parsing vertex declaration"); // todo test if it's OK
            throw new RuntimeException("Error parsing vector declaration");
        }

        try {
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);

            return new Vector3(x, y, z);
        } catch (NumberFormatException e) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING,
                    "error parsing vector declaration"); // todo test if it's OK
            throw new RuntimeException("Error parsing vector declaration");
        }

    }

    /* private */ record PolygonRecord(
            int[] vertexIndices,
            int[] normalIndices
    ) { }

    /* private */ PolygonRecord parsePolygonDeclaration(String line) {
        var parts = line.split(" ");

        if (parts.length < 4) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING,
                    "error parsing polygon declaration"); // todo test if it's OK
            throw new RuntimeException("Error parsing polygon declaration");
        }

        int[] vertexIndices = new int[parts.length - 1];
        int[] normalIndices = new int[parts.length - 1];

        for (int i = 1; i < parts.length; i++) {
            String vertexDeclaration = parts[i];

            var vertexParts = vertexDeclaration.split("/");
            if (vertexParts.length == 0) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING,
                        "error parsing polygon declaration"); // todo test if it's OK
                throw new RuntimeException("Error parsing polygon declaration");
            }

            try {
                int vertexIndex = Integer.parseInt(vertexParts[0]);
                vertexIndices[i - 1] = vertexIndex;

                if (vertexParts.length < 3) {
                    // todo: not only check length but also v1// type records (with slashes but with empty values)
                    normalIndices[i - 1] = -1;
                } else {
                    normalIndices[i - 1] = Integer.parseInt(vertexParts[2]);
                }
            } catch (NumberFormatException e) {
                Logger.getLogger(getClass().getName()).log(Level.WARNING,
                        "error parsing polygon declaration"); // todo test if it's OK
                throw new RuntimeException("Error parsing polygon declaration");
            }

        }

        return new PolygonRecord(vertexIndices, normalIndices);
    }

}
