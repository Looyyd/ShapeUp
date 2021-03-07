package fr.utt.LO02.ShapeUp.Modele;
/**
 * This class keep in memory the state of player's round.
 * With this object we could know if a player has ever moved or placed his Card.
 * 
 * @author petit
 *
 */

public class EtatDuTour{ 
	/**
	 * This attribute is a boolean. It takes the value true when a real player put a card.
	 * In the instantiation catrePlace is false
	 */
    public Boolean cartePlace;//false au debut,ne peut placer qu'une carte
    /**
     * This attribute is a boolean. carteBouge takes the value true when a real player move a card.
     * It allows to move a card only one time in a round.
     * In the instantiation catreBouge is false
     */
    public Boolean carteBouge;//ne peut bouger qu'une carte
    /**
     * This attribute can take the value from the list Phase.
     * Thanks to it we can know the actual phase of a round player. 
     * This attribute allows to the different Controllers and Views to know how react according to the actual phase.
     *  
     */
    public Phase phaseActuel;
}