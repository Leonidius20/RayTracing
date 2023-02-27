package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.enitites.Vector3;
import ua.leonidius.raytracing.algorithm.IShape3d;
import ua.leonidius.raytracing.shapes.Triangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParsedWavefrontFile implements ParsedGeometryFile {

    private final BufferedReader reader;

    public ParsedWavefrontFile(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public ArrayList<IShape3d> shapes() throws IOException, ParsingException {
        String line;

        var allVertices = new ArrayList<Vector3>(); // starts with 0 instead of 1, keep in mind
        var allNormals = new ArrayList<Vector3>();
        var shapes = new ArrayList<IShape3d>();

        int lineNumber = 0; // for error messages
        while ((line = reader.readLine()) != null) {
            lineNumber++;

            if (line.isBlank() || line.length() < 2) continue;

            var firstTwoChars = line.substring(0, 2);

            try {
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
            } catch (ParsingException e) {
                // rethrow but add line number info
                throw new ParsingException("Line " + lineNumber + ": " + e.getMessage());
            }
        }

        return shapes;
    }

    // just test the end function?
    /* private */ Vector3 parseVectorDeclaration(String line) throws ParsingException {
        var parts = line.split(" ");

        if (parts.length < 4) {
            throw new ParsingException("Error parsing vector declaration: less than 3 coordinates");
        }

        try {
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);

            return new Vector3(x, y, z);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing vector declaration: invalid number format");
        }

    }

    /* private */ record PolygonRecord(
            int[] vertexIndices,
            int[] normalIndices
    ) { }

    /* private */ PolygonRecord parsePolygonDeclaration(String line) throws ParsingException {
        var parts = line.split(" ");

        if (parts.length < 4) {
            throw new RuntimeException("Error parsing polygon declaration: less than 3 vertices");
        }

        int[] vertexIndices = new int[parts.length - 1];
        int[] normalIndices = new int[parts.length - 1];

        for (int i = 1; i < parts.length; i++) {
            String vertexDeclaration = parts[i];

            var vertexParts = vertexDeclaration.split("/");
            if (vertexParts.length == 0) {
                throw new ParsingException("Error parsing polygon declaration: vertex " + i + " has an empty declaration. Maybe there is an extra space.");
            }

            try {
                int vertexIndex = Integer.parseInt(vertexParts[0]);
                vertexIndices[i - 1] = vertexIndex;

                if (vertexParts.length < 3) {
                    normalIndices[i - 1] = -1;
                } else {
                    normalIndices[i - 1] = Integer.parseInt(vertexParts[2]);
                }
            } catch (NumberFormatException e) {
                throw new ParsingException("Error parsing polygon declaration: vertex " + i + " decl. has invalid numbers");
            }

        }

        return new PolygonRecord(vertexIndices, normalIndices);
    }

}
