package fr.utt.LO02.ShapeUp.Controleurs;

import java.util.Observable;

import fr.utt.LO02.ShapeUp.Modele.*;
import fr.utt.LO02.ShapeUp.Vue.InterfaceGraphique;

/**
 * This controller receives input strings from VueLigneCommand and calls the
 * appropriate function from Partie if it is a valid input that answers the last
 * printed instruction
 * 
 * @see fr.utt.LO02.ShapeUp.Vue.VueLigneCommande
 */
public class ControleurTexte extends Observable {
	Partie partie;
	Tapis tapis;

	Joueur joueur;

	public ControleurTexte(Partie partie) {
		this.partie = partie;

	}

	/**
	 * Takes the input string and verifies if it is a valid input to the last asked question
	 * If it is it modifies the model accordingly
	 * @see Partie#Partie()
	 * @see Enum Phase
	 * @param input
	 */
	public void inputTexte(String input) {
		Integer inputAsInt;
		Carte carte;
		if (partie.etatTour.phaseActuel == Phase.FINDUTOUR) {
			return;// si en cours d'un tour IA ne pas accepter d'inputs
		} else if (partie.etatTour.phaseActuel == Phase.DEBUTTOUR) {// Souhaitez vous bouger une carte ?

			if (checkON(input)) {
				if (input.equals("N")) {
					if (partie.isAdvanced()) {
						partie.selectionDepuisMain();
					} else {
						partie.menuPlacement();
					}
				} else {
					partie.menuMouvement();
				}
			}

		} else if (partie.etatTour.phaseActuel == Phase.MENUMOUVEMENT) {// choix de la carte a bouger
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, partie.nombreDechoix, inputAsInt)) {
				carte = tapis.getCarteTapis().get(inputAsInt);
				partie.menuMouvement2(carte);
			}
		} else if (partie.etatTour.phaseActuel == Phase.MENUMOUVEMENT2) {// Ou poser la carte
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, partie.nombreDechoix, inputAsInt)) {
				tapis.effectuerChoixI(inputAsInt);
				partie.etatTour.carteBouge = true;// carte bougé
				if (partie.etatTour.cartePlace == false) {
					if (partie.isAdvanced()) {
						partie.selectionDepuisMain();
					} else {
						partie.menuPlacement(); // si pas de carte placé on donne le menu
					}
				} else {
					partie.finDuTour();// si carte placé et carte bpugé c'est la fin du tour
				}
			}
		} else if (partie.etatTour.phaseActuel == Phase.SELECTIONDEPUISMAIN) {// nous sommes donc en mode advanced

			joueur = partie.getJoueurDuTour();
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, joueur.getCarteEnMain().size() - 1, inputAsInt)) {
				partie.cartePioche = joueur.getCarteEnMain().get(inputAsInt);
				joueur.getCarteEnMain().remove(partie.cartePioche);

				partie.menuPlacement();// on appelle le menu placement il sait qu'elle carte on a sélectionnée car
										// cartePioche est globale

			}
		} else if (partie.etatTour.phaseActuel == Phase.MENUPLACEMENT && partie.etatTour.cartePlace == false) {
			// on
			// vient
			// d'entrer
			// dans
			// le
			// menu
			// placement

			this.tapis = partie.getTapis();// Attribution du Tapis
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, partie.nombreDechoix, inputAsInt)) {
				tapis.effectuerChoixI(inputAsInt);
				partie.etatTour.cartePlace = true;
				if (partie.etatTour.carteBouge) {
					partie.finDuTour();
				} else {
					System.out.println("Souhaitez vous désormais bouger une carte?");
				}
			}

		} else if (partie.etatTour.phaseActuel == Phase.MENUPLACEMENT && partie.etatTour.cartePlace == true) {// souhaitez
																												// vous
																												// desormais
																												// bouger
																												// une
																												// carte
			if (checkON(input)) {
				if (input.equals("N")) {
					partie.finDuTour();
				} else {
					partie.menuMouvement();
				}
			}
		} else if (partie.etatTour.phaseActuel == Phase.CHOIXVERSION) {
			if (checkON(input)) {
				if (input.equals("N")) {
					partie.setAdvanced(false);
					partie.nombreDeJoueur();
					setChanged();
					notifyObservers();
				} else {
					partie.setAdvanced(true);
					partie.nombreDeJoueur();
					setChanged();
					notifyObservers();
				}
			}
		} else if (partie.etatTour.phaseActuel == Phase.CHOIXNBJOUEUR) {
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(partie.minNombreDeJoueurs, partie.maxNombredeJoueurs, inputAsInt)) {
				partie.setTypePartie(inputAsInt);
				partie.demandeNombreJoueurReels();
				setChanged();
				notifyObservers();
			}
		} else if (partie.etatTour.phaseActuel == Phase.CHOIXJOUEUREELS) {
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, partie.getTypePartie(), inputAsInt)) {
				partie.setNbJoueur(inputAsInt);
				partie.choixDifficulteeIA();
				setChanged();
				notifyObservers();

			}

		} else if (partie.etatTour.phaseActuel == Phase.CHOIXDIFFICULTEIA) {
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, 1, inputAsInt)) {
				partie.setDifficulte(inputAsInt);

				if (partie.getNbJoueur() != 0) {
					partie.nommerJoueur();
					setChanged();
					notifyObservers();

				} else {
					partie.creerJoueurs();
					partie.setJeu(new JeuDeCarte());
					partie.getJeu().creationCartes();
					partie.getJeu().melanger();
					partie.hiddeenCarte();
					partie.setTapis(this.tapis = new Tapis());
					setChanged();
					notifyObservers();

				}
			}
		} else if (partie.etatTour.phaseActuel == Phase.CHOIXNOMJOUEUR) {
			partie.nombreJoueursNommes++;
			partie.getNomJoueurReel().add(input);
			if (partie.getNbJoueur() > partie.nombreJoueursNommes) {
				partie.nommerJoueur();
				setChanged();
				notifyObservers();
			} else {
				partie.creerJoueurs();
				partie.setJeu(new JeuDeCarte());
				partie.getJeu().creationCartes();
				partie.getJeu().melanger();
				partie.hiddeenCarte();
				partie.setTapis(this.tapis = new Tapis());
				setChanged();
				notifyObservers();
			}

		} else if (partie.etatTour.phaseActuel == Phase.CHOIXTAPIS) {
			try {
				inputAsInt = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Veuillez donner un nombre");
				return;
			}
			if (inputDansBornes(0, partie.getTapis().getListesFormes().size() - 1, inputAsInt)) {
				partie.getTapis().setFormeARespecter(partie.getTapis().getListesFormes().get(inputAsInt));
				partie.getTapis().setCases(new Carte[partie.getTapis()
						.getFormeARespecter().length][partie.getTapis().getFormeARespecter()[0].length]);
				partie.debutDuTour();
				setChanged();
				notifyObservers();

			}
		}
	}

	private boolean inputDansBornes(int min, int max, int input) {
		if (min <= input && max >= input) {
			return true;
		} else {
			System.out.println("Veuillez saisir un int dans l'intervalle donnée");
			return false;
		}
	}

	private boolean checkON(String input) {
		if (input.equals("O")) {
			return true;
		} else if (input.equals("N")) {
			return true;
		} else {
			System.out.println("Veuillez répondre par oui ou par non");
			return false;
		}
	}
}
