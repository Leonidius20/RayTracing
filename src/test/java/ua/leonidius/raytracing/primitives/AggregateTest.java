package ua.leonidius.raytracing.primitives;

import org.junit.jupiter.api.Test;
import ua.leonidius.raytracing.algorithm.IPrimitive;
import ua.leonidius.raytracing.algorithm.IShadingModel;
import ua.leonidius.raytracing.algorithm.Intersection;
import ua.leonidius.raytracing.entities.BoundingBox;
import ua.leonidius.raytracing.entities.Point;
import ua.leonidius.raytracing.entities.Ray;
import ua.leonidius.raytracing.shapes.triangle.Triangle;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static ua.leonidius.raytracing.primitives.PrimitivesFactory.newTriangle;

public class AggregateTest {

    private static class SomeAggregate extends Aggregate {
        protected SomeAggregate(ArrayList<IPrimitive> primitives) {
            super(primitives);
        }

        @Override
        public Optional<Intersection> findVisibleIntersectionWithRay(Ray ray) {
            return Optional.empty();
        }

        @Override
        public Optional<Intersection> findAnyIntersectionWithRay(Ray ray) {
            return Optional.empty();
        }

        @Override
        public BoundingBox computeBoundingBox() {
            return calcBoundingBox();
        }
    }

    @Test
    void testCalcBoundingBox() {
        var triangle1 = newTriangle(new Point(0, 0, 0), new Point(0.5, 0.5, 0), new Point(0, 1, 1));
        var triangle2 = newTriangle(new Point(4, 4, 4), new Point(5, 3, 1), new Point(0, 1, 0));

        ArrayList<IPrimitive> primitives = new ArrayList<>(2);
        primitives.add(triangle1);
        primitives.add(triangle2);
        var aggregate = new SomeAggregate(primitives); // todo replace with mock?

        var expected = new BoundingBox(new Point(0, 0, 0), new Point(5, 4, 4));

        assertEquals(expected, aggregate.calcBoundingBox());
    }

}
