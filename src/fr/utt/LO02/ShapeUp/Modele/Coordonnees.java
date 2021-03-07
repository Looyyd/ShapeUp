package fr.utt.LO02.ShapeUp.Modele;

/**
 * This class allows you to instantiate objects
 * that list the coordinates of a move or a care on the carpet
 * 
 * @author petit
 *
 */
public class Coordonnees {
	/**
	 * This attribute represents the line of the carpet
	 */
    private int ligne;
    /**
	 * This attribute represents the column of the carpet
	 */
    private int colonne;
    /**
     * Gets line
     * 
     * @return the line
     */
    public int getLigne() {
        return ligne;
    }
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
    /**
     * Gets column
     * 
     * @return the column
     */
    public int getColonne() {
        return colonne;
    }
    /**
     * 
     * @param colonne
     * 
     * Sets column
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }
    /**
     * 
     * @param ligne
     * @param colonne
     * 
     * Instantiates a new object Coordonnees
     */
    public Coordonnees(int ligne, int colonne) {

        this.ligne = ligne;
        this.colonne = colonne;
    }


}
