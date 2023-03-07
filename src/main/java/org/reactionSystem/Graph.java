package org.reactionSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<String, Node> nodes;

    public Graph(Node... nodes) {
        this.nodes = new HashMap<>();
        Arrays.stream(nodes).forEach(this::addNode);
    }

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(List<String> nodeName) {
        this.addNode(new Node(nodeName));
    }

    public void addNode(Node node) {
        this.nodes.put(node.getName(), node);
    }

    @Override
    public String toString() {
        var res = new StringBuffer();
        this.nodes.forEach((nodeName, node) -> {
            res.append(nodeName + " -> " + node + "\n");
        });
        return res.toString();
    }
}
