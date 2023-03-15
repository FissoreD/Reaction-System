import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FixedPointTest {

    private Graph graph;

    @BeforeEach
    public void setUp() {
        UtilTest.setUp();
    }

    @Test
    public void fixedPoint1() {
        graph = UtilTest.graph1;
        assertTrue(graph.getFixedPoints().isEmpty());
    }

    @Test
    public void fixedPoint2() {
        graph = UtilTest.graph2;
        assertEquals(1, graph.getFixedPoints().size());
    }

    @Test
    public void fixedPoint3() {
        graph = UtilTest.graph3;
        assertTrue(graph.getFixedPoints().isEmpty());
    }
}


