package Carte.Speciale;

import Carte.Carte;
import Joueur.Joueur;

public class CarteSpeciale extends Carte {
    private String nom;

    public CarteSpeciale(String nom, Joueur j) {
        super(j);
        this.nom = nom;
    }

    public void affiche() {
        System.out.println("Carte spéciale :" + this.nom);
    }
}
