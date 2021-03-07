package fr.utt.LO02.ShapeUp.Modele;
import java.util.ArrayList;
/**
 * This class represents all the card in a ShapeUp's game
 * 
 *  
 * @author petit
 *
 */

public class JeuDeCarte {
	/**
	 * This unique attribute 'carte' of JeuDeCarte is an ArrayList of Carte where we can find each card of the game.
	 */
	private final ArrayList <Carte> cartes = new ArrayList <Carte>();
	/**
	 * This method create the card of the game.
	 * To create the card this method assign a shape, a color and a filling.
	 * Each card is different it exists 18 cards and any duplicate
	 */

	public void creationCartes() {
		int i = 0;
		for (int c = 0; c < 3; c++) {
			for (int f = 0; f < 3; f++) {
				for (int r = 0; r < 2; r++) {
					Couleur enumCouleur = Couleur.values()[c];
					Forme enumForme = Forme.values()[f];
					Remplissage enumRemplissage = Remplissage.values()[r];
					cartes.add(new Carte(enumCouleur, enumForme, enumRemplissage));
					// System.out.println(carte[i]);
					i = i + 1;
				}
			}
		}
	}
	/**
	 * This method shuffle the cards. 
	 * For that, we generate a random integer between 0 and 17 (NombreAleatoire) and we exchange the first card with the
	 * card in position NbAleatoire. Then we repeat this operation for each position of the ArrayList.
	 */

	public void melanger() {
		int NbAleatoire;
		Carte sauvegarde;
		for (int position = cartes.size() - 1; position >= 1; position--) {
			do {
				NbAleatoire = (int) ((Math.random() * 18));// generation d'un nombre aleatoire
			} while (NbAleatoire == 18);
			sauvegarde = (Carte) cartes.get(position);
			cartes.set(position, cartes.get(NbAleatoire));
			cartes.set(NbAleatoire, sauvegarde);
		}
	}
	/**
	 * This method allows to a player to draw a card. It returns the card in the first position and removes it.
	 * 
	 * @return the first card from the deck "sauvegarde".
	 */

	public Carte popPremiereCarte() {
		Carte sauvegardeCarte = (Carte) cartes.get(0);
		cartes.remove(0);
		return sauvegardeCarte;
	}
	/**
	 * This method return a boolean according to the number of card in the deck.
	 * 
	 * @return false when the deck is empty, then true.
	 */

	public boolean piochePleine() {
		return !cartes.isEmpty();
	}

	public void afficherJeuDeCarte() {// A supprimer je crois qu'on ne l'utilise pas 
		for (int i = 0; i < cartes.size(); i++) {
			System.out.println(cartes.get(i));
		}
	}
	/**
	 * Gets carte
	 * 
	 * @return carte
	 */

	public ArrayList<Carte> getCartes() {
		return cartes;
	}
	
	
	
}


