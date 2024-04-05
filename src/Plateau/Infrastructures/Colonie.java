package Plateau.Infrastructures;

import Joueur.Joueur;
import Plateau.Plateau;
import Plateau.Composants.Case;

import java.util.HashMap;
import java.util.Random;

import Jeu.Communication;

public class Colonie {
	private Joueur joueur;
	private Plateau plateau;
	private int nbrRessource;
	private HashMap<String, Case> case_adja;
	private boolean isVille;
	private Communication c;

	public Colonie(Joueur j, boolean isVille, Plateau p) {
		this.joueur = j;
		this.isVille = false;
		this.case_adja = null;
		this.plateau = p;
		this.nbrRessource = 1;
		c = new Communication();
		case_adja = new HashMap<>();
	}

	public void upgrade() {
		this.isVille = true;
		this.nbrRessource = 2;
	}

	public String[] decoupe(String str) {
		String[] tab = str.split(" ");
		return tab;
	}

	public void placer() {
		String CaseLocation = c.choixPlacementColonie();
		String[] tab = decoupe(CaseLocation);
		int i = Integer.parseInt(tab[0]);
		int j = Integer.parseInt(tab[1]);
		Case caseChoisi = plateau.getPlateau()[i][j];
		String location = c.choixLocationDeLaColonie();
		boolean placementOk = false;
		int essai = 0;
		while (!placementOk) {
			if (essai != 0) {
				CaseLocation = c.choixPlacementColonie();
				tab = decoupe(CaseLocation);
				i = Integer.parseInt(tab[0]);
				j = Integer.parseInt(tab[1]);
				caseChoisi = plateau.getPlateau()[i][j];
				location = c.choixLocationDeLaColonie();
			}
			if (placementPossible(caseChoisi, location)) {
				placement(caseChoisi, location);
				placementOk = true;
			} else {
				placementOk = !c.continuerPlacement("de colonie");
				essai++;
			}
		}
	}

	public boolean placementPossible(Case caseChoisi, String location) {
		boolean ok = false;
		HashMap<String, Colonie> MapColonie = caseChoisi.getMapColonie();
		HashMap<String, Case> caseAdja = caseChoisi.getMap();
		Case adja = caseAdja.get(location);
		HashMap<String, Colonie> mapColonieAdja = adja.getMapColonie();
		if (MapColonie.get(location) == null) {
			switch (location) {
				case "bas gauche":
					if ((caseChoisi.getRoute("gauche") != null
							&& caseChoisi.getRoute("gauche").getJoueur() == joueur)
							|| (caseChoisi.getRoute("bas") != null && caseChoisi.getRoute("bas").getJoueur() == joueur)
							|| (adja.getRoute("haut") != null
									&& adja.getRoute("haut").getJoueur() == joueur)
							|| (adja.getRoute("droit") != null && adja.getRoute("droit").getJoueur() == joueur)) {
						ok = (MapColonie.get("haut gauche") == null && MapColonie.get("bas droit") == null
								&& mapColonieAdja.get("haut gauche") == null
								&& mapColonieAdja.get("bas droit") == null);
					}
					break;
				case "haut gauche":
					if ((caseChoisi.getRoute("gauche") != null
							&& caseChoisi.getRoute("gauche").getJoueur() == joueur)
							|| (caseChoisi.getRoute("haut") != null
									&& caseChoisi.getRoute("haut").getJoueur() == joueur)
							|| (adja.getRoute("bas") != null
									&& adja.getRoute("bas").getJoueur() == joueur)
							|| (adja.getRoute("droit") != null && adja.getRoute("droit").getJoueur() == joueur)) {
						ok = (MapColonie.get("bas gauche") == null && MapColonie.get("haut droit") == null
								&& mapColonieAdja.get("bas gauche") == null
								&& mapColonieAdja.get("haut droit") == null);
					}
					break;
				case "bas droit":
					if ((caseChoisi.getRoute("droit") != null
							&& caseChoisi.getRoute("droit").getJoueur() == joueur)
							|| (caseChoisi.getRoute("bas") != null && caseChoisi.getRoute("bas").getJoueur() == joueur)
							|| (adja.getRoute("haut") != null
									&& adja.getRoute("haut").getJoueur() == joueur)
							|| (adja.getRoute("gauche") != null && adja.getRoute("gauche").getJoueur() == joueur)) {
						ok = (MapColonie.get("haut droit") == null && MapColonie.get("bas gauche") == null
								&& mapColonieAdja.get("haut droit") == null
								&& mapColonieAdja.get("bas gauche") == null);
					}
					break;
				case "haut droit":
					if ((caseChoisi.getRoute("droit") != null
							&& caseChoisi.getRoute("droit").getJoueur() == joueur)
							|| (caseChoisi.getRoute("haut") != null
									&& caseChoisi.getRoute("haut").getJoueur() == joueur)
							|| (adja.getRoute("bas") != null
									&& adja.getRoute("bas").getJoueur() == joueur)
							|| (adja.getRoute("gauche") != null && adja.getRoute("gauche").getJoueur() == joueur)) {
						ok = (MapColonie.get("haut gauche") == null && MapColonie.get("bas droit") == null
								&& mapColonieAdja.get("haut gauche") == null
								&& mapColonieAdja.get("bas droit") == null);
					}
					break;
			}
		}
		return ok;
	}

	public void placerPremierTour() {
		String CaseLocation = c.choixPlacementColonie();
		String[] tab = decoupe(CaseLocation);
		int i = Integer.parseInt(tab[0]);
		int j = Integer.parseInt(tab[1]);
		Case caseChoisi = plateau.getPlateau()[i][j];
		String location = c.choixLocationDeLaColonie();
		boolean placementOk = false;
		int essai = 0;
		while (!placementOk) {
			if (essai != 0) {
				CaseLocation = c.choixPlacementColonie();
				tab = decoupe(CaseLocation);
				i = Integer.parseInt(tab[0]);
				j = Integer.parseInt(tab[1]);
				caseChoisi = plateau.getPlateau()[i][j];
				location = c.choixLocationDeLaColonie();
			}
			if (placementOkPremierTours(caseChoisi, location)) {
				placement(caseChoisi, location);
				placementOk = true;
			} else {
				System.out
						.println("Vous ne pouvez pas placer votre colonie ici, veuillez choisir un autre emplacement.");
				essai++;
			}
		}
	}

	public void placerPremierTourIa() {
		Random rd = new Random();
		boolean ok = false;
		String[] locationColonie = { "haut gauche", "haut droit", "bas droit", "bas gauche" };
		while (!ok) {
			int i = rd.nextInt(plateau.getPlateau().length - 2) + 1;
			int j = rd.nextInt(plateau.getPlateau()[0].length - 2) + 1;
			int nLocation = rd.nextInt(4);
			if (placementOkPremierTours(plateau.getPlateau()[i][j], locationColonie[nLocation])) {
				placement(plateau.getPlateau()[i][j], locationColonie[nLocation]);
				ok = true;
			}
		}
	}

	public void placement(Case caseChoisi, String location) {
		String[] decoupe = decoupe(location);
		String locationHorizontale = decoupe[1];
		String locationVerticale = decoupe[0];
		HashMap<String, Case> map = new HashMap<>();
		String inverseLh, inverseLv;
		if (locationHorizontale.equals("gauche")) {
			inverseLh = "droite";
		} else {
			inverseLh = "gauche";
		}

		if (locationVerticale.equals("bas")) {
			inverseLv = "haut";
		} else {
			inverseLv = "bas";
		}

		for (var v : caseChoisi.getMap().entrySet()) {
			if (v.getKey().equals(location)) {
				map.put(location, v.getValue());
			}
			if (v.getKey().equals(locationHorizontale)) {
				map.put(inverseLv + " " + locationHorizontale, v.getValue());
			}
			if (v.getKey().equals(locationVerticale)) {
				map.put(locationVerticale + " " + inverseLh, v.getValue());
			}
		}

		if (locationHorizontale.equals("gauche") && locationVerticale.equals("haut")) {
			map.put("bas droit", caseChoisi);
		}
		if (locationHorizontale.equals("gauche") && locationVerticale.equals("bas")) {
			map.put("haut droit", caseChoisi);
		}
		if (locationHorizontale.equals("droit") && locationVerticale.equals("haut")) {
			map.put("bas gauche", caseChoisi);
		}
		if (locationHorizontale.equals("droit") && locationVerticale.equals("bas")) {
			map.put("haut gauche", caseChoisi);
		}

		this.case_adja = map;
		setMapColonie();

		if (caseChoisi.hasPort()) {
			joueur.getPorts().add(caseChoisi.getPort());
		}
	}

	public boolean placementOkPremierTours(Case caseChoisi, String location) {
		boolean ok = false;
		HashMap<String, Colonie> MapColonie = caseChoisi.getMapColonie();
		HashMap<String, Colonie> mapColonieAdja;
		HashMap<String, Case> caseAdja = caseChoisi.getMap();
		Case adja = caseAdja.get(location);
		if (MapColonie.get(location) == null) {
			switch (location) {
				case "haut droit":
				case "bas gauche":
					mapColonieAdja = adja.getMapColonie();
					ok = (MapColonie.get("haut gauche") == null && MapColonie.get("bas droit") == null
							&& mapColonieAdja.get("haut gauche") == null
							&& mapColonieAdja.get("bas droit") == null);
					break;
				case "haut gauche":
					mapColonieAdja = adja.getMapColonie();
					ok = (MapColonie.get("bas gauche") == null && MapColonie.get("haut droit") == null
							&& mapColonieAdja.get("bas gauche") == null
							&& mapColonieAdja.get("haut droit") == null);
					break;
				case "bas droit":
					mapColonieAdja = adja.getMapColonie();
					ok = (MapColonie.get("haut droit") == null && MapColonie.get("bas gauche") == null
							&& mapColonieAdja.get("haut droit") == null
							&& mapColonieAdja.get("bas gauche") == null);
					break;
			}
		}
		return ok;
	}

	public void setMapColonie() {
		for (var v : case_adja.entrySet()) {
			switch (v.getKey()) {
				case "haut gauche":
					v.getValue().getMapColonie().put("bas droit", this);
					break;
				case "bas droit":
					v.getValue().getMapColonie().put("haut gauche", this);
					break;
				case "bas gauche":
					v.getValue().getMapColonie().put("haut droit", this);
					break;

				case "haut droit":
					v.getValue().getMapColonie().put("bas gauche", this);
					break;
			}
		}
	}

	public Joueur getJoueur() {
		return this.joueur;
	}

	public boolean getIsVille() {
		return this.isVille;
	}

	public HashMap<String, Case> getCaseAdja() {
		return this.case_adja;
	}

	String CaseDescription() {
		String str = "";
		for (var v : this.case_adja.entrySet()) {
			str += v.getValue().getEnvironement() + " numéro " + v.getValue().getNumero() + "\n";
		}
		return str;
	}

	public String toString() {
		String adjacents = CaseDescription();
		String ColonieOuVille;
		if (isVille) {
			ColonieOuVille = "Ville";
		} else {
			ColonieOuVille = "Colonie";
		}
		return "Colonie du joueur " + this.joueur.getNom() +
				" Est une " + ColonieOuVille +
				" Située entre " + adjacents;
	}

	public int getNbrRessource() {
		return this.nbrRessource;
	}

}
