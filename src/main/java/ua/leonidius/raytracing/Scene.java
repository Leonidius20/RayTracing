package ua.leonidius.raytracing;

import lombok.Getter;
import lombok.Setter;
import ua.leonidius.raytracing.algorithm.ICamera;
import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.enitites.Color;

import java.util.ArrayList;

public class Scene {

    @Getter private final ArrayList<Instance> objects;
    @Getter @Setter
    ICamera activeCamera;
    @Getter @Setter
    ILightSource lightSource;

    @Getter Color backgroundColor = Color.PINK;

    public Scene(ICamera camera, ILightSource light) {
        this(camera, light, new ArrayList<>());
    }

    public Scene(ICamera camera, ILightSource light, ArrayList<Instance> shapes) {
        this.activeCamera = camera;
        this.lightSource = light;
        this.objects = shapes;
    }

    public void add(Instance obj) {
        objects.add(obj);
    }

}
