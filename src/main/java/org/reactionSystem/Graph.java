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
    // the map of nodes of the graph
    @JsonSerialize(using = GraphMapSerializer.class)
    private final Map<String, Node> nodes;

    public Graph(Node... nodes) {
        this.nodes = new HashMap<>();
        Arrays.stream(nodes).forEach(this::addNode);
    }

    public Graph() {
        this.nodes = new HashMap<>();
    }

    /**
     * 
     * @param json the json representation of the graph
     * @return the graph object
     * @throws JsonProcessingException
     */
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

    /**
     * 
     * @param graphString the string representation of the graph
     * @return the graph object
     */
    public static Graph fromJSON(String graphString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(graphString);
        return fromJSON(json);
    }

    /**
     * 
     * @param nodeName list of the name of the node to add
     */
    public void addNode(List<String> nodeName) {
        this.addNode(new Node(nodeName));
    }

    public void addNode(String nodeName, int id) {
        this.addNode(new Node(List.of(nodeName.equals("nil") ? new String[] {} : nodeName.split("-")), id));
    }

    /**
     * @param nodeName the name of the node to add
     *                 add the node to the graph
     */
    public void addNode(String nodeName) {
        this.addNode(List.of(nodeName));
    }

    /**
     * @param node the node to add
     *             add the node to the graph
     */
    public void addNode(Node node) {
        this.nodes.put(node.getName(), node);
    }

    /**
     * 
     * @param node1 the name of the first node
     * @param node2 the name of the second node
     * 
     *              add an edge from node1 to node2
     */
    public void addEdge(String node1, String node2) {
        this.getNodeByName(node1).addSuccessor(this.getNodeByName(node2));
    }

    private Stream<Node> filterNodes(Predicate<Node> pred) {
        return nodes.values().stream().filter(pred);
    }

    /**
     * @return the list of all fixed points of the graph
     */
    public List<Node> getFixedPoints() {
        Predicate<Node> fixedPointPred = e -> e.getSuccessors().get(e.getName()) != null;
        return filterNodes(fixedPointPred).toList();
    }

    /**
     * @return the list of all periodic points of the graph
     */
    public List<List<Node>> getPeriodicPoints() {
        return getNPeriodicPoints(0);
    }

    /**
     * @return the list of periodic points of the graph how have a period of n
     */
    public List<List<Node>> getNPeriodicPoints(int n) {
        Tarjan tarjan = new Tarjan();
        tarjan.findSCCs_Tarjan(this);
        List<List<String>> composante = tarjan.getComposante();
        List<List<Node>> res = new ArrayList<>();

        for (List<String> l : composante) {
            if ((n == 0 && l.size() > 1) || (n != 0 && l.size() == n)) {
                res.add(new ArrayList<>());
                l.forEach(e -> res.get(res.size() - 1).add(getNodes().get(e)));
            }
        }

        return res;
    }

    /**
     * @return the list of nodes that have access to a periodic point
     */
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

    /**
     * @param start the node to start the dfs
     * @param visit the map of visited nodes
     * @param PP    the list of periodic points
     * @return true if the node have access to a periodic point
     */
    private boolean dfs(String start, Map<String, Integer> visit, List<String> PP) {
        int index = 0;
        Stack<String> s = new Stack<>();
        s.push(start);
        while (!s.isEmpty()) {
            String v = s.pop();
            if (visit.get(v) == null) { // v not visited
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

    /**
     * add an edge from each node without successor to the nil node
     */
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

    /**
     * @return the graph in JSON format
     */
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

    /**
     * @param result a set of names
     * @return the nodes having the same name as the one passed in result
     */
    public Node getNodeByName(String... result) {
        return getNodeByName(Set.of(result));
    }

    /**
     * @param nodeName the name of the node
     * @return the node having the same name as the one passed in result
     */
    public Node getNodeByName(String nodeName) {
        return nodes.get(nodeName);
    }

    /**
     * @return the nodes of the graph
     */
    public Map<String, Node> getNodes() {
        return nodes;
    }
}
