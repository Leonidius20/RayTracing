package ua.leonidius.raytracing;

import lombok.Getter;

import java.util.ArrayList;

public class Scene {

    ArrayList<Shape3d> objects;
    @Getter Camera activeCamera;
    LightSource lightSource;

    public Scene(Camera camera, LightSource light) {
        this.activeCamera = camera;
        this.lightSource = light;
    }

    public void addObject(Shape3d obj) {
        objects.add(obj);
    }

}
