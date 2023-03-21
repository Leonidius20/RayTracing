package ua.leonidius.raytracing.primitives.kdtree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {

    @Test
    void buildTreeMiddleSplitTest() {
        var tree = new KdTree(new ArrayList<>(), new MiddleSplitChooser(), null);
    }
}