package Carte.Speciale;

import Joueur.Joueur;

public class CarteChevalierLePlusPuissant extends CarteSpeciale {

    public CarteChevalierLePlusPuissant(String nom, Joueur j) {
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
