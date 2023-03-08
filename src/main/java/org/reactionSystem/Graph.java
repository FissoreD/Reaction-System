package org.reactionSystem;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.*;

public class Graph {
    @JsonSerialize(using = GraphMapSerializer.class)
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

    /**
     * Returns the list of nodes of the graphs having the activators,
     * but not the inhibitors
     */
    public List<Node> getNodes(Set<String> activator, Set<String> inhibitors) {
        return this.nodes.values().stream().filter(e -> e.hasMolecule(activator) && e.hasNotMolecule(inhibitors)).toList();
    }

    @Override
    public String toString() {
        var res = new StringBuffer();
        this.nodes.forEach((nodeName, node) -> {
            res.append(nodeName).append(" -> ").append(node).append("\n");
        });
        return res.toString();
    }

    public Node getNode(Set<String> result) {
        return this.nodes.values().stream().filter(e -> e.sameMolecules(result)).findFirst().get();
    }
}
