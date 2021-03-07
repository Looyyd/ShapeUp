package fr.utt.LO02.ShapeUp.Modele;

import java.util.ArrayList;

import java.util.concurrent.ConcurrentHashMap.KeySetView;

/**
 * The class Joueur allows to create the players of the party.
 *  A Joueur can be real or an AI.
 * Are a player has a victory card when the ShapeUp is not Advanced
 * are he has 3 distributed cards. The AI have a strategy characterized by the interface Strategie.
 * A player can be characterized by a pseudo too.
 */
public class Joueur {
	/**
	 * Gets pseudo 
	 * 
	 * @return pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * Gets victoryCarte
	 * @return victoryCarte
	 */

	public Carte getVictoryCarte() {
		return victoryCarte;
	}
	/**
	 * This method return a boolean according to the state of the player.
	 * 
	 * @return true if the player is an AI, the false.
	 */

	public boolean isAI() {
		return IsAI;
	}
	/**
	 * This attribute come from the interface Strategie it's null for the real player. 
	 * This attribute allows to the AI to move and place a card during its turn.
	 */
	public Strategie strategie;
	/**
	 * This attribute allows to characterized the objects player by a pseudo.
	 */
	private final String pseudo;
	/**
	 * This attribute is a Carte and represent the victory card. It can be attribute in the beginning of the party
	 * if we play to the normal version of ShapeUp then it is designed at the end of the party.
	 */
	private Carte victoryCarte;
	/**
	 * This attribute allows to do the differnce between a real player and  a Ai. It is true when the object is an AI and
	 * false in the other case.
	 */
	private final boolean IsAI;// true if is ai false if real player
	/**
	 * This attribute represents the 3 cards distributed when we play to the advanced ShapeUp.
	 * These cards are in a ArrayList.
	 */
	private ArrayList<Carte> CarteEnMain = new ArrayList<Carte>();
	/**
	 * Gets carteEnMain
	 * 
	 * @return carteEnMain
	 */

	public ArrayList<Carte> getCarteEnMain() {
		return CarteEnMain;
	}
	/**
	 * Instantiates a Joueur according to its pseudo and his state (Ai or not).
	 * If the player is an AI a strategy is attributed.
	 * @param pseudo
	 * @param IsAI
	 */

	public Joueur(String pseudo, boolean IsAI) {
		this.pseudo = pseudo;
		this.IsAI = IsAI;
		if (IsAI) {// Je pense qu'on pourris effacer cette structure car on attribue les strategies dans Partie/CreerJoueurs
			strategie = new StrategiePlacementSeulementAvecCalculScore();
		} else {
			strategie = null;
		}
	}
	/**
	 * This method attribute the victory card with drawing a the first card on the deck.
	 * This method is called only in a normal party.
	 */
	/*
	 * deleted since only used oonce and joueur doesn't have access to and instance of JeuDeCarte
	public void piocherVictoryCarte() {
		this.victoryCarte = JeuDeCarte.getPremiereCarte();
	}
	*/
	/**
	 * This method allows to display a Joueur on the console.
	 * 
	 */

	@Override
	public String toString() {
		return "Joueur [pseudo=" + pseudo + ", victoryCarte=" + victoryCarte + "]";
	}
	/**
	 * This method display in console each card owned by a player in a advanced ShapeUp party in the form of a menu.
	 */

	public void affichageCarteEnMain() {
		for (int i = 0; i < CarteEnMain.size(); i++) {
			System.out.println(i + ") " + CarteEnMain.get(i));

		}
	}
	/**
	 * This method used in a the normal version of the ShapeUp allows to attribute the victory card for each real player
	 * according to the last card owned. For an AI the victory card is attributed on the first round, it's its second card 
	 * distributed.
	 * @param victoryCarte
	 */

	public void setVictoryCarte(Carte victoryCarte) {
		this.victoryCarte = victoryCarte;
	}

}
