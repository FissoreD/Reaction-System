package org.reactionSystem;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.*;

public class Node {

    private static int idCnt = 0;

    public final int id;
    private final String name;
    private final Set<String> molecules;
    @JsonSerialize(using = NodeMapSerializer.class)
    private final Map<String, Node> successors;
    @JsonSerialize(using = NodeMapSerializer.class)
    private final Map<String, Node> predecessors;

    public Node(List<String> moleculesName, int id) {
        this.molecules = new HashSet<>(moleculesName);
        this.name = String.join("-", this.molecules);
        this.successors = new HashMap<>();
        this.predecessors = new HashMap<>();
        this.id = id;
    }

    public Node(List<String> moleculesName) {
        this(moleculesName, idCnt++);
    }

    public Node(String... moleculesName) {
        this(List.of(moleculesName));
    }

    public void addSuccessor(Node node) {
        this.successors.put(node.name, node);
        node.predecessors.put(this.name, this);
    }

    public void addPredecessor(Node node) {
        node.addSuccessor(this);
    }

    public String getName() {
        return name;
    }

    public boolean sameMolecules(Set<String> molecules) {
        return molecules.equals(this.molecules);
    }

    public boolean hasMolecule(Set<String> moleculeName) {
        return this.molecules.containsAll(moleculeName);
    }

    public boolean hasNotMolecule(Set<String> moleculesName) {
        return moleculesName.stream().noneMatch(molecules::contains);
    }

    public Map<String, Node> getSuccessors() {
        return successors;
    }

    public Map<String, Node> getPredecessors() {
        return predecessors;
    }

    @Override
    public String toString() {
        var res = new StringBuffer();
        res.append("succ:{");
        successors.forEach((succName, _ignore) -> res.append(succName).append(";"));
        res.append("} -- pred:{");
        predecessors.forEach((predName, _ignore) -> res.append(predName).append(";"));
        res.append("}");
        return res.toString();
    }
}
