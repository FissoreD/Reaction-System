package org.reactionSystem;


import java.util.*;

public class Tarjan {

    private Graph graph;
    private Map<String, Integer> dfs = new HashMap<>();
    private Map<String, Integer> low = new HashMap<>();
    private Map<String, Boolean> inStack = new HashMap<>();
    private Stack<String> stack = new Stack<>();
    private int time = 0;
    private int n;

    private List<List<String>> composante = new ArrayList<>();


    private void DFS(String u) {
        dfs.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        inStack.put(u, true);
        Map<String, Node> temp = graph.getNodes().get(u).getSuccessors(); // get the list of edges from the node.

        if (temp == null)
            return;

        for (Map.Entry<String, Node> entry : temp.entrySet()) {
            if (dfs.get(entry.getKey()) == -1) //If v is not visited
            {
                DFS(entry.getKey());
                low.put(u, Math.min(low.get(u), low.get(entry.getKey())));
            }
//Differentiate back-edge and cross-edge
            else if (inStack.get(entry.getKey())) //Back-edge case
                low.put(u, Math.min(low.get(u), dfs.get(entry.getKey())));
        }

        if (Objects.equals(low.get(u), dfs.get(u))) //If u is head node of SCC
        {
            List<String> tmp = new ArrayList<>();
            while (!Objects.equals(stack.peek(), u)) {
                //adj.getNodes().put(stack.peek(), new int[0]);
                tmp.add(stack.peek());
                inStack.put(stack.peek(), false);
                stack.pop();
            }
            tmp.add(stack.peek());
            if (tmp.size() > 1 || graph.getNodes().get(tmp.get(0)).getSuccessors().containsKey(tmp.get(0))) {
                composante.add(tmp);
            }
            inStack.put(stack.peek(), false);
            stack.pop();
        }
    }

    public void findSCCs_Tarjan(Graph graph) {
        composante = new ArrayList<>();
        this.graph = graph;
        this.n = graph.getNodes().size();

        this.dfs = new HashMap<>();
        this.low = new HashMap<>();
        this.inStack = new HashMap<>();

        this.n -= 1;

        for (Map.Entry<String, Node> entry : graph.getNodes().entrySet()) {
            dfs.put(entry.getKey(), -1);
            low.put(entry.getKey(), -1);
            inStack.put(entry.getKey(), false);
        }
        for (Map.Entry<String, Node> entry : graph.getNodes().entrySet()) {
            if (dfs.get(entry.getKey()) == -1)
                DFS(entry.getKey());   // call DFS for each undiscovered node.
        }
    }

    public List<List<String>> getComposante() {
        return composante;
    }
}
