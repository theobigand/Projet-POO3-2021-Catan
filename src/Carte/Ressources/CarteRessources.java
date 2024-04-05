package Carte.Ressources;

import Carte.Carte;
import Joueur.Joueur;

public class CarteRessources extends Carte {
    private String nom;

    public CarteRessources(String nom, Joueur j) {
        super(j);
        this.nom = nom;
    }

    public String toString() {
        return ("CarteRessources " + this.nom);
    }

    public String getNom() {
        return this.nom;
    }

}
