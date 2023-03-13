import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PeriodicPointTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        UtilTest.setUp();
    }

    @Test
    void periodicPoint1() {
        graph = UtilTest.graph1;
        assertTrue(graph.getPeriodicPoints().isEmpty());
    }

    @Test
    void periodicPoint2() {
        graph = UtilTest.graph2;
        assertTrue(graph.getPeriodicPoints().isEmpty());
    }

    @Test
    void periodicPoint3() {
        graph = UtilTest.graph3;
        assertEquals(3, graph.getPeriodicPoints().size());
    }
}


