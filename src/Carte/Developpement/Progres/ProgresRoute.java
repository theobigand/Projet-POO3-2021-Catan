package Carte.Developpement.Progres;

import Joueur.Joueur;
import Plateau.Infrastructures.Route;

public class ProgresRoute extends Progres {
    public ProgresRoute(Joueur j) {
        super("Construction de route", j);
        // TODO Auto-generated constructor stub
    }

    public void construireRoute() {
        for (int i = 0; i < 2; i++) {
            Route r = new Route(super.joueur, super.joueur.getPlateau());
            r.placer();
        }
    }

}
