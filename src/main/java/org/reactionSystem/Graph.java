package org.reactionSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.reactionSystem.jsonGraph.JsonGraph;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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

    public static Graph fromJSON(JsonNode json) throws JsonProcessingException {
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

    public static Graph fromJSON(String graphString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(graphString);
        return fromJSON(json);
    }

    public void addNode(List<String> nodeName) {
        this.addNode(new Node(nodeName));
    }

    public void addNode(String nodeName, int id) {
        this.addNode(new Node(List.of(nodeName.equals("nil") ? new String[]{} : nodeName.split("-")), id));
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
        Predicate<Node> fixedPointPred = e -> e.getSuccessors().get(e.getName()) != null;
        return filterNodes(fixedPointPred).toList();
    }

    public List<List<Node>> getPeriodicPoints() {
        return getNPeriodicPoints(0);
    }

    public List<List<Node>> getNPeriodicPoints(int n) {
        Tarjan tarjan = new Tarjan();
        tarjan.findSCCs_Tarjan(this);
        List<List<String>> composante = tarjan.getComposante();
        List<List<Node>> res = new ArrayList<>();

        for (List<String> l : composante) {
            if ((n == 0 && l.size() > 1) || (n != 0 && l.size() == n)) {
                res.add(new ArrayList<>());
                l.forEach(e -> res.get(res.size()-1).add(getNodes().get(e)));

            }
        }

        return res;
    }

    public Set<Node> getUltimatelyPeriodicPoint() {
        List<List<Node>> PP = getPeriodicPoints();
        List<String> PPName = new ArrayList<>(); // PP.stream().map(Node::getName).collect(Collectors.toSet());

        for (List<Node> nodeList : PP) {
            for (Node node : nodeList) {
                PPName.add(node.getName());
            }
        }

        Set<Node> res = new HashSet<>();

        for (Map.Entry<String, Node> entry : getNodes().entrySet()) {
            if (!PPName.contains(entry.getKey()) && dfs(entry.getKey(), new HashMap<>(), PPName)) {
                res.add(entry.getValue());
            }
        }


        return res;
    }

    private boolean dfs(String start, Map<String, Integer> visit, List<String> PP) {
        int index = 0;
        Stack<String> s = new Stack<>();
        s.push(start);
        while (!s.isEmpty()) {
            String v = s.pop();
            if (visit.get(v) == null) { //v not visited
                visit.put(v, index);
                if (PP.contains(v)) {
                    return true;
                }
                index += 1;
                for (Map.Entry<String, Node> entry : getNodes().get(v).getSuccessors().entrySet()) {
                    s.push(entry.getKey());
                }
            }
        }
        return false;
    }

    public void addEdgeToNil() {
        if (this.nodes.get("") == null)
            addNode("");
        Predicate<Node> nilPred = e -> e.getSuccessors().size() == 0;
        filterNodes(nilPred).forEach(e -> e.addSuccessor(getNodeByName("")));
    }

    /**
     * Returns the list of nodes of the graphs having the activators,
     * but not the inhibitors
     */
    public List<Node> getNodes(Set<String> activator, Set<String> inhibitors) {
        var inhibitorEmpty = inhibitors.size() == 1 && inhibitors.contains("");
        Predicate<Node> nodesPred = e -> e.hasMolecule(activator) && (inhibitorEmpty || e.hasNotMolecule(inhibitors));
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

    public Node getNodeByName(String... result) {
        return getNodeByName(Set.of(result));
    }

    public Node getNodeByName(String nodeName) {
        return nodes.get(nodeName);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }
}
