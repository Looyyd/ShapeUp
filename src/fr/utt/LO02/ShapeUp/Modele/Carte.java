package fr.utt.LO02.ShapeUp.Modele;

import javax.swing.*;



import java.io.File;

/**
 * This class represents a Card
 */
public class Carte {
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * the color of the card
	 */
	private Couleur couleur;
	/**
	 * the shape of the car
	 */
	private Forme forme;
	/**
	 * Is the shape filled or empty
	 */
	private Remplissage remplissage;

	public Forme getForme() {
		return forme;
	}

	public Remplissage getRemplissage() {
		return remplissage;
	}

	/**
	 * The image that represents the card. It is used in the graphical interface
	 * @see fr.utt.LO02.ShapeUp.Vue.InterfaceGraphique#InterfaceGraphique(Partie)
	 */
	public final ImageIcon image;

	public Carte(Couleur couleur, Forme forme, Remplissage remplissage) {

		this.couleur = couleur;
		this.forme = forme;
		this.remplissage = remplissage;

		String dir = System.getProperty("user.dir");
		image = new ImageIcon(dir + File.separator + "src"+ File.separator+ "fr"+File.separator+"utt"+File.separator+"LO02"+File.separator+"ShapeUp" + File.separator+ "images" + File.separator + affichageTapis() + ".png");

	}

	/**
	 * Prints a card with an ugly format, mostly used for debbuging
	 *
	 * @see #affichageTapis() for nice format
	 * @return
	 */
	@Override
	public String toString() {
		return "Carte [couleur=" + couleur + ", forme=" + forme + ", remplissage=" + remplissage + "]";
	}

	/**
	 * Prints card with a nice format for example for a red filled triangle will be printed as :▲R
	 * 
	 * XX serves as padding
	 * @see Tapis#afficherTapis()
	 * @return
	 */
	public String affichageTapis(){
		String S="";

		if(remplissage==Remplissage.PLEIN){
			switch(forme) {
			case CARRE:
				S=S+"■";
				break;
			case TRIANGLE:
				S=S+"▲";
				break;			
			case CERCLE:
				S=S+"●";
				break;
			default:
				System.out.print("Forme inconnue\n");
				break;
			}
		}
		else if(remplissage==Remplissage.VIDE){
			switch(forme) {
			case CARRE:
				S=S+"□";
				break;
			case TRIANGLE:
				S=S+"△";
				break;			
			case CERCLE:
				S=S+"○";
				break;
			default:
				System.out.print("Forme inconnue\n");
				break;
		}
		}
		
		
		switch(couleur) {
		case ROUGE:
			S=S+"R";
			break;
		case BLEU:
			S=S+"B";
			break;
		case VERT:
			S+="V";
			break;
		default:
			System.out.print("Couleur inconnue\n"); 
		}

		return S;
	}


}
