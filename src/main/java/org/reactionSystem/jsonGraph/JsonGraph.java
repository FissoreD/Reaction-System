package org.reactionSystem.jsonGraph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactionSystem.Graph;
import org.reactionSystem.Node;

import java.util.ArrayList;
import java.util.List;

public class JsonGraph {

    protected List<JsonNode> nodes;
    protected List<JsonEdge> edges;

    private JsonGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public static String generateJSONGraph(Graph g) throws JsonProcessingException {
        JsonGraph graph = new JsonGraph();
        NodeData nd;
        EdgeData ed;
        JsonNode jn;
        JsonEdge je;
        List<String> classes = new ArrayList<>();
        classes.add(0, "postype");
        classes.add(null);
        for (Node n1 : g.getNodes().values()) {
            nd = new NodeData(n1);
            jn = new JsonNode(nd, List.of("simpleNode"));
            graph.nodes.add(jn);
            for (Node n2 : n1.getSuccessors().values()) {
                ed = new EdgeData(n1, n2);
                je = new JsonEdge(ed, classes);
                graph.edges.add(je);
            }
        }
        return new ObjectMapper().writeValueAsString(graph);
    }

    public List<JsonNode> getNodes() {
        return nodes;
    }

    public List<JsonEdge> getEdges() {
        return edges;
    }

    public static class NodeData {
        protected String id;
        protected String name;

        public NodeData(Node node) {
            this.id = String.valueOf(node.id);
            this.name = node.getName().length() == 0 ? "nil" : node.getName();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class JsonNode {
        protected NodeData data;
        protected List<String> classes;

        public JsonNode(NodeData data, List<String> classes) {
            this.data = data;
            this.classes = classes;
        }

        public NodeData getData() {
            return data;
        }

        public List<String> getClasses() {
            return classes;
        }
    }

    public static class EdgeData {
        protected String id;
        protected String source;
        protected String target;
        protected String sourceName;
        protected String targetName;

        public EdgeData(Node source, Node target) {
            this.id = source.id + "_" + target.id + "_edge";
            this.source = String.valueOf(source.id);
            this.target = String.valueOf(target.id);
            this.sourceName = source.getName();
            this.targetName = target.getName();
        }

        public String getId() {
            return id;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }

        public String getSourceName() {
            return sourceName;
        }

        public String getTargetName() {
            return targetName;
        }
    }

    public static class JsonEdge {
        protected EdgeData data;
        protected List<String> classes;

        public JsonEdge(EdgeData data, List<String> classes) {
            this.data = data;
            this.classes = classes;
        }

        public EdgeData getData() {
            return data;
        }

        public List<String> getClasses() {
            return classes;
        }
    }
}
