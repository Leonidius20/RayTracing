package ua.leonidius.raytracing.material;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MirrorMaterialTest {

    @Test
    void createSecondaryRay() {
        var material = new MirrorMaterial(mock(ISpectrum.class));

        var incidentRayDirection = new Vector3(1, 0, -1).normalize();
        var normal = new Normal(0, 0, 1).normalize();

        var expectedReflectedDirection = new Vector3(1, 0, 1).normalize();

        assertEquals(expectedReflectedDirection,
                material.getSecondaryRayDirection(incidentRayDirection, normal));
    }
}