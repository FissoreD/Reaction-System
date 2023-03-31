package org.reactionSystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {
    private static int idStatic = 0;

    private final int id;
    private final Set<String> activators;
    private final Set<String> inhibitors;
    private final Set<String> result;

    public Rule(String[] activators, String[] inhibitors, String[] result) {
        this.id = Rule.idStatic++;
        this.activators = new HashSet<>(List.of(activators));
        this.inhibitors = new HashSet<>(List.of(inhibitors));
        this.result = new HashSet<>(List.of(result));
    }

    public Rule() {
        this(new String[] {}, new String[] {}, new String[] {});
    }

    /**
     * 
     * @param name the name of the molecule to add to the activators
     *             add the molecule to the activators of the rule
     */
    public void addActivator(String... name) {
        this.activators.addAll(Arrays.asList(name));
    }

    /**
     * 
     * @param name the name of the molecule to add to the inhibitors
     *             add the molecule to the inhibitors of the rule
     */
    public void addInhibitor(String... name) {
        this.inhibitors.addAll(Arrays.asList(name));
    }

    /**
     * 
     * @param name the name of the molecule to add to the result
     *             add the molecule to the result of the rule
     */
    public void addResult(String... name) {
        this.result.addAll(Arrays.asList(name));
    }

    /**
     * 
     * @return the activators of the rule
     */
    public Set<String> getActivators() {
        return activators;
    }

    /**
     * 
     * @return the inhibitors of the rule
     */
    public Set<String> getInhibitors() {
        return inhibitors;
    }

    /**
     * 
     * @return the result of the rule
     */
    public Set<String> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "\nRule%d{activators=" + activators + ", inhibitors=" + inhibitors + ", result=" + result + "}", id);
    }
}
