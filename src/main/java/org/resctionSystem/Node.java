package org.resctionSystem;

import java.util.List;

public class Node {
    private String nom;
    private List<Node> succeseur;
    private List<Node> predecesseur;

    public Node(String nom) {
        this.nom = nom;
    }
}
