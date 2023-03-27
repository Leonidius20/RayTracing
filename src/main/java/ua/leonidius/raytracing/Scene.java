package ua.leonidius.raytracing;

import lombok.Getter;
import lombok.Setter;
import ua.leonidius.raytracing.algorithm.ICamera;
import ua.leonidius.raytracing.algorithm.ILightSource;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.entities.Color;

import java.util.ArrayList;

public class Scene {

    @Getter private final ArrayList<IPrimitive> objects;

    @Getter @Setter ICamera activeCamera;

    @Getter @Setter ArrayList<ILightSource> lightSources;

    @Getter Color backgroundColor = Color.PINK;

    public Scene(ICamera camera, ILightSource light) {
        this(camera, light, new ArrayList<>());
    }

    @Deprecated
    public Scene(ICamera camera, ILightSource light, ArrayList<IPrimitive> shapes) {
        this.activeCamera = camera;
        this.lightSources = new ArrayList<>();
        this.lightSources.add(light);
        this.objects = shapes;
    }

    public Scene(ICamera camera) {
        this.activeCamera = camera;
        this.lightSources = new ArrayList<>();
        this.objects = new ArrayList<>();
    }

    public void add(IPrimitive obj) {
        objects.add(obj);
    }

    public void add(ILightSource light) {
        lightSources.add(light);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

}
