package org.reactionSystem.jsonGraph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactionSystem.Graph;
import org.reactionSystem.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonGraph {

    public static class NodeData{
        protected String id;
        protected String name;

        public NodeData(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
    public static class JsonNode{
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

    public static class EdgeData{
        protected String id;
        protected String source;
        protected String target;

        public EdgeData(String id, String source, String target) {
            this.id = id;
            this.source = source;
            this.target = target;
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
    }

    public static class JsonEdge{
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
    protected List<JsonNode> nodes;
    protected List<JsonEdge> edges;

    private JsonGraph() {
        nodes=new ArrayList<>();
        edges=new ArrayList<>();
    }

    public List<JsonNode> getNodes() {
        return nodes;
    }

    public List<JsonEdge> getEdges() {
        return edges;
    }

    public static String generateJSONGraph(Graph g) throws JsonProcessingException {
        JsonGraph graph=new JsonGraph();
        NodeData nd;
        EdgeData ed;
        JsonNode jn;
        JsonEdge je;
        List<String> classes = new ArrayList<>();
        classes.add(0,"postype");
        classes.add(null);
        for(Node n : g.getNodes()){
            nd = new NodeData(n.getName(),n.getName());
            jn = new JsonNode(nd,classes);
            graph.nodes.add(jn);
            for(Node n2 : n.getSuccessors().values()){
                ed = new EdgeData(n.getName()+"_"+n2.getName()+"_edge",n.getName(), n2.getName());
                je = new JsonEdge(ed,classes);
                graph.edges.add(je);
            }
        }
        return new ObjectMapper().writeValueAsString(graph);
    }
}
