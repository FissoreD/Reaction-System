package org.reactionSystem;

import java.util.*;

public class Node {
    private final String name;
    private final Set<String> molecules;
    private final Map<String, Node> successors;
    private final Map<String, Node> predecessors;

    public Node(List<String> moleculesName) {
        this.molecules = new HashSet<>(moleculesName);
        this.name = String.join("-", this.molecules);
        this.successors = new HashMap<>();
        this.predecessors = new HashMap<>();
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

    public boolean hasMolecule(String moleculeName) {
        return this.molecules.contains(moleculeName);
    }

    @Override
    public String toString() {
        var res = new StringBuffer();
        res.append("succ:{");
        successors.forEach((succName, _ignore) -> res.append(succName));
        res.append("} -- pred:{");
        predecessors.forEach((predName, _ignore) -> res.append(predName));
        res.append("}");
        return res.toString();
    }
}
