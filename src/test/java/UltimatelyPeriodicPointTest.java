import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UltimatelyPeriodicPointTest {
    private Graph graph;

    @BeforeEach
    public void setUp() {
        UtilTest.setUp();
    }

    @Test
    public void UPP1() {
        graph = UtilTest.graph1;
        assertTrue(graph.getUltimatelyPeriodicPoint().isEmpty());
    }

    @Test
    public void UPP2() {
        graph = UtilTest.graph2;
        assertTrue(graph.getUltimatelyPeriodicPoint().isEmpty());
    }

    @Test
    public void UPP3() {
        graph = UtilTest.graph3;
        assertTrue(graph.getUltimatelyPeriodicPoint().isEmpty());
    }

    @Test
    public void UPP4() {
        graph = UtilTest.graph4;
        assertEquals(2, graph.getUltimatelyPeriodicPoint().size());
    }
}
