package org.reactionSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReactionSystem {
    private final Set<Rule> rules;
    private final Set<String> molecules;
    private final Graph graph;

    public ReactionSystem() {
        this.rules = new HashSet<>();
        this.graph = new Graph();
        this.molecules = new HashSet<>();
    }

    protected Graph getGraph() {
        return graph;
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
        molecules.addAll(rule.getActivators());
        molecules.addAll(rule.getInhibitors());
        molecules.addAll(rule.getResult());
    }

    public void buildGraph() {
        List<String> l = this.molecules.stream().toList();
        var maxNumberOfSubSets = 1 << l.size();
        for (int i = 0; i < maxNumberOfSubSets; i++) {
            var names = new ArrayList<String>();
            for (int j = 0; j < maxNumberOfSubSets; j++)
                if ((i & (1 << j)) > 0)
                    names.add(l.get(j));
            graph.addNode(names);
        }
        rules.forEach(rule -> {
            var successor = graph.getNodeByName(rule.getResult());
            graph.getNodes(rule.getActivators(), rule.getInhibitors()).forEach(curr -> {
                if (curr.getSuccessors().size() == 0)
                    curr.addSuccessor(successor);
                else {
                    var oldSuccessor = curr.getSuccessors().values().stream().findFirst().get();
                    curr.removeSuccessor(oldSuccessor);
                    var molecules = new HashSet<>(oldSuccessor.getMolecules());
                    molecules.addAll(successor.getMolecules());
                    curr.addSuccessor(graph.getNodeByName(molecules));
                }
            });
        });
        graph.addEdgeToNil();
    }

    @Override
    public String toString() {
        return "Model{\nrules=" + rules + ", \ngraph=\n" + graph + '}';
    }
}
