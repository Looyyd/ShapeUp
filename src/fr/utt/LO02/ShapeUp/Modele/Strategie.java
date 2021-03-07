package fr.utt.LO02.ShapeUp.Modele;
/**
 * This interface implement method to execute a turn
 * @author petit
 *
 */
public interface Strategie {
    void effectuerTour(Tapis tapis, Carte cartePioche, Joueur joueur);
}
