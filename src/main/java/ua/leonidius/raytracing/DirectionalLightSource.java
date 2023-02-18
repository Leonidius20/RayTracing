package ua.leonidius.raytracing;

import lombok.Getter;

public class DirectionalLightSource {
    
    @Getter Vector3 direction;

    public DirectionalLightSource(Vector3 direction) {
        this.direction = direction;
    }

}
