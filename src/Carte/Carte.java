package Carte;

import Joueur.Joueur;

public abstract class Carte {
    protected Joueur joueur;

    public Carte(Joueur joueur) {
        this.joueur = joueur;
    }
}
