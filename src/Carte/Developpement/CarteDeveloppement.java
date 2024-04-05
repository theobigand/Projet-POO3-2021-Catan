package Carte.Developpement;

import Carte.Carte;
import Joueur.Joueur;

public class CarteDeveloppement extends Carte {
    private String nom;

    public CarteDeveloppement(String nom, Joueur j) {
        super(j);
        this.nom = nom;
    }

    public String toString() {
        return ("Carte developpement:" + this.nom);
    }

    public void setJoueur(Joueur j) {
        joueur = j;
    }

}
