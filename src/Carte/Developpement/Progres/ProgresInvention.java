package Carte.Developpement.Progres;

import Jeu.Communication;
import Joueur.Joueur;

public class ProgresInvention extends Progres {
    public ProgresInvention(Joueur j) {
        super("Invention", j);
        // TODO Auto-generated constructor stub
    }

    public void pioche() {
        Communication c = new Communication();
        String ressource = c.choixRessource("Quel ressource voulez-vous obtenir ?");
        joueur.recevoirRessource(ressource, 2);
    }
}
