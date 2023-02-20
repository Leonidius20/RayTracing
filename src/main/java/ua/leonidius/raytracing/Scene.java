package ua.leonidius.raytracing;

import lombok.Getter;
import lombok.Setter;
import ua.leonidius.raytracing.shapes.Shape3d;

import java.util.ArrayList;

public class Scene {

    @Getter ArrayList<Shape3d> objects = new ArrayList<>();
    @Getter @Setter Camera activeCamera;
    @Getter @Setter DirectionalLightSource lightSource;

    public Scene(Camera camera, DirectionalLightSource light) {
        this.activeCamera = camera;
        this.lightSource = light;
    }

    public void add(Shape3d obj) {
        objects.add(obj);
    }

}
