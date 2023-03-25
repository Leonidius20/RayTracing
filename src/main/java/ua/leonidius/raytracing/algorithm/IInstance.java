package ua.leonidius.raytracing.algorithm;

public interface IInstance extends IPrimitive {

    IMaterial material();

    IShape3d geometry();

}
