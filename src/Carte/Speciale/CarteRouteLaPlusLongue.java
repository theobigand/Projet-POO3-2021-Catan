package Carte.Speciale;

import Joueur.Joueur;

public class CarteRouteLaPlusLongue extends CarteSpeciale {

    public CarteRouteLaPlusLongue(String nom, Joueur j) {
        super(nom, j);
        // TODO Auto-generated constructor stub
    }

    public void setJoueur(Joueur j) {
        super.joueur = j;
    }

    public Joueur getJoueur() {
        return super.joueur;
    }

}
