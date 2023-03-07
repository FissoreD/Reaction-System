package org.resctionSystem;

import java.util.List;

public class Rule {
    private List<String> activateur;
    private List<String> inibiteur;
    private List<String> resultat;

    public Rule(List<String> activateur, List<String> inibiteur, List<String> resultat) {
        this.activateur = activateur;
        this.inibiteur = inibiteur;
        this.resultat = resultat;
    }


}
