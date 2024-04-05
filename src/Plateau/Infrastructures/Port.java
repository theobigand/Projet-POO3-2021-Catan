package Plateau.Infrastructures;

import Joueur.Joueur;
import Plateau.Composants.Case;

public class Port {
    private final Case emplacement;
    private Joueur joueur;

    public Port(Case emplacement) {
        this.emplacement = emplacement;
        joueur = null;
    }

    public void assignerJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public String toString() {
        return "Port normal.";
    }

    public String getRessource() {
        return "";
    }
}
