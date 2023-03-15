import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueSuccessorTest {

    private Graph graph;

    @BeforeEach
    public void setUp() {
        UtilTest.setUp();
    }

    protected void uniqueSuccessor(Graph g) {
        g.getNodes().values().forEach(e -> assertEquals(1, e.getSuccessors().size()));
    }

    @Test
    public void uniqueSuccessor1() {
        graph = UtilTest.graph1;
        graph.addEdgeToNil();
        uniqueSuccessor(graph);
    }

    @Test
    public void uniqueSuccessor2() {
        graph = UtilTest.graph2;
        graph.addEdgeToNil();
        uniqueSuccessor(graph);
    }

    @Test
    public void uniqueSuccessor3() {
        graph = UtilTest.graph3;
        graph.addEdgeToNil();
        uniqueSuccessor(graph);
    }
}
