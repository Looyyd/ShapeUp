package fr.utt.LO02.ShapeUp.Main;

import java.awt.*;

import fr.utt.LO02.ShapeUp.Controleurs.*;
import fr.utt.LO02.ShapeUp.Modele.*;
import fr.utt.LO02.ShapeUp.Vue.*;


/**
 * Main launches the Partie class the text controller as well as a new thread for the graphical interface
 * @see InterfaceGraphiqueChoix#InterfaceGraphiqueChoix(Partie, ControleurTexte)
 * @see Partie#Partie()
 * @see ControleurTexte#ControleurTexte(Partie)
 */
public class Main {


	public static void main(String[] args) {
		Partie partie = new Partie();
		ControleurTexte controleur = new ControleurTexte(partie);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGraphiqueChoix window = new InterfaceGraphiqueChoix(partie, controleur);
					window.frame.setVisible(true);
					partie.setInterfaceChoix(window);
					//inteface texte :
					new VueLigneCommande(partie,controleur );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}