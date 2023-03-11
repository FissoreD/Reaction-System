import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactionSystem.Graph;
import org.reactionSystem.Search;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class FixedPointTest {

    private Graph graph;
    private Search search;



    @BeforeEach
    void setUp(){
        UtilTest.setUp();

        search=new Search();
    }

    @Test
    void fixedPoint1() {
        graph=UtilTest.graph1;
        assertEquals(new HashMap<>(), search.fingFixedPoint(graph));

    }

    @Test
    void fixedPoint2() {
        graph=UtilTest.graph2;
        assertEquals(1, search.fingFixedPoint(graph).size());

    }

}


