import Carte.*;
import Jeu.*;
import Plateau.*;
import Joueur.*;

public class Launcher {
    public static void main(String[] args) {

        Communication c = new Communication();
        if (c.choixPartie().equals("textuel"))
            jeuText();
        else
            jeuGraph();

    }

    static void jeuText() {
        Plateau p = new Plateau();
        AffichageText a = new AffichageText(p);
        a.affiche();
        p.deuxPremiersTour(a);
        while (!p.getPartiFini()) {
            a.affiche();
            p.tour();
        }
        System.out.println(
                "Bravo à " + p.getListJoueurs().get(p.getNumeroJoueur()).getNom() + " !! Qui à gagner la partie avec "
                        + p
                                .getListJoueurs().get(p.getNumeroJoueur()).getPoints()
                        + " points.");
    }

    static void jeuGraph() {
    }
}
