package org.reactionSystem;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    private Stream<Node> filterNodes(Predicate<Node> pred) {
        return nodes.values().stream().filter(pred);
    }

    public List<Node> getFixedPoints() {
        Predicate<Node> fixedPointPred = e -> e.getSuccessors().size() == 1 && e.getSuccessors().get(e.getName()) != null;
        return filterNodes(fixedPointPred).toList();
    }

    /**
     * Returns the list of nodes of the graphs having the activators,
     * but not the inhibitors
     */
    public List<Node> getNodes(Set<String> activator, Set<String> inhibitors) {
        Predicate<Node> nodesPred = e -> e.hasMolecule(activator) && e.hasNotMolecule(inhibitors);
        return filterNodes(nodesPred).toList();
    }

    @Override
    public String toString() {
        var res = new StringBuffer();
        this.nodes.forEach((nodeName, node) -> {
            res.append(nodeName).append(" -> ").append(node).append("\n");
        });
        return res.toString();
    }

    /**
     * @param result a set of names
     * @return the nodes having the same name as the one passed in result
     */
    public Node getNodeByName(Set<String> result) {
        Predicate<Node> nodeNamePred = e -> e.sameMolecules(result);
        var stream = filterNodes(nodeNamePred).findFirst();
        if (stream.isPresent())
            return stream.get();
        else
            throw new RuntimeException("Node " + result + " not found");
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }
}
