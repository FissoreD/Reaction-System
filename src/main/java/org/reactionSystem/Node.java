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

    /**
     * 
     * @param node the node to add to the successors
     *             add the node to the successors of the current node
     *             and add the current node to the predecessors of the node
     */
    public void addSuccessor(Node node) {
        this.successors.put(node.name, node);
        node.predecessors.put(this.name, this);
    }

    /**
     * 
     * @param node the node to add to the predecessors
     *             add the node to the predecessors of the current node
     */
    public void addPredecessor(Node node) {
        node.addSuccessor(this);
    }

    /**
     * 
     * @param node the node to remove from the successors
     *             remove the node from the successors of the current node
     */
    public void removeSuccessor(Node node) {
        this.successors.remove(node.name);
        node.predecessors.remove(this.name, this);
    }

    /**
     * 
     * @param node the node to remove from the predecessors
     *             remove the node from the predecessors of the current node
     */
    public void removePredecessor(Node node) {
        node.removePredecessor(this);
    }

    /**
     * 
     * @return the name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param molecules the set of molecules to check
     * @return true if the node has the same molecules, false otherwise
     */
    public boolean sameMolecules(Set<String> molecules) {
        return molecules.equals(this.molecules);
    }

    /**
     * 
     * @return the set of the molecules of the node
     */
    public Set<String> getMolecules() {
        return molecules;
    }

    /**
     * 
     * @param moleculeName the name of the molecule to check
     * @return true if the node has the molecule, false otherwise
     */
    public boolean hasMolecule(Set<String> moleculeName) {
        return this.molecules.containsAll(moleculeName);
    }

    /**
     * 
     * @param moleculesName the name of the molecules to check
     * @return true if the node has not the molecules, false otherwise
     */
    public boolean hasNotMolecule(Set<String> moleculesName) {
        return moleculesName.stream().noneMatch(molecules::contains);
    }

    /**
     * 
     * @return the map of the successors of the node
     */
    public Map<String, Node> getSuccessors() {
        return successors;
    }

    /**
     * 
     * @return the map of the predecessors of the node
     */
    public Map<String, Node> getPredecessors() {
        return predecessors;
    }

    @Override
    public String toString() {
        // var res = new StringBuffer();
        // res.append("succ:{");
        // successors.forEach((succName, _ignore) -> res.append(succName).append(";"));
        // res.append("} -- pred:{");
        // predecessors.forEach((predName, _ignore) ->
        // res.append(predName).append(";"));
        // res.append("}");
        // return res.toString();
        return this.name.equals("") ? "nil" : this.name;
    }
}
