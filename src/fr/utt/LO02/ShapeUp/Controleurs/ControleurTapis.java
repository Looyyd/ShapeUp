package fr.utt.LO02.ShapeUp.Controleurs;

import javax.swing.*;

import fr.utt.LO02.ShapeUp.Modele.Carte;
import fr.utt.LO02.ShapeUp.Modele.Partie;
import fr.utt.LO02.ShapeUp.Modele.Phase;
import fr.utt.LO02.ShapeUp.Modele.Tapis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurTapis {

	public ControleurTapis(Partie partie, Tapis tapis, int ligne, int colonne, JButton bouton) {
		// ligne et colonnes seront decalés par rapport aux cases du tapis car il y a
		// des bouttons supplémentaires pour les coups possibles

		bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Carte[][] cases = tapis.getCases();
				Carte carte;

				if (partie.etatTour.phaseActuel == Phase.DEBUTTOUR
						|| (partie.etatTour.phaseActuel == Phase.MENUPLACEMENT && !partie.etatTour.carteBouge
								&& partie.etatTour.cartePlace)) {// on peut selectionner une carte du tapis pour la
					// bouger
					try {
						carte = cases[ligne - 1][colonne - 1];// les -1 compenssent les nboutons supplémentaires
					} catch (ArrayIndexOutOfBoundsException e) {
						return;// devrait jamais arriver en soit car les colonnes supplémentaires ne sont pas
								// affichés
					}
					if (carte != null) {// une cartepartie.etatTour.phaseActuel
						// une carte a bouger a été selectionné
						partie.menuMouvement2(carte);
					}
				} else if (partie.etatTour.phaseActuel == Phase.MENUPLACEMENT && !partie.etatTour.cartePlace) {
					// une carte doit etre placé
					// les coups sont affichés
					int nCoup = tapis.positionCaseToCoup(ligne - 1, colonne - 1);// ajusté pour les cases
					// renvoi -1 si cette case ne correspond a aucun coup
					if (nCoup != -1) {
						tapis.effectuerCoup(tapis.getCoupsLegaux().get(nCoup), tapis.getDecalageAFaire().get(nCoup));
						partie.etatTour.cartePlace = true;
						if (partie.etatTour.carteBouge) {
							partie.finDuTour();
						} else {// carte pas encore bougé
							System.out.println("Souhaiter vous desormais bouger une carte?");
							tapis.afficherTapis();
						}
					}
				} else if (partie.etatTour.phaseActuel == Phase.MENUMOUVEMENT2) {
					// une carte doit etre placé
					// les coups sont affichés
					int nCoup = tapis.positionCaseToCoup(ligne - 1, colonne - 1);// ajusté pour les cases
					// renvoi -1 si cette case ne correspond a aucun coup
					if (nCoup != -1) {
						tapis.effectuerCoup(tapis.getCoupsLegaux().get(nCoup), tapis.getDecalageAFaire().get(nCoup));
						partie.etatTour.carteBouge = true;
						if (partie.etatTour.cartePlace) {
							partie.finDuTour();
						} else {// carte pas encore bougé
							if (partie.isAdvanced()) {
								partie.selectionDepuisMain();
							} else {
								partie.menuPlacement(); // si pas de carte placé on donne le menu
							}
						}
					}

				}

			}
		});

	}

}
