package org.resctionSystem;

import java.util.List;

public class System {
    private List<Rule> rules;
    private List<String> elements;

    public System(List<Rule> rules, List<String> elements) {
        this.rules = rules;
        this.elements = elements;
    }
}
