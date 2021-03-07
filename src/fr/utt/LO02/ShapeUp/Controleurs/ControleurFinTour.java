package fr.utt.LO02.ShapeUp.Controleurs;

import javax.swing.*;

import fr.utt.LO02.ShapeUp.Modele.Partie;
import fr.utt.LO02.ShapeUp.Modele.Phase;
import fr.utt.LO02.ShapeUp.Vue.InterfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * Creates an ActionListener to the end turn button When the button is clicked
 * the turn is ended if the player has already placed a card (it thus allows to
 * end a turn without moving a card
 */
public class ControleurFinTour extends Observable {
	ControleurFinTour controleur;

	public ControleurFinTour(JButton btn, Partie partie, InterfaceGraphique gui) {
		controleur = this;
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (partie.etatTour.cartePlace == true) {// on peut finir son tour si on a placé notre carte
					partie.finDuTour();
				} else {
					gui.update(controleur, "Vous ne pouvez finir votre tour que après avoir placé une carte");
				}
			}
		});
	}
}
