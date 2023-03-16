import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;
import org.reactionSystem.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    private Graph graph;

    @BeforeEach
    public void setUp() {
        UtilTest.setUp();
    }

    @Test
    public void JSON_reflexive1() throws JsonProcessingException {
        graph = UtilTest.graph1;
        assertEquals(Graph.fromJSON(graph.toJson()).toJson(), graph.toJson());
    }

    @Test
    public void JSON_reflexive2() throws JsonProcessingException {
        graph = UtilTest.graph2;
        assertEquals(Graph.fromJSON(graph.toJson()).toJson(), graph.toJson());
    }

    @Test
    public void JSON_reflexive3() throws JsonProcessingException {
        graph = UtilTest.graph3;
        assertEquals(Graph.fromJSON(graph.toJson()).toJson(), graph.toJson());
    }

    @Test
    public void JSON_fromTxt() throws JsonProcessingException {
        var model = Parser.parseCnt("a;b;b");
        model.buildGraph();
        var json = model.getGraph().toJson();
        Graph.fromJSON(json);
    }

    @Test
    public void graphFromString1() {
        var m = Parser.parseCnt("a;;b\nb;;a\n;;c\nc;;d\nd;;");
        m.buildGraph();
    }

    @Test
    public void graphFromString2() {
        var m = Parser.parseCnt("a e ff q fq sdf fdqq f f d s  q sd f q sd f qs df ;b;c");
        m.buildGraph();
    }
}
