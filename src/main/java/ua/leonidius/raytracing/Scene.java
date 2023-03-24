package ua.leonidius.raytracing;

import lombok.Getter;
import lombok.Setter;
import ua.leonidius.raytracing.algorithm.ICamera;
import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.entities.Color;

import java.util.ArrayList;

public class Scene {

    // TODO: method to compute bounding box of the entire scene

    @Getter private final ArrayList<IPrimitive> objects;
    @Getter @Setter
    ICamera activeCamera;
    @Getter @Setter
    ILightSource lightSource;

    @Getter Color backgroundColor = Color.PINK;

    public Scene(ICamera camera, ILightSource light) {
        this(camera, light, new ArrayList<>());
    }

    public Scene(ICamera camera, ILightSource light, ArrayList<IPrimitive> shapes) {
        this.activeCamera = camera;
        this.lightSource = light;
        this.objects = shapes;
    }

    public void add(IPrimitive obj) {
        objects.add(obj);
    }

}
