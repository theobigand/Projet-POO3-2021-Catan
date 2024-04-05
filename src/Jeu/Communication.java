package Jeu;

import java.util.Scanner;

import Joueur.Ia;
import Joueur.Joueur;
import Plateau.Plateau;

public class Communication {

    Scanner sc;

    public Communication() {
        sc = new Scanner(System.in);
    }

    public String choixPartie() {
        System.out.println("Voulez-vous jouer en mode textuel ou en mode graphique ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", "").strip();
        } while (!rep.equals("textuel") && !rep.equals("graphique"));
        return rep;
    }

    public int demanderNombreJoueurs() {
        System.out.println("Combien de joueurs vont participer ? 3 ou 4 ?");
        String rep;
        do {
            rep = sc.nextLine().replaceAll("\\s+", "").strip();
        } while (!rep.equals("3") && !rep.equals("4"));
        return Integer.parseInt(rep);
    }

    public Joueur demanderJoueurs(int numeroJoueur, Plateau p) {
        System.out.println("Le joueur n° " + (numeroJoueur + 1) + " est-il joueur par un humain ou par l'ia ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", "").strip();
        } while (!rep.equals("humain") && !rep.equals("ia"));
        boolean ia = rep.equals("ia");
        String nom;
        if (!ia) {
            System.out.println("Quel est le nom du joueur ?");
            do {
                rep = sc.nextLine().strip();
            } while (rep.isEmpty());
            nom = rep;
        } else
            nom = "ia" + (numeroJoueur + 1);
        String[] couleurs = { "bleu", "vert", "jaune", "rouge" };
        // System.out.println("Quelle couleurs aura le joueurs ?");
        // do {
        // rep = sc.nextLine().toLowerCase().strip();
        // } while (!rep.equals("bleu") && !rep.equals("vert") && !rep.equals("jaune")
        // && !rep.equals("rouge"));
        if (ia)
            return new Ia(nom, couleurs[numeroJoueur], p);
        return new Joueur(nom, couleurs[numeroJoueur], p);
    }

    public String choixAction(String action) {
        System.out.println(action);
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", "").strip();
        } while (!rep.equals("oui") && !rep.equals("non"));
        return rep;
    }

    public String choixConstruction() {
        System.out.println(
                "Voulez-vous construire une Route, une Colonie ou une Ville ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", "").strip();
        } while (!rep.equals("route") && !rep.equals("colonie") && !rep.equals("ville"));
        return rep;
    }

    public String choixRessource(String action) {
        System.out.println(action);
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", "").strip();
        } while (!rep.equals("argile") && !rep.equals("bois") && !rep.equals("laine") && !rep.equals("ble")
                && !rep.equals("blé") && !rep.equals("minerai") && !rep.equals("stop"));
        return rep;
    }

    public int choixPort(int nbPort) {
        System.out.println("Quel port voulez-vous faire du commerce avec ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
        } while (Integer.parseInt(rep) <= 0 && Integer.parseInt(rep) > nbPort);
        return Integer.parseInt(rep) - 1;
    }

    public String choixPlacementColonie() {
        System.out.println("Où voulez-vous placer votre colonie");
        String rep;
        int i;
        int j;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
            if (rep.length() != 3) {
                i = -1;
                j = -1;
            } else {
                i = Integer.parseInt(rep.substring(0, 1));
                j = Integer.parseInt(rep.substring(2, 3));
            }
            System.out.println(rep);
        } while (i <= 0 || i >= 5 || j <= 0 || j >= 5);
        return rep;
    }

    public String choixLocationDeLaColonie() {
        System.out.println(
                "Dans quelle partie de la case voulez-vous placer votre colonie ?  haut gauche ?  bas gauche ? haut droit ? bas droit ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
        } while (!rep.equals("haut gauche") && !rep.equals("bas gauche") && !rep.equals("bas droit")
                && !rep.equals("haut droit"));
        return rep;
    }

    public String choixCarteDeveloppement(String cartePossible) {
        System.out.println(cartePossible);
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
        } while (!rep.equals("chevalier") && !rep.equals("progres"));
        return rep;
    }

    public String choixCarteProgres() {
        System.out.println("Quel carte progres voulez-vous jouer ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
        } while (!rep.equals("invention") && !rep.equals("monopole") && !rep.equals("route"));
        return rep;
    }

    public int choixColonieUpgrade(int nbColonies) {
        System.out.println("Choisissez une colonie à transformer en ville");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", "").strip();
        } while (Integer.parseInt(rep) <= 0 || Integer.parseInt(rep) > nbColonies);
        return Integer.parseInt(rep) - 1;
    }

    public String choixPlacementRoute() {
        System.out.println("Où voulez-vous placer votre route");
        String rep;
        int i;
        int j;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
            i = Integer.parseInt(rep.substring(0, 1));
            j = Integer.parseInt(rep.substring(2, 3));
        } while (i <= 0 || i >= 5 || j <= 0 || j >= 5);
        return rep;
    }

    public String choixLocationDeLaRoute(String s) {
        System.out.println(s);
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
        } while (!rep.equals("haut") && !rep.equals("droit") && !rep.equals("bas")
                && !rep.equals("gauche"));
        return rep;
    }

    public boolean continuerPlacement(String s) {
        System.out.println("Vous ne pouvez pas placer " + s + " ici. Voulez-vous essayer un autre endroit ?");
        String rep;
        do {
            rep = sc.nextLine().toLowerCase().replaceAll("\\s+", " ").strip();
        } while (!rep.equals("oui") && !rep.equals("non"));
        return rep.equals("oui");
    }

}
