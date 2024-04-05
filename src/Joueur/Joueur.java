package Joueur;

import Carte.Developpement.CarteDeveloppement;
import Carte.Developpement.Chevalier;
import Carte.Developpement.PointDeVictoire;
import Carte.Developpement.Progres.Progres;
import Carte.Developpement.Progres.ProgresInvention;
import Carte.Developpement.Progres.ProgresMonopole;
import Carte.Developpement.Progres.ProgresRoute;
import Carte.Ressources.CarteRessources;
import Carte.Speciale.CarteSpeciale;
import Jeu.Communication;
import Plateau.Plateau;
import Plateau.Infrastructures.Colonie;
import Plateau.Infrastructures.Port;
import Plateau.Infrastructures.PortSpecialise;
import Plateau.Infrastructures.Route;
import java.util.*;

public class Joueur {
	protected boolean carteDev; // dit si le joueur à utilier une carte developpement ou non durant son tour;
	protected final String nom;
	protected int points;
	protected Communication c;
	public final String couleur;
	public final int chiffreCouleur;
	protected LinkedList<Colonie> colonie;
	protected LinkedList<CarteRessources> deckCarteRessources;
	protected LinkedList<CarteDeveloppement> deckCarteDeveloppement;
	protected LinkedList<CarteSpeciale> deckCarteSpeciale;
	protected LinkedList<Port> port;
	protected Plateau plateau;
	protected int nbColonies;
	protected int nbVilles;
	protected int nbRoutes;
	protected int nbChevalierJouer;

	public Joueur(String nom, String couleur, Plateau p) {
		this.nom = nom;
		this.couleur = couleur;
		points = 0;
		c = new Communication();
		deckCarteRessources = new LinkedList<>();
		deckCarteDeveloppement = new LinkedList<>();
		deckCarteSpeciale = new LinkedList<>();
		colonie = new LinkedList<>();
		port = new LinkedList<>();
		this.plateau = p;
		nbColonies = 3;
		nbVilles = 4;
		nbRoutes = 13;
		nbChevalierJouer = 0;
		carteDev = true;
		;
		switch (couleur) {
			case "bleu":
				chiffreCouleur = 0;
				break;
			case "vert":
				chiffreCouleur = 1;
				break;
			case "jaune":
				chiffreCouleur = 2;
				break;
			case "rouge":
				chiffreCouleur = 3;
				break;
			default:
				chiffreCouleur = -1;
		}
	}

	public void affiche() {
		System.out.println("Nom :" + this.nom + ", couleur " + this.couleur);
		System.out.println("Nombre de point : " + this.points);
	}

	public int LancerDe() {
		Random rd = new Random();
		int val = rd.nextInt(11) + 2;
		return val;
	}

	public int nbRessource(String ressource) {
		int r = 0;
		for (CarteRessources c : deckCarteRessources) {
			if (c.getNom().equalsIgnoreCase(ressource))
				r += 1;
		}
		return r;
	}

	public void FicheCoutConstruction() {
		int nbArgile = nbRessource("Argile");
		int nbBois = nbRessource("Bois");
		int nbLaine = nbRessource("Laine");
		int nbBle = nbRessource("Ble");
		int nbMinerai = nbRessource("Minerai");
		System.out.println("Matériaux en possession: Argile(" + nbArgile + "), Bois(" + nbBois + "), Laine("
				+ nbLaine + "), Blé(" + nbBle + "), Minerai(" + nbMinerai + ")\n");
		System.out.println("Matériaux nécéssaires pour une route: Argile(1) , Bois(1)\n");
		System.out.println("Matériaux nécéssaires pour une colonie: Argile(1) , Bois(1), Laine(1), Blé(1)\n");
		System.out.println("Matériaux nécéssaires pour une ville: Minerai(3) , Blé(2)");
		System.out.println("Attention, vous devez posséder une colonie pour pouvoir construire une ville !!!\n");
		System.out
				.println("Matériaux nécéssaires pour acheter une carte développement: Minerai(1), Laine(1), Blé(1)\n");
	}

	private void jouerCarteDeveloppement() {
		if (deckCarteDeveloppement.isEmpty()) {
			System.out.println("Vous n'avez pas de carte de développement");
		} else if (carteDev) {
			StringBuilder sb = new StringBuilder();
			for (CarteDeveloppement c : deckCarteDeveloppement)
				sb.append(c + " ");
			System.out.println("Voici la liste de vos cartes développement" + sb.toString());
			String dev = c.choixAction("Voulez-vous jouer une carte développement ?");
			if (dev.equals("oui"))
				utiliserCarteDevelopment();
		}
	}

	private void utiliserCarteDevelopment() {
		boolean hasChevalier = false;
		boolean hasProgres = false;
		carteDev = false;
		for (CarteDeveloppement cd : deckCarteDeveloppement) {
			if (cd instanceof Chevalier)
				hasChevalier = true;
			if (cd instanceof Progres)
				hasProgres = true;
		}
		String carteJouer = "";
		if (hasChevalier && hasProgres)
			carteJouer = c.choixCarteDeveloppement("Voulez-vous jouer une carte Chevalier ou une carte Progres ?");
		else if (hasChevalier)
			carteJouer = "chevalier";
		else if (hasProgres)
			carteJouer = "progres";

		switch (carteJouer) {
			case "chevalier":
				for (CarteDeveloppement cd : deckCarteDeveloppement) {
					if (cd instanceof Chevalier) {
						((Chevalier) cd).jouerChevalier();
						deckCarteDeveloppement.remove(cd);
						break;
					}
				}
				break;
			case "progres":
				for (CarteDeveloppement cd : deckCarteDeveloppement) {
					StringBuilder sb = new StringBuilder();
					if (cd instanceof Progres) {
						sb.append(cd);
					}
					System.out.println("Voici la liste de vos cartes proges: " + sb.toString());
					String carteProgres = c.choixCarteProgres();
					switch (carteProgres) {
						case "invention":
							for (CarteDeveloppement cp : deckCarteDeveloppement) {
								if (cp instanceof ProgresInvention) {
									((ProgresInvention) cp).pioche();
									deckCarteDeveloppement.remove(cp);
									break;
								}
							}
							break;
						case "monopole":
							for (CarteDeveloppement cp : deckCarteDeveloppement) {
								if (cp instanceof ProgresMonopole) {
									((ProgresMonopole) cp).monopole();
									deckCarteDeveloppement.remove(cp);
									break;
								}
							}
							break;
						case "route":
							for (CarteDeveloppement cp : deckCarteDeveloppement) {
								if (cp instanceof ProgresRoute) {
									((ProgresRoute) cp).construireRoute();
									deckCarteDeveloppement.remove(cp);
									break;
								}
							}
							break;
					}
				}
				break;
		}
	}

	protected void achatCarteDeveloppement() {
		if (!plateau.getPileCarteDeveloppement().isEmpty()) {
			CarteDeveloppement cd = plateau.getPileCarteDeveloppement().removeFirst();
			System.out.println("Vous avez obtenu: " + cd);
			cd.setJoueur(this);
			deckCarteDeveloppement.add(cd);
			perdreRessource("minerai", 1);
			perdreRessource("laine", 1);
			perdreRessource("blé", 1);
		} else {
			System.out.println("Il n'y a plus de carte développement disponible");
		}
	}

	private void voirFicheCout() {
		String fiche = c.choixAction("Voulez-vous voir la fiche des cout de construction ?");
		if (fiche.equals("oui"))
			FicheCoutConstruction();
	}

	public void actionEffectuer() {
		carteDev = true;
		jouerCarteDeveloppement();

		int resultatDe = LancerDe();
		plateau.repartirRessource(resultatDe);

		jouerCarteDeveloppement();

		voirFicheCout();

		String commerce;
		if (!hasPort()) {
			commerce = c.choixAction(
					"Vous n'avez pas de port donc le taux pour le commerce est de 4:1. Voulez-vous faire du commerce ? Vous pouvez écrire stop pour arreter l'échange.");
			if (commerce.equals("oui"))
				commerce("4:1");
		} else if (!hasSpecialPort()) {
			commerce = c.choixAction(
					"Vous avez juste un/des ports normaux donc le taux pour le commerce est de 3:1. Voulez-vous faire du commerce ? Vous pouvez écrire stop pour arreter l'échange.");
			if (commerce.equals("oui"))
				commerce("3:1");
		} else {
			StringBuilder sb = new StringBuilder();
			boolean portNormal = false;
			for (Port p : port)
				if (p instanceof PortSpecialise)
					sb.append(p + " ");
				else
					portNormal = true;
			if (portNormal)
				commerce = c.choixAction(
						"Vous avez un/des ports normaux, donc avec un taux de 3:1. Et vous avez aussi avec un taux de 2:1 un/des port(s) spécialisé(s) présent dans la liste suivante:\n"
								+ sb.toString()
								+ "\nVoulez-vous faire du commerce ? Vous pouvez écrire stop pour arreter l'échange.");
			else
				commerce = c.choixAction(
						"Vous un/des port(s) spécialisé qui ont un taux de 2:1 et qui sont dans la liste suivante:\n"
								+ sb.toString()
								+ "\nVoulez-vous faire du commerce ? Vous pouvez écrire stop pour arreter l'échange.");
			if (commerce.equals("oui"))
				commerce("2:1");
		}

		jouerCarteDeveloppement();

		voirFicheCout();

		String achatCarte = c.choixAction("Voulez-vous acheter une carte developpement ?");
		if (achatCarte.equals("oui"))
			achatCarteDeveloppement();

		String construction = c.choixAction("Voulez-vous construire quelque chose ?");
		if (construction.equals("oui")) {
			String choixConstruction = c.choixConstruction();
			switch (choixConstruction) {
				case "route":
					construireRoute();
					break;
				case "colonie":
					construireColonie();
					break;
				case "ville":
					construireVille();
					break;
			}
		}
		jouerCarteDeveloppement();
	}

	public boolean aRessource(int t) {
		int nbArgile = nbRessource("Argile");
		int nbBois = nbRessource("Bois");
		int nbLaine = nbRessource("Laine");
		int nbBle = nbRessource("Ble");
		int nbMinerai = nbRessource("Minerai");
		if (nbArgile < t && nbBois < t && nbLaine < t && nbBle < t && nbMinerai < t) {
			return false;
		}
		return true;
	}

	public boolean aRessource(String str) {
		int nbArgile = nbRessource("Argile");
		int nbBois = nbRessource("Bois");
		int nbLaine = nbRessource("Laine");
		int nbBle = nbRessource("Ble");
		int nbMinerai = nbRessource("Minerai");
		switch (str) {
			case "colonie":
				if (nbArgile < 1 && nbBois < 1 && nbLaine < 1 && nbBle < 1)
					return false;
				break;
			case "ville":
				if (nbMinerai < 3 && nbBle < 2)
					return false;
				break;
			case "route":
				if (nbArgile < 1 && nbBois < 1)
					return false;
				break;
		}
		return true;
	}

	public boolean aRessource(String str, int n) {
		int nbArgile = nbRessource("Argile");
		int nbBois = nbRessource("Bois");
		int nbLaine = nbRessource("Laine");
		int nbBle = nbRessource("Ble");
		int nbMinerai = nbRessource("Minerai");
		switch (str) {
			case "Bois":
				if (nbBois < n) {
					return false;
				}
			case "Argile":
				if (nbArgile < n) {
					return false;
				}
			case "Ble":
				if (nbBle < n) {
					return false;
				}
			case "Minerai":
				if (nbMinerai < n) {
					return false;
				}
			case "Laine":
				if (nbLaine < n) {
					return false;
				}
		}
		return true;
	}

	public void construireColonie() {
		System.out.println(
				"Matériaux utilisés pour la construction: Argile(1) , Bois(1), Laine(1), Blé(1)\nEt voici le nombre de colonie que vous pouvez encore construire "
						+ nbColonies + "\n");
		if (nbColonies != 0) {
			if (!aRessource("colonie")) {
				System.out.println("Vous n'avez pas les ressource necéssaires pour construire une colonie");
			} else {
				perdreRessource("Argile", 1);
				perdreRessource("Bois", 1);
				perdreRessource("Laine", 1);
				perdreRessource("Ble", 1);
				Colonie col = new Colonie(this, false, this.plateau);
				col.placer();
				this.colonie.add(col);
				nbColonies--;
			}
		} else
			System.out.println("Vous avez déjà le nombre maximum de colonies");
	}

	public void construireVille() {
		System.out.println("Voici la liste de vos Colonie");
		String listColonie = "";
		int compteur = 0;
		for (Colonie colon : colonie) {
			compteur += 1;
			listColonie += colon + " (Colonie numéro " + compteur + ")\n";
		}
		System.out.println(listColonie);
		// associer le numéro demandé et la colonie correspondant dans la list
		int colonieUprage = c.choixColonieUpgrade(compteur);
		System.out.println(
				"Matériaux utilisés pour la construction: Minerai(3), Blé(2)\nEt voici le nombre de villes que vous pouvez encore construire "
						+ nbVilles + "\n");
		if (nbVilles != 0) {
			if (!aRessource("ville")) {
				System.out.println("Vous n'avez pas les ressource necéssaires pour construire une ville");
			} else {
				perdreRessource("Minerai", 3);
				perdreRessource("Ble", 2);
				colonie.get(colonieUprage).upgrade();
				nbVilles--;
				nbColonies++;
			}
		} else
			System.out.println("Vous avez déjà le nombre maximum de villes.");
	}

	public void construireRoute() {
		System.out.println(
				"Matériaux utilisés pour la construction: Argile(1), Bois(1)\nEt voici le nombre de route que vous pouvez encore construire "
						+ nbRoutes + "\n");
		if (nbRoutes != 0) {
			if (!aRessource("route"))
				System.out.println("Vous n'avez pas les ressources necéssaires pour construire une route");
			else {
				perdreRessource("argile", 1);
				perdreRessource("bois", 1);
				Route r = new Route(this, plateau);
				r.placer();
				nbRoutes--;
			}
		} else
			System.out.println("Vous avez déjà le nombre maximum de routes.");
	}

	public void recevoirRessource(String ressource, int n) {
		for (int i = 0; i < n; i++) {
			deckCarteRessources.add(new CarteRessources(ressource.toLowerCase(), this));
		}
	}

	public void perdreRessource(String ressource, int n) {
		LinkedList<CarteRessources> list = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			for (CarteRessources cr : deckCarteRessources) {
				if (cr.getNom().equalsIgnoreCase(ressource)) {
					list.add(cr);
				}
			}
		}
		for (CarteRessources i : list) {
			deckCarteRessources.remove(i);
		}
	}

	public boolean hasPort() {
		return !(port.isEmpty());
	}

	public boolean hasSpecialPort() {
		for (Port p : port)
			if (p instanceof PortSpecialise)
				return true;
		return false;
	}

	public void commerce(String taux) {
		int nbArgile = nbRessource("Argile");
		int nbBois = nbRessource("Bois");
		int nbLaine = nbRessource("Laine");
		int nbBle = nbRessource("Ble");
		int nbMinerai = nbRessource("Minerai");
		int t = Integer.parseInt(taux.split(":")[0]);
		boolean echangeEnCour = true;
		System.out.println("Matériaux en possession : Argile(" + nbArgile + "), Bois(" + nbBois + "), Laine(" + nbLaine
				+ "), Blé(" + nbBle + "), Minerai(" + nbMinerai + ")");
		if (taux.equals("4:1") || taux.equals("3:1")) {
			System.out.println(
					"Ce taux vous permet d'échanger" + t + " matière première identique contre une de votre choix");
			if (!aRessource(t)) {
				System.out.println("Vous n'avez pas les ressouces nécessaire");
			} else {
				while (echangeEnCour) {
					String ressource = c.choixRessource("Quel matière voulez-vous utiliser ?");
					if (ressource.equals("stop"))
						echangeEnCour = false;
					else {
						if (nbRessource(ressource) < t) {
							System.out.println("Vous n'avez pas assez de " + ressource);
						} else {
							perdreRessource(ressource, t);
							ressource = c.choixRessource("Quel matière voulez vous recevoir");
							if (ressource.equals("stop"))
								echangeEnCour = false;
							else {
								recevoirRessource(ressource, 1);
								echangeEnCour = false;
							}
						}
					}
				}
			}
		} else {
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (Port p : port) {
				i++;
				sb.append(p + "(" + i + ") ");
			}
			int choixPort = c.choixPort(i);
			Port portChoisi = port.get(choixPort);
			if (portChoisi instanceof PortSpecialise) {
				if (!aRessource(2)) {
					System.out.println("Vous n'avez pas les ressouces nécessaire");
				} else {
					while (echangeEnCour) {
						String ressource = c.choixRessource("Quel matière voulez-vous utiliser ?");
						if (ressource.equals("stop"))
							echangeEnCour = false;
						else {
							if (nbRessource(ressource) < 2) {
								System.out.println("Vous n'avez pas assez de " + ressource);
							} else {
								perdreRessource(ressource, 2);
								ressource = c.choixRessource("Quel matière voulez vous recevoir");
								if (ressource.equals("stop"))
									echangeEnCour = false;
								else {
									recevoirRessource(portChoisi.getRessource(), 1);
									echangeEnCour = false;
								}
							}
						}
					}
				}
			} else {
				if (!aRessource(3)) {
					System.out.println("Vous n'avez pas les ressouces nécessaire");
				} else {
					while (echangeEnCour) {
						String ressource = c.choixRessource("Quel matière voulez-vous utiliser ?");
						if (ressource.equals("stop"))
							echangeEnCour = false;
						else {
							if (nbRessource(ressource) < 3) {
								System.out.println("Vous n'avez pas assez de " + ressource);
							} else {
								String ressourcePerdu = ressource;
								ressource = c.choixRessource("Quel matière voulez vous recevoir");
								if (ressource.equals("stop"))
									echangeEnCour = false;
								else {
									perdreRessource(ressourcePerdu, 3);
									recevoirRessource(ressource, 1);
									echangeEnCour = false;
								}
							}
						}
					}
				}
			}
		}
	}

	public void calculPoints() {
		points = 0;
		for (Colonie col : colonie) {
			if (col.getIsVille())
				points += 2;
			else
				points += 1;
		}
		for (CarteDeveloppement cd : deckCarteDeveloppement) {
			if (cd instanceof PointDeVictoire)
				points += 1;
		}
		for (CarteSpeciale cs : deckCarteSpeciale) {
			points += 2;
		}
	}

	public int getPoints() {
		return points;
	}

	public LinkedList<CarteRessources> getDeckCarteRessources() {
		return deckCarteRessources;
	}

	public String getNom() {
		return nom;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public LinkedList<Port> getPorts() {
		return port;
	}

	public void JouerChevalier() {
		nbChevalierJouer++;
		Scanner sc = new Scanner(System.in);
		System.out.println("Vous avez la possibilité de déplacer le voleur");
		plateau.getVoleur().placer();
		System.out.println(
				"Vous avez la possibilité de récupérer une matière première d'un joueur ayant une colonie qui jouxte l'emplacement  du voleur");
		System.out.println("Voici la liste des joueurs qui ont des colonies près de l'emplacement du voleur:");
		plateau.afficheJoueur();
		System.out.println("Veuillez saisir le nom d'un joueur ");
		String Jnom = sc.nextLine();
		Joueur player = new Joueur(null, "", null);
		for (Joueur j : plateau.getListJoueurs()) {
			if (j.getNom().equals(Jnom)) {
				player = j;
			}
		}
		System.out.println("une ressource vous sera donné au hasard");
		Random r = new Random();
		int x = r.nextInt(5);
		switch (x) {
			case 0:
				if (!player.aRessource("Bois", 1)) {
					System.out.println("Ce joueur n'a pas assez de bois");
				} else {
					player.perdreRessource("Bois", 1);
					recevoirRessource("Bois", 1);
					System.out.println("une ressource de bois vous à été donnée");
				}
			case 1:
				if (!player.aRessource("Argile", 1)) {
					System.out.println("Ce joueur n'a pas assez d'Argile");
				} else {
					player.perdreRessource("Argile", 1);
					recevoirRessource("Argile", 1);
					System.out.println("une ressource d'argile vous à été donnée");
				}
			case 2:
				if (!player.aRessource("Ble", 1)) {
					System.out.println("Ce joueur n'a pas assez de ble");
				} else {
					player.perdreRessource("Ble", 1);
					recevoirRessource("Ble", 1);
					System.out.println("une ressource de ble vous à été donnée");
				}
			case 3:
				if (!player.aRessource("Minerai", 1)) {
					System.out.println("Ce joueur n'a pas assez de Minerai");
				} else {
					player.perdreRessource("Minerai", 1);
					recevoirRessource("Minerai", 1);
					System.out.println("une ressource de minerai vous à été donnée");
				}
			case 4:
				if (!player.aRessource("Laine", 1)) {
					System.out.println("Ce joueur n'a pas assez de laine");
				} else {
					player.perdreRessource("Laine", 1);
					recevoirRessource("Laine", 1);
					System.out.println("une ressource de laine vous à été donnée");
				}
		}

	}

	public LinkedList<Colonie> getColonie() {
		return this.colonie;
	}

	public int getNbChevalier() {
		return nbChevalierJouer;
	}

	public int getNbRoutes() {
		return 15 - nbRoutes;
	}

	public LinkedList<CarteSpeciale> getCarteSpeciale() {
		return deckCarteSpeciale;
	}

	public String getCouleur() {
		return couleur;
	}

	public int getChiffreCouleur() {
		return chiffreCouleur;
	}

}
