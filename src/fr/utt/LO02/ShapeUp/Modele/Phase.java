package fr.utt.LO02.ShapeUp.Modele;

/**
 * This class is used to keep track of the current state of a Game
 * @see Partie
 */
public enum Phase{//utilisé dans etatDuTour
    /**
     * Advanced oor not
     */
    CHOIXVERSION,//Advanced ou non?
    /**
     * Number of players
     */
    CHOIXNBJOUEUR,//CombienDeJoueur?
    /**
     * How many real players
     */
    CHOIXJOUEUREELS,//Combien de joueur r�els
    /**
     * The AI difficulty
     */
    CHOIXDIFFICULTEIA,
    /**
     * Choose the name of a player
     * @see Joueur#getPseudo()
     */
	CHOIXNOMJOUEUR,
    /**
     * Chose the shape that the placed cards have to respect
     * @see Tapis#getFormeARespecter()
     */
	CHOIXTAPIS,
    /**
     * Do you wish to place a card?
     */
    DEBUTTOUR,//Souhaitez vous bouger une carte
    /**
     * Choose which card to move
     * @see Tapis#menuCarteADeplacer()
     */
    MENUMOUVEMENT,//choisir quelle carte bouger
    /**
     * Where to put the card that you choose to move
     * @see Tapis#placerCarte(Carte, Integer, Integer)
     */
    MENUMOUVEMENT2,//choisir ou bouger ladite carte
    /**
     * Choose wich card from your hand you wish to move
     * @see Partie#selectionDepuisMain()
     */
    SELECTIONDEPUISMAIN,//choisir quelle carte poser en Advanced seulement
    /**
     * Choose where do you wish to place the card you picked or that you choose to place from your hand
     * @see Tapis#effectuerCoup(Coup, Cardinaux)
     */
    MENUPLACEMENT,//choisir ou placer la carte(peut venir de menuMouvement,debutTour ou selection depuis main
    //si dans menu placement et qu'on a placé une carte et pas bougé de carte alors question posée : Souhaitez vous bouger une carte
    /**
     * When the human turn is over and the AI turns are being calculated
     */
    FINDUTOUR,//quand le tour humain est fini est qu'aucun autre n'a commencé//donc calcul des tours IA en cours
    /**
     * When the game is over
     * @see Partie#partieFinie()
     * @see Partie#finDePartie()
     */
    PARTIEFINIE//quand la partie est finie, pour que l'interface graphique cesse d'accepter des mouvements
}