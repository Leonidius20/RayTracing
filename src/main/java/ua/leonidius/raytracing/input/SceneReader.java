package ua.leonidius.raytracing.input;

import ua.leonidius.raytracing.Scene;

import java.io.IOException;

public interface SceneReader {

    public Scene read() throws IOException;

}
