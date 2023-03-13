package org.reactionSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.reactionSystem.jsonGraph.JsonGraph;

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

    public static Graph fromJSON(String graphString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(graphString);
        Graph graph = new Graph();
        var nodes = json.get("nodes");
        nodes.elements().forEachRemaining(e -> graph.addNode(
                e.get("data").get("name").asText(),
                e.get("data").get("id").asInt()));
        var edges = json.get("edges");
        edges.elements().forEachRemaining(e -> graph.addEdge(
                e.get("data").get("sourceName").asText(),
                e.get("data").get("targetName").asText()));
        return graph;
    }

    public void addNode(List<String> nodeName) {
        this.addNode(new Node(nodeName));
    }

    public void addNode(String nodeName, int id) {
        this.addNode(new Node(List.of(nodeName.split("-")), id));
    }

    public void addNode(String nodeName) {
        this.addNode(List.of(nodeName));
    }

    public void addNode(Node node) {
        this.nodes.put(node.getName(), node);
    }

    public void addEdge(String node1, String node2) {
        this.getNodeByName(node1).addSuccessor(this.getNodeByName(node2));
    }

    private Stream<Node> filterNodes(Predicate<Node> pred) {
        return nodes.values().stream().filter(pred);
    }

    public List<Node> getFixedPoints() {
        Predicate<Node> fixedPointPred = e -> e.getSuccessors().size() == 1 && e.getSuccessors().get(e.getName()) != null;
        return filterNodes(fixedPointPred).toList();
    }

    public Set<Node> getPeriodicPoints() {
        Tarjan tarjan=new Tarjan();
        tarjan.findSCCs_Tarjan(this);
        List<List<String>> composante=tarjan.getComposante();
        Set<Node> res=new HashSet<>();

        for(List<String> l: composante){
            if(l.size()>1) {
                l.forEach(e-> res.add(getNodes().get(e)));

            }
        }

        return res;
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

    public String toJson() {
        try {
            return JsonGraph.generateJSONGraph(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    public Node getNodeByName(String nodeName) {
        return nodes.get(nodeName);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }
}
