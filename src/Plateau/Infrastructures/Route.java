package Plateau.Infrastructures;

import java.util.HashMap;
import java.util.Random;

import Jeu.Communication;
import Joueur.Joueur;
import Plateau.Plateau;
import Plateau.Composants.Case;

public class Route {

    private Joueur joueur;
    // private LinkedList<Route> routeLiee; utile pour faire la carte de la route la
    // plus longue
    private Plateau plateau;
    private Communication c;

    public Route(Joueur joueur, Plateau p) {
        this.joueur = joueur;
        // routeLiee = new LinkedList<>();
        plateau = p;
        c = new Communication();
    }

    public void placer() {
        String caseLocation = c.choixPlacementRoute();
        String[] locationDecoupe = caseLocation.split(" ");
        int i = Integer.parseInt(locationDecoupe[0]);
        int j = Integer.parseInt(locationDecoupe[1]);
        Case caseChoisi = plateau.getPlateau()[i][j];
        String location = c.choixLocationDeLaRoute(
                "Dans quelle partie de la case voulez-vous placer votre route ?  haut ?  droit ? bas ? gauche ?");
        boolean placementOk = false;
        int essai = 0;
        while (!placementOk) {
            if (essai != 0) {
                caseLocation = c.choixPlacementRoute();
                locationDecoupe = caseLocation.split(" ");
                i = Integer.parseInt(locationDecoupe[0]);
                j = Integer.parseInt(locationDecoupe[1]);
                caseChoisi = plateau.getPlateau()[i][j];
                location = c.choixLocationDeLaRoute(
                        "Dans quelle partie de la case voulez-vous placer votre route ?  haut ?  droit ? bas ? gauche ?");
            }
            if (placementPossible(caseChoisi, location)) {
                placement(caseChoisi, location);
                placementOk = true;
            } else {
                placementOk = !c.continuerPlacement("de route");
                essai++;
            }
        }
    }

    public void placement(Case caseChoisi, String location) {
        caseChoisi.setRoute(location, this);
        HashMap<String, Case> caseAdja;
        switch (location) {
            case "bas":
                caseAdja = caseChoisi.getMap();
                for (var e : caseAdja.entrySet()) {
                    if (e.getKey().equals("bas"))
                        e.getValue().setRoute("haut", this);
                }
                break;
            case "haut":
                caseAdja = caseChoisi.getMap();
                for (var e : caseAdja.entrySet()) {
                    if (e.getKey().equals("haut"))
                        e.getValue().setRoute("bas", this);
                }
                break;
            case "gauche":
                caseAdja = caseChoisi.getMap();
                for (var e : caseAdja.entrySet()) {
                    if (e.getKey().equals("gauche"))
                        e.getValue().setRoute("droit", this);
                }
                break;
            case "droit":
                caseAdja = caseChoisi.getMap();
                for (var e : caseAdja.entrySet()) {
                    if (e.getKey().equals("droit"))
                        e.getValue().setRoute("gauche", this);
                }
                break;
        }
    }

    public boolean placementPossible(Case caseChoisi, String location) {
        boolean ok = false;
        HashMap<String, Colonie> MapColonie;
        HashMap<String, Case> caseAdja;
        if (caseChoisi.getRoute(location) == null) {
            switch (location) {
                case "bas":
                    if ((caseChoisi.getRoute("gauche") != null && caseChoisi.getRoute("gauche").joueur == joueur)
                            || (caseChoisi
                                    .getRoute("droit") != null && caseChoisi.getRoute("droit").joueur == joueur)) {
                        ok = true;
                    }
                    MapColonie = caseChoisi.getMapColonie();
                    for (var i : MapColonie.entrySet()) {
                        if (i.getKey().split(" ")[0].equals("bas") && i.getValue().getJoueur() == joueur) {
                            ok = true;
                        }
                    }
                    caseAdja = caseChoisi.getMap();
                    for (var i : caseAdja.entrySet()) {
                        if (i.getKey().length() == 2 && i.getKey().split(" ")[0].equals("bas")) {
                            Case tmp = i.getValue();
                            if (tmp.getRoute("haut") != null && tmp.getRoute("haut").joueur == joueur)
                                ok = true;
                            if (i.getKey().split(" ")[1].equals("droit")) {
                                if (tmp.getRoute("gauche") != null && tmp.getRoute("gauche").joueur == joueur)
                                    ok = true;
                            } else {
                                if (tmp.getRoute("droit") != null && tmp.getRoute("droit").joueur == joueur)
                                    ok = true;
                            }
                        }
                    }

                    break;
                case "haut":
                    if ((caseChoisi.getRoute("gauche") != null && caseChoisi.getRoute("gauche").joueur == joueur)
                            || (caseChoisi
                                    .getRoute("droit") != null && caseChoisi.getRoute("droit").joueur == joueur)) {
                        ok = true;
                    }
                    MapColonie = caseChoisi.getMapColonie();
                    for (var i : MapColonie.entrySet()) {
                        if (i.getKey().split(" ")[0].equals("haut") && i.getValue().getJoueur() == joueur) {
                            ok = true;
                        }
                    }
                    caseAdja = caseChoisi.getMap();
                    for (var i : caseAdja.entrySet()) {
                        if (i.getKey().length() == 2 && i.getKey().split(" ")[0].equals("haut")) {
                            Case tmp = i.getValue();
                            if (tmp.getRoute("bas") != null && tmp.getRoute("bas").joueur == joueur)
                                ok = true;
                            if (i.getKey().split(" ")[1].equals("droit")) {
                                if (tmp.getRoute("gauche") != null && tmp.getRoute("gauche").joueur == joueur)
                                    ok = true;
                            } else {
                                if (tmp.getRoute("droit") != null && tmp.getRoute("droit").joueur == joueur)
                                    ok = true;
                            }
                        }
                    }
                    break;
                case "gauche":
                    if ((caseChoisi.getRoute("haut") != null && caseChoisi.getRoute("haut").joueur == joueur)
                            || (caseChoisi
                                    .getRoute("bas") != null && caseChoisi.getRoute("bas").joueur == joueur)) {
                        ok = true;
                    }
                    MapColonie = caseChoisi.getMapColonie();
                    for (var i : MapColonie.entrySet()) {
                        if (i.getKey().split(" ")[1].equals("gauche") && i.getValue().getJoueur() == joueur) {
                            ok = true;
                        }
                    }
                    caseAdja = caseChoisi.getMap();
                    for (var i : caseAdja.entrySet()) {
                        if (i.getKey().length() == 2 && i.getKey().split(" ")[1].equals("gauche")) {
                            Case tmp = i.getValue();
                            if (tmp.getRoute("droit") != null && tmp.getRoute("droit").joueur == joueur)
                                ok = true;
                            if (i.getKey().split(" ")[0].equals("haut")) {
                                if (tmp.getRoute("bas") != null && tmp.getRoute("bas").joueur == joueur)
                                    ok = true;
                            } else {
                                if (tmp.getRoute("haut") != null && tmp.getRoute("haut").joueur == joueur)
                                    ok = true;
                            }
                        }
                    }
                    break;
                case "droit":
                    if ((caseChoisi.getRoute("haut") != null && caseChoisi.getRoute("haut").joueur == joueur)
                            || (caseChoisi
                                    .getRoute("bas") != null && caseChoisi.getRoute("bas").joueur == joueur)) {
                        ok = true;
                    }
                    MapColonie = caseChoisi.getMapColonie();
                    for (var i : MapColonie.entrySet()) {
                        if (i.getKey().split(" ")[1].equals("droit") && i.getValue().getJoueur() == joueur) {
                            ok = true;
                        }
                    }
                    caseAdja = caseChoisi.getMap();
                    for (var i : caseAdja.entrySet()) {
                        if (i.getKey().length() == 2 && i.getKey().split(" ")[1].equals("droit")) {
                            Case tmp = i.getValue();
                            if (tmp.getRoute("gauche") != null && tmp.getRoute("gauche").joueur == joueur)
                                ok = true;
                            if (i.getKey().split(" ")[0].equals("haut")) {
                                if (tmp.getRoute("bas") != null && tmp.getRoute("bas").joueur == joueur)
                                    ok = true;
                            } else {
                                if (tmp.getRoute("haut") != null && tmp.getRoute("haut").joueur == joueur)
                                    ok = true;
                            }
                        }
                    }
                    break;

            }
        }
        return ok;
    }

    public void placerPremierTours(Colonie colonie, boolean ia) {
        String placementRoute = "";
        if (!ia)
            placementRoute = c.choixLocationDeLaRoute(
                    "Par rapport à la colonie que vous venez de placer, où voulez-vous placer votre route ? haut ? droit ? bas ? gauche ?");
        else {
            Random rd = new Random();
            String[] locationRoute = { "haut", "droit", "bas", "gauche" };
            placementRoute = locationRoute[rd.nextInt(4)];
            boolean ok = false;
        }

        HashMap<String, Case> mapCaseAdja = colonie.getCaseAdja();
        Case adja;
        switch (placementRoute) {
            case "haut":
                for (var i : mapCaseAdja.entrySet()) {
                    if (i.getKey().split(" ")[0].equals("haut")) {
                        adja = i.getValue();
                        if (i.getKey().split(" ")[1].equals("gauche"))
                            adja.setRoute("droit", this);
                        else
                            adja.setRoute("gauche", this);
                    }
                }
                break;
            case "droit":
                for (var i : mapCaseAdja.entrySet()) {
                    if (i.getKey().split(" ")[1].equals("droit")) {
                        adja = i.getValue();
                        if (i.getKey().split(" ")[0].equals("haut"))
                            adja.setRoute("bas", this);
                        else
                            adja.setRoute("haut", this);
                    }
                }
                break;
            case "bas":
                for (var i : mapCaseAdja.entrySet()) {
                    if (i.getKey().split(" ")[0].equals("bas")) {
                        adja = i.getValue();
                        if (i.getKey().split(" ")[1].equals("gauche"))
                            adja.setRoute("droit", this);
                        else
                            adja.setRoute("gauche", this);
                    }
                }
                break;
            case "gauche":
                for (var i : mapCaseAdja.entrySet()) {
                    if (i.getKey().split(" ")[1].equals("gauche")) {
                        adja = i.getValue();
                        if (i.getKey().split(" ")[0].equals("haut"))
                            adja.setRoute("bas", this);
                        else
                            adja.setRoute("haut", this);
                    }
                }
                break;
        }
    }

    public Joueur getJoueur() {
        return joueur;
    }

}
