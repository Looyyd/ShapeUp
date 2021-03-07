package fr.utt.LO02.ShapeUp.Modele;
/**
 * This class allows to qualify a move.
 * A move is the placement of a card 
 * according to the cardinality of a reference card.
 *  
 *  
 * @author petit
 *
 */
public class Coup {
	/**
	 * This attribute represents the card to place during a move.
	 * It's an object from Carte.
	 */
    private Carte carteAPoser;
    /**
     * This attribute is an object from the class Carte.
     * carteAPoser represents the reference card.
     * In the ShapeUp's rules you can place a card only next to another card.
     * That's why we qualify a move by this attribute.
     */
    private Carte carteDeReference;
    /**
     * This attribute is a an object from the enumeration Cardinaux. Thanks to this attribute
     * you know the location compared to the reference card.
     */
    private Cardinaux positionParRapportARef;
    /**
     * Gets carteAPoser
     * 
     * @return carteAPoser
     */

    public Carte getCarteAPoser() {
        return carteAPoser;
    }
    /**
     * 
     * @param carteAPoser
     * 
     * Sets carteAPoser
     */

    public void setCarteAPoser(Carte carteAPoser) {
        this.carteAPoser = carteAPoser;
    }
    /**
     * Gets carteDeReference
     * 
     * @return carteDeReference
     */

    public Carte getCarteDeReference() {
        return carteDeReference;
    }
    /**
     * 
     * @param carteDeReference
     * 
     * Sets carteDeReference
     */

    public void setCarteDeReference(Carte carteDeReference) {
        this.carteDeReference = carteDeReference;
    }
    /**
     * Gets positionParRapportARef
     * 
     * @return positionParRapportARef
     */



    public Cardinaux getPositionParRapportARef() {
        return positionParRapportARef;
    }
    /**
     * 
     * @param positionParRapportARef
     * 
     * Sets positionParRapportARef
     */

    public void setPositionParRapportARef(Cardinaux positionParRapportARef) {
        this.positionParRapportARef = positionParRapportARef;
    }
    /**
     * The method toString allows to display an objects Coup on the console.
     * 
     * @return a message which tell what a card put and where according to a reference card.
     */

    @Override
    public String toString() {
        return "placer :" + this.getCarteAPoser().affichageTapis() + " a " + this.getPositionParRapportARef() + " de "
                + this.getCarteDeReference().affichageTapis();
    }
    /**
     * Instantiates a new object Coup.
     *  
     * @param carteAPoser
     * @param carteDeReference
     * @param positionParRapportARef
     */

    public Coup(Carte carteAPoser, Carte carteDeReference, Cardinaux positionParRapportARef) {
        this.carteAPoser = carteAPoser;
        this.carteDeReference = carteDeReference;
        this.positionParRapportARef = positionParRapportARef;
    }
}
