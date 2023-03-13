import org.reactionSystem.Graph;
import org.reactionSystem.Node;

public class UtilTest {
    public static Graph graph1;
    public static Graph graph2;
    public static Graph graph3;

    public static void setUp() {
        Node P1 = new Node("P1");
        Node P2 = new Node("P2");
        Node P3 = new Node("P3");
        Node M1 = new Node("M1");

        P1.addSuccessor(P2);
        P2.addSuccessor(P3);
        M1.addSuccessor(P3);

        graph1 = new Graph(P1, P2, P3, M1);

        Node P1bis = new Node("P1");
        Node P2bis = new Node("P2");
        Node P3bis = new Node("P3");
        Node M1bis = new Node("M1");

        P1bis.addSuccessor(P2bis);
        P2bis.addSuccessor(P3bis);
        M1bis.addSuccessor(P3bis);
        P3bis.addSuccessor(P3bis);

        graph2 = new Graph(P1bis, P2bis, P3bis, M1bis);

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");


        A.addSuccessor(B);
        B.addSuccessor(C);
        C.addSuccessor(A);

        graph3= new Graph(A, B, C);
    }


}
