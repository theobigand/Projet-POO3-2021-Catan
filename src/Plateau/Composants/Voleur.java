package Plateau.Composants;

import Plateau.Plateau;
import java.util.Random;
import java.util.Scanner;

public class Voleur {
	Plateau p;
	private Case emplacement;

	public Voleur(Case c, Plateau p) {
		this.emplacement = c;
		this.p = p;
	}

	public Case getEmplacement() {
		return this.emplacement;
	}

	public String[] decoupe(String str) {
		String[] tab = str.split(" ");
		return tab;
	}

	public void placer() {
		emplacement.setVoleur(false);
		Scanner sc = new Scanner(System.in);
		System.out.println("Donnez les coordonnées de la case  où vous voulez placer le voleur");
		String CaseLocation = sc.nextLine();
		String[] tab = decoupe(CaseLocation);
		int i = Integer.parseInt(tab[0]);
		int j = Integer.parseInt(tab[1]);
		Case c = p.getPlateau()[i][j];
		this.emplacement = c;
		c.setVoleur(true);
	}

	public void placerIa(Random rd) {
		emplacement.setVoleur(false);
		Case c = p.getPlateau()[rd.nextInt(p.getPlateau().length - 2) + 1][rd.nextInt(p.getPlateau()[0].length - 2)
				+ 1];
		emplacement = c;
		c.setVoleur(true);
	}

}
