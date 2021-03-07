package fr.utt.LO02.ShapeUp.Modele;
import java.util.ArrayList;

public class StrategieAvecMouveument implements Strategie{
	
    private ArrayList<Coup> coupsLegaux ;
    private ArrayList<Coup> mouvementsLegaux;
    private ArrayList<Cardinaux> decalagesMouvement;
    private ArrayList<Cardinaux> decalagesCoups;
    Coup meilleurCoup;
    Coup meilleurMouvement;
    Cardinaux decalageMeilleurCoup;
    Cardinaux decalageMeilleurMouvement;
    Integer i;
    Integer scoreMax,imax,jmax;
    Integer score;
    Carte carteABouger;

    /**
     * execute a full turn. with this strategy the Ia will always first move a card the place a card. The best move is
     * chosen by calculating all possibilities and keeping the one that increments the IA's score the most
     * @see Tapis for score
     * @param tapis
     * @param cartePioche
     * @param joueur
     */
    @Override
    public void effectuerTour(Tapis tapis, Carte cartePioche, Joueur joueur) {
        scoreMax = -10;//on initialise un score negatif
        int i, j,k ,l;
        Carte [][] cases = tapis.getCases();//passé par reference
        if(tapis.getNombreDeCartesPoses()==1){//si qu'une carte posé on ne peut pas la bouger donc on va juste placer une carte
            coupsLegaux= tapis.genererPlacementsLegaux(cartePioche);
            tapis.effectuerCoup(coupsLegaux.get(0),tapis.getDecalageAFaire().get(0));
            System.out.println("Le joueur " + joueur.getPseudo() + " a effectuer le coup : " +coupsLegaux.get(0).toString());
        }
        else {
            for (i = 0; i < cases.length; i++) {
                for (j = 0; j < cases[i].length; j++) {
                    if (cases[i][j] != null) {//une carte se situe sur cette case
                        carteABouger = cases[i][j];
                        //tapis.setCase(i,j,null);
                        cases[i][j] = null;
                        mouvementsLegaux = tapis.genererPlacementsLegaux(carteABouger);
                        mouvementsLegaux = new ArrayList<Coup>(mouvementsLegaux);
                        		//(ArrayList<Coup>) mouvementsLegaux.clone(); //removed because warning unchecked cast
                        //TRES IMPORTANT CAR SINON C
                        // A PASSE LA REFERENCE DE L'array QUI EST DANS TAPIS QUI VA ETRE MODIFIE A LA PROCHAINE ERAPE
                        //J'ai perdu une heure dessus lol
                        decalagesMouvement = tapis.getDecalageAFaire();
                        decalagesMouvement = new ArrayList<Cardinaux>(decalagesMouvement); //clone it 
                        for (k = 0; k < mouvementsLegaux.size(); k++) {//pour chaque mouvement
                            tapis.effectuerCoup(mouvementsLegaux.get(k), decalagesMouvement.get(k));//essayer de deplacer sure ce spot

                            //maintenant tous les meilleurs coups avec ce mouvement
                            coupsLegaux = tapis.genererPlacementsLegaux(cartePioche);
                            decalagesCoups = tapis.getDecalageAFaire();
                            for (l = 0; l < coupsLegaux.size(); l++) {
                                tapis.effectuerCoup(coupsLegaux.get(l), decalagesCoups.get(l));
                                score = tapis.score(joueur.getVictoryCarte());
                                if (score > scoreMax) {//si cette combinaison de mouvements puis coup est meilleur que les precedents
                                    scoreMax = score;
                                    imax = i;//donne les coordonnées de la carte a bouger
                                    jmax = j;
                                    //donnees du meilleur mouvement
                                    meilleurMouvement = mouvementsLegaux.get(k);
                                    decalageMeilleurMouvement = decalagesMouvement.get(k);
                                    //donnees du meilleur coup
                                    decalageMeilleurCoup = decalagesCoups.get(l);
                                    meilleurCoup = coupsLegaux.get(l);
                                }
                                tapis.annulerCoup(coupsLegaux.get(l), decalagesCoups.get(l));
                            }
                            //annuler le deplacement

                            tapis.annulerCoup(mouvementsLegaux.get(k), decalagesMouvement.get(k));
                        }

                        //Après avoir testé toutes les possibilités on remet la carte a sa place d'origine
                        //tapis.setCase(i,j,carteABouger);
                        cases[i][j] = carteABouger;
                    }
                }
            }
            //Après avoir testé toutes les possibilités on effectue le meilleur mouvement puis coup
            cases[imax][jmax] = null;
            System.out.println("Le joueur " + joueur.getPseudo() + " a bougé une carte : " + meilleurMouvement.toString());
            tapis.effectuerCoup(meilleurMouvement, decalageMeilleurMouvement);
            System.out.println("Le joueur " + joueur.getPseudo() + " a effectuer le coup : " + meilleurCoup.toString());
            tapis.effectuerCoup(meilleurCoup, decalageMeilleurCoup);//on applique le coup i
        }
    }
}
