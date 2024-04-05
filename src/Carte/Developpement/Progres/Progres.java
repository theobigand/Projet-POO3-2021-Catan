package Carte.Developpement.Progres;

import Carte.Developpement.CarteDeveloppement;
import Joueur.Joueur;

public class Progres extends CarteDeveloppement {

    private String nom;

    public Progres(String nom, Joueur j) {
        super(nom, j);
        this.nom = nom;
    }

    public String toString() {
        return ("Carte Progrès :" + this.nom);
    }
}