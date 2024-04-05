package Jeu;

import java.util.HashMap;

import Plateau.Plateau;
import Plateau.Infrastructures.Colonie;

public class AffichageText {
    private Plateau p;

    public AffichageText(Plateau p) {
        this.p = p;
    }

    public void affiche() {
        String[] s = { "@", "#", "•", "*" };
        StringBuilder sb = new StringBuilder();
        sb.append("\n" + " ".repeat(21) + "1" + " ".repeat(11) + "2" + " ".repeat(11) + "3"
                + " ".repeat(11) + "4\n");
        sb.append("   " + "|-----------".repeat(6) + "|" + " ".repeat(23) + "Lexique\n");
        for (int i = 0; i < p.getPlateau().length; i++) {
            for (int k = 0; k < 7; k++) {
                for (int j = 0; j < p.getPlateau()[i].length; j++) {
                    HashMap<String, Colonie> mapColo = p.getPlateau()[i][j].getMapColonie();
                    switch (k) {
                        case 0:
                            if (mapColo.get("haut gauche") != null)
                                sb.append("   |" + s[mapColo.get("haut gauche").getJoueur().getChiffreCouleur()] + " ");
                            else
                                sb.append("   |  ");
                            if (p.getPlateau()[i][j].getRoute("haut") != null)
                                sb.append(s[p.getPlateau()[i][j].getRoute("haut").getJoueur().getChiffreCouleur()]
                                        .repeat(3) + " ");
                            else
                                sb.append("    ");
                            if (mapColo.get("haut droit") != null)
                                sb.append(s[mapColo.get("haut droit").getJoueur().getChiffreCouleur()]);
                            else
                                sb.append("  ");
                            if (j == p.getPlateau()[i].length - 1)
                                sb.append("   |\n");
                            break;
                        case 1:
                        case 5:
                            sb.append("   |");
                            sb.append(" ".repeat(8));
                            if (j == p.getPlateau()[i].length - 1)
                                sb.append("   |\n");
                            break;
                        case 2:
                            if (p.getPlateau()[i][j].getRoute("gauche") != null)
                                sb.append(
                                        "   |" + s[p.getPlateau()[i][j].getRoute("gauche").getJoueur()
                                                .getChiffreCouleur()]
                                                + " ");
                            else
                                sb.append("   |  ");
                            sb.append(p.getPlateau()[i][j].getEnvironement().substring(0, 3) + " ");
                            if (p.getPlateau()[i][j].getRoute("droit") != null)
                                sb.append(s[p.getPlateau()[i][j].getRoute("droit").getJoueur().getChiffreCouleur()]);
                            else
                                sb.append("  ");
                            if (j == p.getPlateau()[i].length - 1)
                                sb.append("   |\n");
                            break;
                        case 3:
                            if (p.getPlateau()[i][j].getRoute("gauche") != null) {
                                if (i != 0 && i != 5 && j == 0)
                                    sb.append(
                                            " " + i + " |"
                                                    + s[p.getPlateau()[i][j].getRoute("gauche").getJoueur()
                                                            .getChiffreCouleur()]
                                                    + " ");
                                else {
                                    sb.append(
                                            "   |"
                                                    + s[p.getPlateau()[i][j].getRoute("gauche").getJoueur()
                                                            .getChiffreCouleur()]
                                                    + " ");
                                }
                            } else if (i != 0 && i != 5 && j == 0)
                                sb.append(" " + i + " |  ");
                            else
                                sb.append("   |  ");
                            if (p.getPlateau()[i][j].getRessource().length() < 3)
                                sb.append("    ");
                            else
                                sb.append(p.getPlateau()[i][j].getRessource().substring(0, 3) + " ");
                            if (p.getPlateau()[i][j].getRoute("droit") != null)
                                sb.append(s[p.getPlateau()[i][j].getRoute("droit").getJoueur().getChiffreCouleur()]);
                            else
                                sb.append("  ");
                            if (j == p.getPlateau()[i].length - 1) {
                                switch (i) {
                                    case 0:
                                        sb.append(
                                                "   |" + " ".repeat(10) + "Nom" + " ".repeat(9) + "Couleur"
                                                        + " ".repeat(7)
                                                        + "Symbole\n");
                                        break;
                                    case 1:
                                        sb.append("   |" + " ".repeat(10) + p.getListJoueurs().get(0).getNom()
                                                + " ".repeat(10) + p
                                                        .getListJoueurs().get(0).getCouleur()
                                                + " ".repeat(11)
                                                + s[p.getListJoueurs().get(0).getChiffreCouleur()] + "\n");
                                        break;
                                    case 2:
                                        sb.append("   |" + " ".repeat(10) + p.getListJoueurs().get(1).getNom()
                                                + " ".repeat(10) + p
                                                        .getListJoueurs().get(1).getCouleur()
                                                + " ".repeat(11)
                                                + s[p.getListJoueurs().get(1).getChiffreCouleur()] + "\n");
                                        break;
                                    case 3:
                                        sb.append("   |" + " ".repeat(10) + p.getListJoueurs().get(2).getNom()
                                                + " ".repeat(10) + p
                                                        .getListJoueurs().get(2).getCouleur()
                                                + " ".repeat(11)
                                                + s[p.getListJoueurs().get(2).getChiffreCouleur()] + "\n");
                                        break;
                                    case 4:
                                        if (p.getListJoueurs().size() == 4)
                                            sb.append("   |" + " ".repeat(10) + p.getListJoueurs().get(3).getNom()
                                                    + " ".repeat(10)
                                                    + p
                                                            .getListJoueurs().get(3).getCouleur()
                                                    + " ".repeat(11)
                                                    + s[p.getListJoueurs().get(3).getChiffreCouleur()] + "\n");
                                        else
                                            sb.append("   |\n");
                                        break;
                                    case 5:
                                        sb.append("   |" + " ".repeat(8) + "1 symbole = colonie" + " ".repeat(8)
                                                + "3 symbole = route\n");
                                        break;
                                }

                            }
                            break;
                        case 4:
                            if (p.getPlateau()[i][j].getRoute("gauche") != null)
                                sb.append(
                                        "   |" + s[p.getPlateau()[i][j].getRoute("gauche").getJoueur()
                                                .getChiffreCouleur()]
                                                + " ");
                            else
                                sb.append("   |  ");
                            String num = Integer.toString(p.getPlateau()[i][j].getNumero());
                            if (num.length() == 1)
                                sb.append(" " + p.getPlateau()[i][j].getNumero() + "  ");
                            else
                                sb.append(p.getPlateau()[i][j].getNumero() + "  ");
                            if (p.getPlateau()[i][j].getRoute("droit") != null)
                                sb.append(s[p.getPlateau()[i][j].getRoute("droit").getJoueur().getChiffreCouleur()]);
                            else
                                sb.append("  ");
                            if (j == p.getPlateau()[i].length - 1)
                                sb.append("   |\n");
                            break;
                        case 6:
                            if (mapColo.get("bas gauche") != null)
                                sb.append("   |" + s[mapColo.get("bas gauche").getJoueur().getChiffreCouleur()] + " ");
                            else
                                sb.append("   |  ");
                            if (p.getPlateau()[i][j].getRoute("bas") != null)
                                sb.append(s[p.getPlateau()[i][j].getRoute("bas").getJoueur().getChiffreCouleur()]
                                        .repeat(3) + " ");
                            else
                                sb.append("    ");
                            if (mapColo.get("bas droit") != null)
                                sb.append(s[mapColo.get("bas droit").getJoueur().getChiffreCouleur()]);
                            else
                                sb.append("  ");
                            if (j == p.getPlateau()[i].length - 1)
                                sb.append("   |\n");
                            break;
                    }
                }
            }
            sb.append("   " + "|-----------".repeat(6) + "|\n");
        }
        System.out.println(sb.toString());
    }
}
