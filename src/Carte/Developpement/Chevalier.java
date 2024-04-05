package Carte.Developpement;

import Joueur.Joueur;

public class Chevalier extends CarteDeveloppement {
    public Chevalier(Joueur j) {
        super("Chevalier", j);
    }

    public void jouerChevalier() {
        joueur.JouerChevalier();
    }
}