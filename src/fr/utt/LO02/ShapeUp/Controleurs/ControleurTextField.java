package fr.utt.LO02.ShapeUp.Controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JTextField;

import fr.utt.LO02.ShapeUp.Modele.JeuDeCarte;
import fr.utt.LO02.ShapeUp.Modele.Partie;
import fr.utt.LO02.ShapeUp.Modele.Phase;
import fr.utt.LO02.ShapeUp.Modele.Tapis;

/**
 * This class allows in settings to enter the name of the real players
 * 
 * @author petit
 *
 */
public class ControleurTextField extends Observable {
	/**
	 * This method is executed when a name is entered in the text field. When the
	 * game is in the phase choixNomJoueur, the arrayList nomJoueurReel is update
	 * with adding of the name entered in the text field. Then the method
	 * nommerJoueur is called if it stays at least one people no named, or the
	 * players, cards and carpet are created.
	 * 
	 * @param partie
	 * @param txtField
	 */

	public ControleurTextField(Partie partie, JTextField txtField) {// sert a nommer les joueurs si besoin
		txtField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (partie.etatTour.phaseActuel == Phase.CHOIXNOMJOUEUR) {
					partie.nombreJoueursNommes++;
					partie.getNomJoueurReel().add(txtField.getText());
					System.out.println(txtField.getText());
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
						partie.setTapis(new Tapis());
						setChanged();
						notifyObservers();

					}

				}
			}
		});
	}

}
