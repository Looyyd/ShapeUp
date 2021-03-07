package fr.utt.LO02.ShapeUp.Modele;
import java.util.ArrayList;
/*
*Le default de cette strategie c'est deja qu'elle ne deplace pas de carte et qu'en plus au debut elle fait le premier coup
* trouve car ce n'est qu'après que plusieurs cartes soit alignés que le score augemente
 */
public class StrategiePlacementSeulementAvecCalculScore implements Strategie{

    @Override
    public void effectuerTour(Tapis tapis, Carte cartePioche, Joueur joueur) {
        ArrayList<Coup> coupsLegaux ;
        Integer i;
        Integer scoreMax,imax = null;//uninitialized variable warning
        Integer score;
    
    	scoreMax = -10;//on initialise un score negatif
        coupsLegaux=tapis.genererPlacementsLegaux(cartePioche);

        for(i=0;i<coupsLegaux.size();i++){
            tapis.effectuerCoup(coupsLegaux.get(i),tapis.getDecalageAFaire().get(i));//on applique le coup i
            //on regarde si le coup i est meilleur que les coups testés precedemments
            score = tapis.score(joueur.getVictoryCarte());
            if(score>scoreMax){
                imax=i;
                scoreMax=score;
            }
            //on annule le coup i
            tapis.annulerCoup(coupsLegaux.get(i),tapis.getDecalageAFaire().get(i));
        }

        System.out.println("Le joueur " + joueur.getPseudo() + " a effectuer le coup : " + coupsLegaux.get(imax).toString());
        tapis.effectuerCoup(coupsLegaux.get(imax),tapis.getDecalageAFaire().get(imax));//on applique le coup i

    }
	@Override
	public String getNomDifficulté() {
		return "Intermediaire";
	}
}
