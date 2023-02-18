package ua.leonidius.raytracing;

import lombok.Getter;

import java.util.ArrayList;

public class Scene {

    @Getter ArrayList<Shape3d> objects = new ArrayList<>();
    @Getter Camera activeCamera;
    @Getter DirectionalLightSource lightSource;

    public Scene(Camera camera, DirectionalLightSource light) {
        this.activeCamera = camera;
        this.lightSource = light;
    }

    public void addObject(Shape3d obj) {
        objects.add(obj);
    }

}
