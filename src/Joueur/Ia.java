package Joueur;

import java.util.LinkedList;
import java.util.Random;

import Carte.Developpement.CarteDeveloppement;
import Carte.Developpement.Chevalier;
import Carte.Developpement.Progres.ProgresInvention;
import Carte.Developpement.Progres.ProgresMonopole;
import Carte.Developpement.Progres.ProgresRoute;
import Carte.Ressources.CarteRessources;
import Plateau.Plateau;
import Plateau.Infrastructures.Colonie;
import Plateau.Infrastructures.Port;
import Plateau.Infrastructures.PortSpecialise;
import Plateau.Infrastructures.Route;

public class Ia extends Joueur {

    Random rd;
    static final String[] listRessources = { "argile", "bois", "laine", "ble", "minerai" };

    public Ia(String nom, String couleur, Plateau p) {
        super(nom, couleur, p);
        rd = new Random();
    }

    private void jouerDeveloppement() {
        if (rd.nextInt(2) == 1 && carteDev) {
            if (!deckCarteDeveloppement.isEmpty()) {
                carteDev = false;
                int r = rd.nextInt(3);
                if (r == 1) {
                    for (CarteDeveloppement cd : deckCarteDeveloppement) {
                        if (cd instanceof Chevalier) {
                            ((Chevalier) cd).jouerChevalier();
                            deckCarteDeveloppement.remove(cd);
                            break;
                        }
                    }
                } else if (r == 2) {
                    for (CarteDeveloppement cp : deckCarteDeveloppement) {
                        if (cp instanceof ProgresInvention) {
                            recevoirRessource(listRessources[rd.nextInt(5)], 2);
                            deckCarteDeveloppement.remove(cp);
                            break;
                        } else if (cp instanceof ProgresMonopole) {
                            String ressource = listRessources[rd.nextInt(5)];
                            for (Joueur j : plateau.getListJoueurs()) {
                                if (j != this) {
                                    int nbR = 0;
                                    for (CarteRessources cr : deckCarteRessources) {
                                        if (cr.getNom().equalsIgnoreCase(ressource))
                                            nbR++;
                                    }
                                    j.perdreRessource(ressource, nbR);
                                    recevoirRessource(ressource, nbR);
                                }
                            }
                            deckCarteDeveloppement.remove(cp);
                            break;
                        } else if (cp instanceof ProgresRoute) {
                            for (int i = 0; i < 2; i++) {
                                Route route = new Route(this, plateau);
                                String[] locationRoute = { "haut", "droit", "bas", "gauche" };
                                boolean ok = false;
                                while (!ok) {
                                    int k = rd.nextInt(plateau.getPlateau().length - 2) + 1;
                                    int j = rd.nextInt(plateau.getPlateau()[0].length - 2) + 1;
                                    int nLocation = rd.nextInt(4);
                                    if (route.placementPossible(plateau.getPlateau()[k][j], locationRoute[nLocation])) {
                                        route.placement(plateau.getPlateau()[k][j], locationRoute[nLocation]);
                                        ok = true;
                                    }
                                }
                            }
                            deckCarteDeveloppement.remove(cp);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void commerce() {
        if (rd.nextInt(2) == 1) {
            if (hasSpecialPort()) {
                Port choisi = port.get(rd.nextInt(port.size()));
                if (choisi instanceof PortSpecialise) {
                    perdreRessource(listRessources[rd.nextInt(5)], 2);
                    recevoirRessource(choisi.getRessource(), 1);
                } else {
                    perdreRessource(listRessources[rd.nextInt(5)], 3);
                    recevoirRessource(listRessources[rd.nextInt(5)], 1);
                }
            } else if (hasPort()) {
                perdreRessource(listRessources[rd.nextInt(5)], 3);
                recevoirRessource(listRessources[rd.nextInt(5)], 1);
            } else {
                perdreRessource(listRessources[rd.nextInt(5)], 4);
                recevoirRessource(listRessources[rd.nextInt(5)], 1);
            }
        }
    }

    public void construction() {
        if (rd.nextInt(2) == 1) {
            String[] locationColonie = { "haut gauche", "haut droit", "bas droit", "bas gauche" };
            String[] locationRoute = { "haut", "droit", "bas", "gauche" };
            if (aRessource("ville") && nbVilles != 0) {
                if (!colonie.isEmpty()) {
                    perdreRessource("Minerai", 3);
                    perdreRessource("Ble", 2);
                    colonie.get(rd.nextInt(colonie.size())).upgrade();
                    nbVilles--;
                    nbColonies++;
                }
            } else if (aRessource("colonie") && nbColonies != 0) {
                Colonie col = new Colonie(this, false, plateau);
                boolean ok = false;
                while (!ok) {
                    int i = rd.nextInt(plateau.getPlateau().length - 2) + 1;
                    int j = rd.nextInt(plateau.getPlateau()[0].length - 2) + 1;
                    int nLocation = rd.nextInt(4);
                    if (col.placementPossible(plateau.getPlateau()[i][j], locationColonie[nLocation])) {
                        col.placement(plateau.getPlateau()[i][j], locationColonie[nLocation]);
                        ok = true;
                    }
                }
                super.colonie.add(col);
                nbColonies--;
            } else if (aRessource("route") && nbRoutes != 0) {
                Route route = new Route(this, plateau);
                boolean ok = false;
                while (!ok) {
                    int i = rd.nextInt(plateau.getPlateau().length - 2) + 1;
                    int j = rd.nextInt(plateau.getPlateau()[0].length - 2) + 1;
                    int nLocation = rd.nextInt(4);
                    if (route.placementPossible(plateau.getPlateau()[i][j], locationRoute[nLocation])) {
                        route.placement(plateau.getPlateau()[i][j], locationRoute[nLocation]);
                        ok = true;
                    }
                }
                nbRoutes--;
            }
        }
    }

    @Override
    public void actionEffectuer() {
        carteDev = true;
        jouerDeveloppement();

        int resultatDe = LancerDe();
        plateau.repartirRessource(resultatDe);

        jouerDeveloppement();

        commerce();

        jouerDeveloppement();

        if (rd.nextInt(2) == 1)
            achatCarteDeveloppement();

        construction();

        jouerDeveloppement();
    }

    @Override
    public void JouerChevalier() {
        nbChevalierJouer++;
        plateau.getVoleur().placerIa(rd);
        Joueur j;
        int x;
        do {
            j = plateau.getListJoueurs().get(rd.nextInt(plateau.getListJoueurs().size()));
            x = rd.nextInt(5);
        } while (j == this || !j.aRessource(listRessources[x]));
        j.perdreRessource(listRessources[x], x);
        recevoirRessource(listRessources[x], x);
    }

}
