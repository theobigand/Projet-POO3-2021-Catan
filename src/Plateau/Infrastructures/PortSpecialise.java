package Plateau.Infrastructures;

import Plateau.Composants.Case;

public class PortSpecialise extends Port {

    private final String ressource;

    public PortSpecialise(Case emplacement, String ressource) {
        super(emplacement);
        this.ressource = ressource;
    }

    @Override
    public String getRessource() {
        return ressource;
    }

    public String toString() {
        return "Port spécialisé dans: " + ressource + ".";
    }
}