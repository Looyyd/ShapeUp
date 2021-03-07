package fr.utt.LO02.ShapeUp.Controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.LO02.ShapeUp.Modele.*;

/**
 * This controller allows to call the good methods according to the interaction
 * with the button which represents the card in hand.
 * 
 * @author petit
 *
 */

public class ControleurCarteEnMain {
	/**
	 * This method creates an instance of ControleurCarteEnMain when the button is
	 * pressed. Pressing the button in the phase debutTour assigns a value to
	 * carteEnmain and the method menuPlacement is executed.
	 * 
	 * @param partie
	 * @param tapis
	 * @param carteEnMain
	 * @param numero
	 */

	public ControleurCarteEnMain(final Partie partie, Tapis tapis, JButton carteEnMain, int numero) {
		carteEnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (partie.etatTour.phaseActuel == Phase.DEBUTTOUR
						|| partie.etatTour.phaseActuel == Phase.SELECTIONDEPUISMAIN) {
					Joueur joueur = partie.getJoueurDuTour();
					partie.cartePioche = joueur.getCarteEnMain().get(numero);
					if (partie.isAdvanced()) {
						joueur.getCarteEnMain().remove(partie.cartePioche);
						// si partie normale la carte est supprimé en fin de tour
					}
					partie.menuPlacement();// on appelle le menu placement il sait qu'elle carte on a sélectionnée car
											// cartePioche est globale
				}
			}
		});
	}

}
