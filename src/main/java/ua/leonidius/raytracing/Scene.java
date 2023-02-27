package ua.leonidius.raytracing;

import lombok.Getter;
import lombok.Setter;
import ua.leonidius.raytracing.algorithm.ICamera;
import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.algorithm.IShape3d;

import java.util.ArrayList;

public class Scene {

    @Getter @Setter ArrayList<IShape3d> objects = new ArrayList<>();
    @Getter @Setter
    ICamera activeCamera;
    @Getter @Setter
    ILightSource lightSource;

    public Scene(ICamera camera, ILightSource light) {
        this.activeCamera = camera;
        this.lightSource = light;
    }

    public void add(IShape3d obj) {
        objects.add(obj);
    }

}
