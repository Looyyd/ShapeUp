package fr.utt.LO02.ShapeUp.Controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;

import fr.utt.LO02.ShapeUp.Modele.Carte;
import fr.utt.LO02.ShapeUp.Modele.JeuDeCarte;
import fr.utt.LO02.ShapeUp.Modele.Partie;
import fr.utt.LO02.ShapeUp.Modele.Phase;
import fr.utt.LO02.ShapeUp.Modele.Strategie;
import fr.utt.LO02.ShapeUp.Modele.Tapis;
import fr.utt.LO02.ShapeUp.Vue.InterfaceGraphique;
import fr.utt.LO02.ShapeUp.Vue.InterfaceGraphiqueChoix;

/**
 * This controller allows to call the good methods according to the interaction
 * with the button in the settings window.
 * 
 * @author petit
 *
 */
public class ControleurBtnChoix extends Observable {
	Integer i;
	private Partie partie;

	/**
	 * This constructor instantiates a new ControleurBtnChoix when a button in a
	 * windows settings is pushed. According to the phase of the party, this object
	 * move forward the party thanks to the interaction.
	 * 
	 * @param partie
	 * @param btn
	 * @param interfaceGraph
	 */

	public ControleurBtnChoix(Partie partie, JButton btn, InterfaceGraphiqueChoix interfaceGraph) {
		this.partie = partie;
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (partie.etatTour.phaseActuel == Phase.CHOIXVERSION) {

					if (btn.getText().equals(interfaceGraph.getTexteNon())) {
						System.out.println("N");
						partie.setAdvanced(false);
						partie.nombreDeJoueur();

					} else {//Oui
						System.out.println("O");
						partie.setAdvanced(true);
						partie.nombreDeJoueur();
					}
					setChanged();
					notifyObservers();
				} else if (partie.etatTour.phaseActuel == Phase.CHOIXNBJOUEUR) {

					i = Integer.valueOf(btn.getText());
					System.out.println(i);
					partie.setTypePartie(i);

					partie.demandeNombreJoueurReels();
					setChanged();
					notifyObservers();

				} else if (partie.etatTour.phaseActuel == Phase.CHOIXJOUEUREELS) {

					i = Integer.valueOf(btn.getText());
					System.out.println(i);
					partie.setNbJoueur(i);
					partie.choixDifficulteeIA();
					setChanged();
					notifyObservers();

				} else if (partie.etatTour.phaseActuel == Phase.CHOIXDIFFICULTEIA) {
					int i=0;
					boolean difficultySet=false;
					for(Strategie strat : partie.getListStrategies()) {
						if(strat.getNomDifficulté().equals(btn.getText())) {
							System.out.println(i);
							partie.setDifficulte(i);
							difficultySet=true;
						}
						i++;
					}
					
					if(!difficultySet)
						System.out.println("ERREUR difficulté non trouvée");
					
					/*
					if (btn.getText().equals("Interm�diare")) {
						System.out.println("0");
						partie.setDifficulte(0);
					} else {
						System.out.println("1");
						partie.setDifficulte(1);
					}*/

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
						partie.setTapis(new Tapis());

						setChanged();
						notifyObservers();

					}

				} else if (partie.etatTour.phaseActuel == Phase.CHOIXTAPIS) {

					i = Integer.valueOf(btn.getText());
					System.out.println(i);

					partie.getTapis().setFormeARespecter(partie.getTapis().getListesFormes().get(i));
					partie.getTapis().setCases(new Carte[partie.getTapis()
							.getFormeARespecter().length][partie.getTapis().getFormeARespecter()[0].length]);

					partie.debutDuTour();

				}
			}
		});
	}

}
