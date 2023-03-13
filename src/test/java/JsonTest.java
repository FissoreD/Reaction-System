import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        UtilTest.setUp();
    }

    @Test
    void JSON_reflexive() throws JsonProcessingException {
        graph = UtilTest.graph1;
        assertEquals(Graph.fromJSON(graph.toJson()).toJson(), graph.toJson());
    }

    @Test
    void fixedPoint2() throws JsonProcessingException {
        graph = UtilTest.graph2;
        assertEquals(Graph.fromJSON(graph.toJson()).toJson(), graph.toJson());
    }
}
