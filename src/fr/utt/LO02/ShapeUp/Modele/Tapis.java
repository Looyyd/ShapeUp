package fr.utt.LO02.ShapeUp.Modele;
import java.util.*;

public class Tapis extends Observable{
	/**
	 * Returns the attibute cases
	 * @see #cases
	 * @return cases
	 */
	public Carte[][] getCases() {
		return cases;
	}

	/**
	 * This 2 dimensional array keeps track of the position of the already placed cards
	 */
	private  Carte[][] cases ;
	private Integer i, j;

	/**
	 * @see #decalageAFaire
	 * @return decalageAfaire
	 */
	public ArrayList<Cardinaux> getDecalageAFaire() {
		return decalageAFaire;
	}

	/**
	 * Keeps track of the shifts of cases necessary to execute the corresponding move. A shift will be necessary if the
	 * move would be out of bounds. For exemple if you want to place a card above a card that is on the first row of cases
	 * @see #cases
	 * @see #coupsLegaux
	 * @see #genererPlacementsLegaux(Carte)
	 * @see Cardinaux
	 * @see Carte
	 */
	private final ArrayList<Cardinaux> decalageAFaire = new ArrayList<Cardinaux>();
	/**
	 * Contains the newest generated possible moves
	 * @see #genererPlacementsLegaux(Carte)
	 */
	private final ArrayList<Coup> coupsLegaux = new ArrayList<Coup>();

	/**
	 * @see #carteTapis
	 * @return carteTapis
	 */
	public ArrayList<Carte> getCarteTapis() {
		return carteTapis;
	}

	/**
	 * Strores all the already placed cards
	 */
	private final ArrayList<Carte> carteTapis= new ArrayList<>();

	/**
	 * Return the chosen shape of the board, aka the way the cards can be arranged
	 * @see #formeARespecter
	 * @return
	 */
	public Boolean[][] getFormeARespecter() {
		return formeARespecter;
	}

	/**
	 *The chosen shape of the board, aka the way the cards can be arranged
	 * It is a 2D boolean array where a true represents a possible placement and a false represents an impossible
	 * placement
	 * @see #formeTriangle
	 * @see #formeRectangle
	 */
	private Boolean[][] formeARespecter ;
	/**
	 * An array that will contain a triangular possible shape
	 * @see #formeARespecter
	 */
	private final Boolean[][] formeTriangle = new Boolean[5][5];
	/**
	 *An arrat that will contain a rectangular possible shape
	 * @see #formeARespecter
	 */
	private final Boolean[][] formeRectangle = new Boolean[3][5];

	/**
	 * Lists the possible card board shapes
	 * @see #formeARespecter
	 */
	private ArrayList<Boolean[][]> listesFormes = new ArrayList<Boolean[][]>();

	/**
	 * Return the possible board shapes
	 * @see #listesFormes
	 * @return listeFormes
	 */
	public ArrayList<Boolean[][]> getListesFormes() {
		return listesFormes;
	}

	/**
	*Returns the number of arlready placed cards
	 * @see #nombreDeCartesPoses
	 */
	public int getNombreDeCartesPoses() {
		return nombreDeCartesPoses;
	}


	/**
	*The number of already placed cards
	 */
	private int nombreDeCartesPoses = 0;//ATTENTION A RESET A 0 SI ON REFAIT UNE PARTIE

	/**
	 * Contains all the logic et variables related to the placement of cards
	 */
	public Tapis() {

		creerFormeARespecter();
		 menuFormesTapis();

	}

	/**
	*Prints the options of the different possible card placement configurations
	 */
	public void menuFormesTapis(){
		System.out.println("Quel type de tapis souhaitez vous ?");
		System.out.println("Les . representes les emplacements de carte");
		Iterator<Boolean[][]> iterator = listesFormes.iterator();
		int choix;
		int i=-1;
		while(iterator.hasNext()){
			i++;
			System.out.println("Le tapis "+i+"):");
			afficherForme(iterator.next());
		}

	}

	public void setCases(Carte[][] cases) {
		this.cases = cases;
	}

	public void setFormeARespecter(Boolean[][] formeARespecter) {
		this.formeARespecter = formeARespecter;
	}

	/**
	*Prints a single card placement configuration where dots represent possible placements and the rest are X'es
	 * for padding
	 */
	public void afficherForme(Boolean[][] formeAAfficher){
		int i,j;
		for (i = 0; i < formeAAfficher.length; i++) {
			for (j = 0; j < formeAAfficher[i].length; j++) {
				if(formeAAfficher[i][j]==true){
					System.out.print(".");
				}
				else{
					System.out.print("X");
				}
			}
			System.out.println("");//prochaine ligne
		}
	}

	/**
	* Returns true if there is no more possible moves or false otherwise
	 */
	public Boolean tapisEstRemplie(){
		ArrayList<Coup> list = genererPlacementsLegaux(null);//si aucun placement legal alors le tapis est remplie
		if(list.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Makes the possible board shapes	
	 * @see #formeARespecter
	 */
	public void creerFormeARespecter() {
		//RECTANGLE
		int i,j;
		for (i = 0; i < formeRectangle.length; i++) {
			for (j = 0; j < formeRectangle[i].length; j++) {
				formeRectangle[i][j] = true;// pas besoin de new Boolean(true)
			}
		}
		listesFormes.add(formeRectangle);
		//TRIANGLE
		for(i=0;i<formeTriangle.length;i++){
			for(j=0;j<formeTriangle[i].length;j++){
				if(i==4 || i==3){
					formeTriangle[i][j]=true;
				}
				else if(j==2){
					formeTriangle[i][j]=true;
				}
				else if (j==1 && i==2){
					formeTriangle[i][j]=true;
				}
				else if(j==3 && i==2){
					formeTriangle[i][j]=true;
				}
				else{
					formeTriangle[i][j]=false;
				}
			}
		}
		listesFormes.add(formeTriangle);
	}


	/**
	 * Takes as input the number of cards with the same color encountered in a row
	 * Returns the corresponding score
	 * @see #score(Carte) 
	 * @param compteur 
	 * @return score to add
 	 */
	private Integer scoreCouleur(Integer compteur) {
		if (compteur >= 3) {
			return compteur + 1;
		} else {
			return 0;
		}
	}
	/**
	 * Takes as input the number of cards with the same shape encountered in a row
	 * Returns the corresponding score
	 * @see #score(Carte)
	 * @param compteur
	 * @return score to add
	 */
	private Integer scoreForme(Integer compteur) {
		if (compteur >= 2) {
			return compteur - 1;
		} else {
			return 0;
		}
	}

	/**
	 * Takes as input the number of cards with the same hollowness encountered in a row
	 * Returns the corresponding score
	 * @see #score(Carte)
	 * @param compteur
	 * @return score to add
	 */
	private Integer scoreRemplissage(Integer compteur) {
		if (compteur >= 3) {
			return compteur;
		} else {
			return 0;
		}
	}

	/**
	 * Calculates the score you would get with a particular victory card
	 *
	 * When enough cards with a identical element(shape,color or hollowness) the correcponding function is called 
	 * to calculate how much score needs to be added
	 * @see #scoreCouleur(Integer) 
	 * @see #scoreForme(Integer) 
	 * @see #scoreRemplissage(Integer) 
	 * 
	 * @param carteVictoire
	 * @return the score you would get with that victory card
	 */
	public int score(Carte carteVictoire) {
		Integer i, j;
		Integer compteurForme, compteurCouleur, compteurRemplissage;
		Integer score = 0;

		// parcours des lignes
		for (i = 0; i < cases.length; i++) {
			// on remet les compteurs a 0 a chaque ligne
			compteurCouleur = 0;
			compteurForme = 0;
			compteurRemplissage = 0;
			for (j = 0; j < cases[i].length; j++) {
				if (cases[i][j] == null) {// on rencontre un emplassement vide
					score += scoreCouleur(compteurCouleur);
					score += scoreForme(compteurForme);
					score += scoreRemplissage(compteurRemplissage);
					compteurCouleur = 0;
					compteurForme = 0;
					compteurRemplissage = 0;
				} else {// carte presente sur cette case
						// si Froem/Couleur/Remplissage identique on ajoute au compteur sinon on ajoute
						// le score correspondant au compteur
					if (cases[i][j].getCouleur() == carteVictoire.getCouleur()) {
						compteurCouleur++; // si couleur identique on ajout au compteur
					} else {
						score += scoreCouleur(compteurCouleur);
						compteurCouleur = 0;
					}
					if (cases[i][j].getForme() == carteVictoire.getForme()) {
						compteurForme++; // si Forme identique on ajout au compteur
					} else {
						score += scoreForme(compteurForme);
						compteurForme = 0;
					}
					if (cases[i][j].getRemplissage() == carteVictoire.getRemplissage()) {
						compteurRemplissage++; // si Remplissage identique on ajout au compteur
					} else {
						score += scoreRemplissage(compteurRemplissage);
						compteurRemplissage = 0;
					}
				}
			}
			// On ajoute les compteurs a la fin
			score += scoreCouleur(compteurCouleur);
			score += scoreForme(compteurForme);
			score += scoreRemplissage(compteurRemplissage);
		}

		// parcours des colonnes
		for (j = 0; j < cases[1].length; j++) {
			// on remet les compteurs a 0 a chaque colonne
			compteurCouleur = 0;
			compteurForme = 0;
			compteurRemplissage = 0;
			for (i = 0; i < cases.length; i++) {
				if (cases[i][j] == null) {// on rencontre un emplassement vide
					score += scoreCouleur(compteurCouleur);
					score += scoreForme(compteurForme);
					score += scoreRemplissage(compteurRemplissage);
					compteurCouleur = 0;
					compteurForme = 0;
					compteurRemplissage = 0;
				} else {// carte presente sur cette case
						// si Froem/Couleur/Remplissage identique on ajoute au compteur sinon on ajoute
						// le score correspondant au compteur
					if (cases[i][j].getCouleur() == carteVictoire.getCouleur()) {
						compteurCouleur++; // si couleur identique on ajout au compteur
					} else {
						score += scoreCouleur(compteurCouleur);
						compteurCouleur = 0;
					}
					if (cases[i][j].getForme() == carteVictoire.getForme()) {
						compteurForme++; // si Forme identique on ajout au compteur
					} else {
						score += scoreForme(compteurForme);
						compteurForme = 0;
					}
					if (cases[i][j].getRemplissage() == carteVictoire.getRemplissage()) {
						compteurRemplissage++; // si Remplissage identique on ajout au compteur
					} else {
						score += scoreRemplissage(compteurRemplissage);
						compteurRemplissage = 0;
					}
				}
			}
			// On ajoute les compteurs a la fin
			score += scoreCouleur(compteurCouleur);
			score += scoreForme(compteurForme);
			score += scoreRemplissage(compteurRemplissage);
		}

		return score;
	}

	/**
	 * Executes a move , also needs the corresponding shift as a Cardinaux
	 * @see Coup#Coup(Carte, Carte, Cardinaux) 
	 * @see Cardinaux
	 * @see #decalageAFaire
	 * @param coup
	 * @param decalage
	 */
	public void effectuerCoup(Coup coup, Cardinaux decalage) {

		if (decalage == Cardinaux.DROITE) {
			decalerADroite();
		}
		else if (decalage == Cardinaux.GAUCHE) {
			decalerAGauche();
		}
		else if (decalage == Cardinaux.BAS) {
			decalerEnBas();
		}
		else if (decalage == Cardinaux.HAUT) {
			decalerEnHaut();
		}
		Coordonnees carteRef = positionCarteDeRef(coup);

		if (coup.getPositionParRapportARef() == Cardinaux.DROITE) {
			placerCarte(coup.getCarteAPoser(), carteRef.getLigne(), carteRef.getColonne() + 1);
		}
		else if (coup.getPositionParRapportARef() == Cardinaux.GAUCHE) {
			placerCarte(coup.getCarteAPoser(), carteRef.getLigne(), carteRef.getColonne() - 1);
		}
		else if (coup.getPositionParRapportARef() == Cardinaux.BAS) {
			placerCarte(coup.getCarteAPoser(), carteRef.getLigne() + 1, carteRef.getColonne());
		}
		else if (coup.getPositionParRapportARef() == Cardinaux.HAUT) {
			placerCarte(coup.getCarteAPoser(), carteRef.getLigne() - 1, carteRef.getColonne());
		}

	}

	/**
	 * Cancels a move taking the same parameters that were given to execute it. Is used to try different possibilities 
	 * when the IA's are calculating the optimal moves
	 * 
	 * @see Strategie#effectuerTour(Tapis, Carte, Joueur) 
	 * @see #effectuerCoup(Coup, Cardinaux) 
	 * @param coup
	 * @param decalage
	 */
	public void annulerCoup(Coup coup, Cardinaux decalage) {
		Coordonnees carteRef = positionCarteDeRef(coup);
		if (coup.getPositionParRapportARef() == Cardinaux.DROITE) {
			placerCarte(null, carteRef.getLigne(), carteRef.getColonne() + 1);
		}
		else if (coup.getPositionParRapportARef() == Cardinaux.GAUCHE) {
			placerCarte(null, carteRef.getLigne(), carteRef.getColonne() - 1);
		}
		else if (coup.getPositionParRapportARef() == Cardinaux.BAS) {
			placerCarte(null, carteRef.getLigne() + 1, carteRef.getColonne());
		}
		else if (coup.getPositionParRapportARef() == Cardinaux.HAUT) {
			placerCarte(null, carteRef.getLigne() - 1, carteRef.getColonne());
		}
		nombreDeCartesPoses-=2;//2 cartes posés en moins car placerCarte(null)augemente le nombre de cartes poses
		if (decalage == Cardinaux.DROITE) {
			decalerAGauche();
		}
		else if (decalage == Cardinaux.GAUCHE) {
			decalerADroite();
		}
		else if (decalage == Cardinaux.BAS) {
			decalerEnHaut();
		}
		else if (decalage == Cardinaux.HAUT) {
			decalerEnBas();
		}

	}

	
	/**
	* Executes the ith move from the last calculated moves,with i as a parameter
	 * @see #effectuerCoup(Coup, Cardinaux)
	 */
	public void effectuerChoixI(int choix){
		effectuerCoup(coupsLegaux.get(choix), decalageAFaire.get(choix));
	}

	/**
	 * Lists the different cards that can be move, this is the same as listing all the already placed cards
	 * @return the number of cards listed
	 */
	public int menuCarteADeplacer() {
		carteTapis.clear();
		int i, j;
		for (i = 0; i < cases.length; i++) {
			for (j = 0; j < cases[i].length; j++) {
				if (cases[i][j] != null) {
					carteTapis.add(cases[i][j]);
				}
			}
		}

		for (i = 0; i < carteTapis.size(); i++) {
			System.out.println(i+") " + carteTapis.get(i));
		}
		return i-1;//return le i max
	}

	/**
	 * Lists the possible moves given an arrayList of possible moves
	 * @see #effectuerCoup(Coup, Cardinaux)
	 * @see Coup
	 * @see #genererPlacementsLegaux(Carte)
	 * @param coupsLegaux
	 * @return
	 */
	public int menuDesCoups(ArrayList<Coup> coupsLegaux) {
		ListIterator<Coup> iteretor = coupsLegaux.listIterator();
		Coup coupAAfficher = null;
		Integer index =-1;//return -1 si vide sinon return le numero max
		while (iteretor.hasNext()) {
			index = iteretor.nextIndex();
			coupAAfficher = iteretor.next();
			System.out.println(index + ") placer :" + coupAAfficher.getCarteAPoser().affichageTapis() + " a "
					+ coupAAfficher.getPositionParRapportARef() + " de "
					+ coupAAfficher.getCarteDeReference().affichageTapis());
		}
		setChanged();
		notifyObservers(coupsLegaux);
		return index;
	}

	/**
	 * Takes as parameter an already placed card to be moved and removes it from the board then calls menuDesCoups with
	 * the possible placements of the chosen cards
	 * @see #menuDesCoups(ArrayList)
	 * @see #genererPlacementsLegaux(Carte)
	 * @param carteADeplacer
	 * @return
	 */
	public int choixCarteADeplacer(Carte carteADeplacer) {//choisir la carte a deplacer après affichage du menu
		//J'ai enlevé le scan de ces foncitons par rapport a avant pour que les scans soit dans la view text au final
			for (int i = 0; i < cases.length; i++) {
				for (int j = 0; j < cases[i].length; j++) {
					if (cases[i][j]==carteADeplacer) {
					this.cases[i][j]=null;
					
				
			}
		}
		
		}
		return menuDesCoups(genererPlacementsLegaux(carteADeplacer));
		
	}

	/**
	 * Gives the row and column indices of the reference card given a coup
	 * @see Coup
	 * @see Coordonnees
	 * @param coup
	 * @return position as Coordonnes
	 */
	public Coordonnees positionCarteDeRef(Coup coup) {// R�cup�re les coordonnees de la carte de Ref�rence
		Integer i=0 , j=0 ;
		Coordonnees position = new Coordonnees(i, j);
		for (i = 0; i < cases.length; i++) {
			for (j = 0; j < cases[i].length; j++) {// on parcourt toutes les cases
				if (cases[i][j] == coup.getCarteDeReference()) {
					position.setLigne(i);
					position.setColonne(j);
					return position;
				}
			}
		}
		//si pas rencontrée la carte
		return null;
	}

	/**
	 * Takes as input the coordinates in cases of a potential square and returns the indice i of the corresponding coupLegauxin  if it i
	 * s a valide placement or -1 if it isn't. Since you can move next to a card that is on the edge of cases(with a shift needed however)
	 * the inputs can go from i = [-1 cases.length] and same for j
	 * @see #coupsLegaux
	 * @param i
	 * @param j
	 * @return
	 */
	public int positionCaseToCoup(int i, int j){
		/*
		*Prend en input les coordonnés d'une "case" vide
		* case entre guillement car peut prendre -1 pour i,j ou la valeur max pour les autres
		* retourne le coup correspondant si cette position est valide dans l'un des coups legaux calculés
		* retourn -1 sinon
		*
		 */
		Coup coup;
		Coordonnees coord;
		int offsetI, offsetJ;

		for(int l=0; l<coupsLegaux.size();l++){
			coup = coupsLegaux.get(l);
			coord= positionCarteDeRef(coup);
			if(coup.getPositionParRapportARef()==Cardinaux.BAS){
				offsetI=-1;
				offsetJ=0;
				if(coord.getColonne()==j+offsetJ && coord.getLigne()==i+offsetI){
					return l;
				}
			}
			else if(coup.getPositionParRapportARef()==Cardinaux.HAUT){
				offsetI=+1;
				offsetJ=0;
				if(coord.getColonne()==j+offsetJ && coord.getLigne()==i+offsetI){
					return l;
				}
			}
			else if(coup.getPositionParRapportARef()==Cardinaux.DROITE){
				offsetI=0;
				offsetJ=-1;
				if(coord.getColonne()==j+offsetJ && coord.getLigne()==i+offsetI){
					return l;
				}
			}
			else if(coup.getPositionParRapportARef()==Cardinaux.GAUCHE){
				offsetI=0;
				offsetJ=+1;
				if(coord.getColonne()==j+offsetJ && coord.getLigne()==i+offsetI){
					return l;
				}
			}
		}


		return -1;
	}

	/**
	* Placed a card at the given corrdinates used by effectuerCoup
	 * @see #effectuerCoup(Coup, Cardinaux)
	 */
	public void placerCarte(Carte carte, Integer ligne, Integer colonne) {
		cases[ligne][colonne] = carte;
		nombreDeCartesPoses++;
	}

	// j'ai fait de fonctions decaler horizontalement et verticalement au debut car
	// on avait mit ca dans le diagramme mais finalement decaler ADroite AGauche
	// etc.. ca parait plus logique comme noms

	/**
	 * Shifts the cases array horizontally
	 * @param aDroite if =true then shift right else shift left
	 */
	private void decalerTapisHorizontalement(boolean aDroite) {
		// a finir
		if (aDroite) {
			for (i = 0; i < cases.length; i++) {
				for (j = cases[i].length - 2/* On commence a l'avant derniere colonne */; j >= 0; j--) { // on parcourt
																											// de droite
																											// a gauche
					cases[i][j + 1] = cases[i][j];
				}
			}
			for (i = 0; i < cases.length; i++) {
				cases[i][0] = null;// premiere colonne null
			}
		} else {
			for (i = 0; i < cases.length; i++) {
				for (j = 1/* On commence a l'avant premiere colonne donc la deuxieme */; j < cases[i].length; j++) { // on
																														// parcourt
																														// de
																														// droite
																														// a
																														// gauche
					cases[i][j - 1] = cases[i][j];
				}
			}
			for (i = 0; i < cases.length; i++) {
				cases[i][cases[i].length - 1] = null;// derniere colonne null
			}
		}
	}

	/**
	 * Shifts cases to the right
	 * @see #cases
	 */
	public void decalerADroite() {
		decalerTapisHorizontalement(true);
	}

	/**
	 * Shifts cases to the left by one
	 * @see #cases
	 */
	public void decalerAGauche() {
		decalerTapisHorizontalement(false);
	}

	/**
	 * Shift cases vertically by one
	 * @param enHaut if true shifts up else shift down
	 */
	private void decalerTapisVerticalement(boolean enHaut) {
		if (enHaut) {
			for (i = 1; i <= cases.length - 1; i++) { // on parcourt de haut en bas
				for (j = 0; j < cases[i].length; j++) {
					cases[i - 1][j] = cases[i][j];
				}
			}
			for (j = 0; j < cases[cases.length - 1].length; j++) {
				cases[cases.length - 1][j] = null;// derniere ligne null
			}
		} else {
			for (i = cases.length - 2/* avant derniere ligne */; i >= 0; i--) { // on parcourt de bas en haut
				for (j = 0; j < cases[i].length; j++) {
					cases[i + 1][j] = cases[i][j];
				}
			}
			for (j = 0; j < cases[0].length; j++) {
				cases[0][j] = null;// derniere ligne null
			}
		}
	}

	/**
	 * Shifts cases up by one
	 * @see #cases
	 */
	public void decalerEnHaut() {
		decalerTapisVerticalement(true);
	}

	/**
	 * Shifts cases down by one
	 * @see #cases
	 */
	public void decalerEnBas() {
		decalerTapisVerticalement(false);
	}

	/**
	 * Checks if a colum in cases is empty used in the move generetor to see if a shift is possible
	 * @param nColonne the column index
	 * @return true if empty
	 */
	public Boolean colonneVide(Integer nColonne) {
		Integer i;// j'avais oublié de mettre ca avant, verifier si je n'ai pas oublié dans
					// d'autre fonctions
		for (i = 0; i < cases.length; i++) {
			if (cases[i][nColonne] != null) {
				return false;
			}
		}
		return true;// si boucle passé sans rencontrer de carte
	}

	/**
	 * Checks if a row in cases is empty used in the move generetor to see if a shift is possible
	 * @param nLigne the row index
	 * @return true if empty
	 */
	public Boolean ligneVide(Integer nLigne) {
		Integer j;
		for (j = 0; j < cases[nLigne].length; j++) {
			if (cases[nLigne][j] != null) {
				return false;
			}
		}
		return true;// si boucle passé sans rencontrer de cartes
	}

	/**
	 * Prints the cases array to show how the cards are placed also notifies observers
	 */
	public void afficherTapis() {
		
		for (i = 0; i < cases.length; i++) {
			for (j = 0; j < cases[i].length; j++) {
				if (cases[i][j] == null) {
					System.out.print("XX ");
				} else {
					System.out.print(cases[i][j].affichageTapis() + " ");
				}
			}
			System.out.println();
		}
		setChangedAndNotify();
		//La ou en ligne de commande on affichait le tapis, en gui on update l'interface
		
	}

	/**
	 * sets the class as changed and notifies observers
	 */
	public void setChangedAndNotify(){

		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Creates an 2 dimensional Boolean array where A[i,j]==true if and only if cases[i,j]!=null (contains a Card)
	 * @see #mapRespecteForme(Boolean[][], Boolean[][])
	 * @return A
	 */
	public Boolean[][] casesToMap() {// mettre en private après test
		Boolean[][] map = new Boolean[cases.length][cases[0].length];
		int i,j;
		for (i = 0; i < cases.length; i++) {
			for (j = 0; j < cases[i].length; j++) {
				map[i][j] = cases[i][j] != null;
			}
		}
		return map;
	}

	/**
	 * Checks if a boolean map 	is valid given a shape that it has to fit in
	 * Tries bruteforce every possibilities until it finds a way to fit into the shape or exhausts all possibilities
	 * @see #formeARespecter
	 * @see #genererPlacementsLegaux(Carte)
	 * @param map the map
	 * @param formeARespecter the shape
	 * @return
	 */
	public Boolean mapRespecteForme(Boolean[][] map, Boolean[][] formeARespecter) {// pourra etre private apres tests
		/*
		 * Cette fonction verifiera si la formation de cartes envisagé(map) respecte la
		 * formation a respecter(formeARespecter) Cela permettra de verifier si le coup
		 * consideré est valide L'utilisation de la formeARespecter permet de rester
		 * modulaire sur la forme
		 *
		 * La fonction cherche une carte sur la map, Si cette map respecte la forme
		 * alors cette carte doit correspondre a au moins un point de la forme, On va
		 * tester pour tout les points de la forme, si en ayant la carte a la meme
		 * position que ce point on a tout les autres cartes a des points valides il
		 * suffit d'une seule solution valide pour que la map respecte la forme
		 */
		Integer i, j, k, l;
		Integer i_test = 0, j_test = 0, offset_i, offset_j;// les coordonnées de la cart testée
		Boolean nonVide = false;
		Boolean pointDansForme, respecteForme;

		// Recherche d'une carte
		for (i = 0; i < map.length; i++) {
			for (j = 0; j < map[i].length; j++) {
				if (map[i][j] == true) {
					nonVide = true;
					i_test = i;
					j_test = j;
					break;// le break renvoi que de la premiere loop donc en fait ca va donner la premiere
							// carte de la derniere ligne goto existe pas en java donc on pourrait creer une
							// fonction qui retourne que la place de la preiere carte
				}
			}
		}

		if (nonVide == false) {// si aucune carte placé ca ne peut que respecter la forme
			return true;
		} else {
			// on parcourt la forme a respecter pour tester si la carte peut correspondre a
			// au moins un point
			for (i = 0; i < formeARespecter.length; i++) {
				for (j = 0; j < formeARespecter[i].length; j++) {
					if (formeARespecter[i][j] == true) {// la carte pourrait etre ce point
						offset_i = i_test - i;
						offset_j = j_test - j;// offset = icarte-iforme

						// maintenant on parcour toutes les cartes posés pour voir si elles respectent
						// la forme
						respecteForme = true;
						for (k = 0; k < map.length; k++) {
							for (l = 0; l < map[k].length; l++) {
								if (map[k][l] == true) {
									try {
										pointDansForme = (formeARespecter[k - offset_i][l - offset_j] == true);
									} catch (ArrayIndexOutOfBoundsException e) {// si out of bounds alors faux
										pointDansForme = false;
									}

									if (!pointDansForme) {
										respecteForme = false;// suffit qu'un point ne soit pas dans la forme pour que
																// ca soit faut
									}
								}
							}
						}
						if (respecteForme) { // si au moins une possiblité foncitonne alors ca respecte la forme
							return true;
						}
					}
				}
			}
		}
		return false;// on a pas trouvé de forme avant donc on return false

	}

	/**
	 * generates the given legal the genereted moves are stored in coupsLegaux and the shifts in decalageAFaire
	 * @see #coupsLegaux
	 * @see #decalageAFaire
	 * @see Coup
	 * @see #formeARespecter
	 * @param carteAPlacer car to be placed
	 * @return
	 */
	public ArrayList<Coup> genererPlacementsLegaux(Carte carteAPlacer) {
		HashSet<String> doublon = new HashSet<String>();
		coupsLegaux.clear();
		decalageAFaire.clear();
		Boolean[][] mapEnvisage ;
		Integer i, j;
		for (i = 0; i < cases.length; i++) {
			for (j = 0; j < cases[i].length; j++) {// on parcourt toutes les cases
				if (cases[i][j] == null) {
					continue;
				} else {// une carte est presente sur cette case
						// Peut on placer une carte a gauche?
					if (j == 0) {// si tout a gauche(premiere colonne)
						if (colonneVide(cases[0].length - 1)) {
							decalerADroite();// on Suppose que l'on decale a droite
							mapEnvisage=casesToMap();
							mapEnvisage[i][j] = true;// supposons que l'on place la carte (pas de -1 car cartetapis
														// decalé
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté

								if (doublon.add(String.valueOf(i) + (j -1))) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i][j + 1], Cardinaux.GAUCHE));
									//+1 a cause du décalage
									decalageAFaire.add(Cardinaux.DROITE);
								}

							}
							decalerAGauche();
						}
					} else {
						if (cases[i][j - 1] == null) {// emplacement vide a gauche
							mapEnvisage=casesToMap();
							mapEnvisage[i][j - 1] = true;// supposons que l'on place la carte
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								if (doublon.add(String.valueOf(i) + (j - 1))) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i][j], Cardinaux.GAUCHE));
									decalageAFaire.add(Cardinaux.NULL);
								}

							}
						}
					}
					// Peut on placer une carte a droite?
					if (j == cases[i].length - 1) {// si tout a droite(derniere colonne)
						if (colonneVide(0)) {
							decalerAGauche();// on Suppose que l'on decale a droite
							mapEnvisage=casesToMap();
							mapEnvisage[i][j] = true;// supposons que l'on place la carte
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								if (doublon.add(String.valueOf(i) + (j + 1))) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i][j - 1], Cardinaux.DROITE));
									decalageAFaire.add(Cardinaux.GAUCHE);
								}
							}
							decalerADroite();
						}
					} else {
						if (cases[i][j + 1] == null) {// emplacement vide a droite
							mapEnvisage=casesToMap();
							mapEnvisage[i][j + 1] = true;// supposons que l'on place la carte
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								if (doublon.add(String.valueOf(i) + (j + 1))) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i][j], Cardinaux.DROITE));
									decalageAFaire.add(Cardinaux.NULL);
								}

							}
						}
					}
					// Peut on placer une carte en haut?
					if (i == 0) {// si tout en haut(premiere ligne)
						if (ligneVide(cases.length - 1)) {// derniere ligne vide?
							decalerEnBas();// on Suppose que l'on decale a droite
							mapEnvisage=casesToMap();
							mapEnvisage[i][j] = true;// supposons que l'on place la carte (pas de -1 car cartetapis
														// decalé
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								// si oui on rajoute le coup
								if (doublon.add(String.valueOf(i -1) + j)) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i + 1][j], Cardinaux.HAUT));
									decalageAFaire.add(Cardinaux.BAS);
								}

							}
							decalerEnHaut();
						}
					} else {
						if (cases[i - 1][j] == null) {// emplacement vide en haut
							mapEnvisage=casesToMap();
							mapEnvisage[i - 1][j] = true;// supposons que l'on place la carte
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								if (doublon.add(String.valueOf(i - 1) + j)) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i][j], Cardinaux.HAUT));
									decalageAFaire.add(Cardinaux.NULL);
								}

							}
						}
					}
					// Peut on placer en bas?
					if (i == cases.length - 1) {// si tout en bas(derniere ligne)
						if (ligneVide(0)) {
							decalerEnHaut();// on Suppose que l'on edcale en haut
							mapEnvisage=casesToMap();
							mapEnvisage[i][j] = true;// supposons que l'on place la carte
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								if (doublon.add(String.valueOf(i + 1) + j)) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i - 1][j], Cardinaux.BAS));// en effet
																											// carte de
																											// ref
																											// au dessus
									decalageAFaire.add(Cardinaux.HAUT);
								}

							}
							decalerEnBas();
						}
					} else {
						if (cases[i + 1][j] == null) {// emplacement vide en dessous
							mapEnvisage=casesToMap();
							mapEnvisage[i + 1][j] = true;// supposons que l'on place la carte
							if (mapRespecteForme(mapEnvisage, formeARespecter)) {// la forme est elle respecté
								if (doublon.add(String.valueOf(i + 1) + j)) {
									coupsLegaux.add(new Coup(carteAPlacer, cases[i][j], Cardinaux.BAS));
									decalageAFaire.add(Cardinaux.NULL);
								}

							}
						}
					}

				}
			}
		}
		doublon.clear();
		
		return coupsLegaux;
	}

	public ArrayList<Coup> getCoupsLegaux() {
		return coupsLegaux;
	}
}