package fr.utt.LO02.ShapeUp.Modele;

import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Scanner;

import fr.utt.LO02.ShapeUp.Controleurs.ControleurTexte;
import fr.utt.LO02.ShapeUp.Vue.InterfaceGraphique;
import fr.utt.LO02.ShapeUp.Vue.InterfaceGraphiqueChoix;
import fr.utt.LO02.ShapeUp.Vue.VueLigneCommande;

/**
 * The class Partie manage the game. Only one object is created for each game.
 * This object allows to control all the different steps of a player round.
 * The controller can manage all the game thanks to an instantiate of Partie.
 * Partie extends from Observable and give the information about player, carpet, card ...)
 * 
 * @author petit
 *
 */
public class Partie extends Observable {

	private ArrayList<Strategie> listeStrategies = new ArrayList<Strategie>();

	private void initStrategies() {
		//essayer de la mettre dans l'ordre de difficulté
		listeStrategies.add(new StrategiePlacementSeulementAvecCalculScore());
		listeStrategies.add( new StrategieAvecMouveument());

	}
	
	public ArrayList<Strategie> getListStrategies() {
		return listeStrategies;
	}
	
/**
 * The attribute nbJoueur represents the number of real players in the game.
 */
	private int nbJoueur;
	/**
	 * The attribute typePartie represents the number of total players
	 */
	private int typePartie;
	/**
	 * This attribute conaintes all the player(class Joueur) created
	 */
	private Joueur[] joueurCree;
	/**
	 * This attribute is a Carte, it represents the first card draw at the beginning of a party that anybody can see.
	 */
	private Carte hiddenCarte;
	/**
	 * This attribute qualify the state of the party (Advanced or not).
	 */
	private boolean advanced = false;
	/**
	 * This attribute inform about the state of the party.
	 */
	public EtatDuTour etatTour = new EtatDuTour();
	
	Scanner scan = new Scanner(System.in);// creation du scanner pour la boucle
	/**
	 * This attribute design the number of moves available to place or move a card.
	 */
	public int nombreDechoix;
	/**
	 * It designates the difficulty of the AI. 0 is a low level, 1 is an high level.
	 * The difficulty of virtual player depend on its strategy an intermediate AI don't
	 * move a card whereas a difficult player can move card.
	 */
	private int difficulte;
	/**
	 * This attribute is an object of the Partie create.
	 */
	private Partie partie;
	/**
	 * This attribute inform about the tour number a tour number is increment when a player finishes his turn.
	 *  It allows to manage the first round which is different of the other because you can't move a card.
	 * Initially numeroTour is 0.
	 */
	public int numeroTour = 0;
	/**
	 * carteAPlacer is the card that you decided to place on the carpet.
	 */
	private Carte carteAPlacer;
	/**
	 * It's an ArrayList with the pseudo ofd the real player.
	 */
	private ArrayList<String> nomJoueurReel=new ArrayList<String>();
	/**
	 * This attribute is a constant which means the number of card in your hand in
	 * an advanced ShapeuP, here it's 3.
	 */
	public int nombreDeCartesEnMain = 3;
	/**
	 * These attributes are a constant and inform about the max and the min number of players
	 * in a ShapeUp's games. Here the min is 2 and the max is 3.
	 */
	public int minNombreDeJoueurs =2, maxNombredeJoueurs =3;//nombre de joueurs maximum
	/**
	 * This is a counter which count the number of real players ever named.
	 * It is initialized to 0.
	 */
	public int nombreJoueursNommes=0;
	/**
	 * Sets interfaceChoix
	 * 
	 * @param interfaceChoix
	 */

	public void setInterfaceChoix(InterfaceGraphiqueChoix interfaceChoix) {//utilisé par main
		this.interfaceChoix = interfaceChoix;
	}
	/**
	 * interfaceChoix is the interface where you inform the settings of the party.
	 */

	private InterfaceGraphiqueChoix interfaceChoix;
	/**
	 * interfaceTapis is the interface where you can interact with the carpet and the card drawed.
	 */
	private InterfaceGraphique interfaceTapis ;
	/**
	 * Gets carteAPlacer
	 * 
	 * @return carteAPlacer
	 */
	public Carte getCarteAPlacer() {
		return carteAPlacer;
	}
	/**
	 * Sets carteAPlacer
	 * 
	 * @param carteAPlacer
	 */

	public void setCarteAPlacer(Carte carteAPlacer) {
		this.carteAPlacer = carteAPlacer;
	}
	/**
	 * Gets tapis
	 * 
	 * @return tapis
	 */

	public Tapis getTapis() {
		return tapis;
	}
	/**
	 * This attribute designates the carpet of the game.
	 */

	private Tapis tapis;
	/**
	 * It's the card in game it represents the deck of the game.
	 */

	private JeuDeCarte jeu;
	/**
	 * This attribute designates the player of the round.
	 * It allows what a player is playing.
	 */
	private Joueur joueurDuTour;
	/**
	 * This attribute designates the card drawed during a normal version of ShapeUp.
	 */
	public Carte cartePioche;
	/**
	 * Gets advanced 
	 * 
	 * @return true when the party is advanced
	 */

	public boolean isAdvanced() {
		return advanced;
	}
	/**
	 * Sets advanced
	 * 
	 * @param advanced
	 */

	public void setAdvanced(boolean advanced) {
		this.advanced = advanced;
	}
	/**
	 * This method allows to an AI player to do a round Advanced. The virtual player place 
	 * always its second card. In the first round the card is placed on the (1,1) "square".
	 * To place a card an AI computes the different potential scores that every move can give and chooses the bes.
	 * 
	 * @param joueurIA
	 */

	public void effectuerTourIAAdvanced(Joueur joueurIA) {
		
		cartePioche = joueurIA.getCarteEnMain().get(1);// Pour l'instant l'IA place toujours sa deuxieme carte La
														// première carte sera donc sa carte victoire
		joueurIA.getCarteEnMain().remove(cartePioche);
		if (this.numeroTour == 0) {
			tapis.placerCarte(cartePioche, 1, 1);
		} else {
			joueurIA.strategie.effectuerTour(tapis, cartePioche, joueurIA);// les strategies des joueurs
		}

		if (jeu.getCartes().size() > 0) {
			joueurIA.getCarteEnMain().add(jeu.popPremiereCarte());
		}
		numeroTour++;
		
	}
	/**
	 *  This method allows to an AI player to do a round in a normal version of ShapeUp. The virtual player places
	 * always its second card. To place a card an AI compute the different potential point that a moves can give.
	 * 
	 * @param joueurIA
	 */

	public void effectuerTourIANormal(Joueur joueurIA) {
		cartePioche = jeu.popPremiereCarte();
		joueurDuTour.strategie.effectuerTour(tapis, cartePioche, joueurDuTour);
		numeroTour++;
	}
	/**
	 * This method is called on each round to know if we can execute another round.
	 * An advanced game is finished when the player has only one card in hand or if the carpet is full.
	 * An normal game is finished the carpet is full or if the deck is empty.
	 * 
	 * @return true when the game is finished and false during the game
	 */

	public Boolean partieFinie() {
		if (partie.isAdvanced() && (joueurDuTour.getCarteEnMain().size() <= 1 || tapis.tapisEstRemplie())) {
			return true;
		} else if (!partie.isAdvanced() && (tapis.tapisEstRemplie() || !jeu.piochePleine())) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * This method initializes the interface of settings to resolve some bug on the graphic display.
	 */

	public void initialiserInterfaces(){
		interfaceChoix.frame.setVisible(false);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					interfaceTapis = new InterfaceGraphique(partie);
					interfaceTapis.frmMonInterface.setVisible(true);//lance l'interface du tapis
					interfaceChoix.frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * This method is called on the beginning of each turn for each player. 
	 * In this method on the first round the card are deal according to the type of the game.
	 * For each turn this method allows to know if a real player wants to move a card on the carpet.
	 * 
	 */
	
	public void debutDuTour() {	// peut arriver ici alors que partie finie donc go check
		
		etatTour.phaseActuel = Phase.DEBUTTOUR;
		etatTour.carteBouge = false;
		etatTour.cartePlace = false;
		
		if (numeroTour==0) {
			initialiserInterfaces();
			this.joueurDuTour=partie.getJoueur(0);
			if (isAdvanced()) {
				for (int i = 0; i < nombreDeCartesEnMain; i++) {// 3cartes par joueur
					for (int j = 0; j < partie.getTypePartie(); j++) {
						partie.getJoueur(j).getCarteEnMain().add(jeu.popPremiereCarte());
					}
				}
				// ATTRIBUTION DES CARTES VICTOIRES DES IA
				// L'IA PLACE TOUJOURS SA DEUXIEME CARTE ET CONSIDERE QUE SA PREMIERE CARTE EST
				// SA CARTE VICTOIRE
				for (int i = 0; i < partie.getTypePartie(); i++) {
					if (partie.getJoueur(i).isAI()) {
						partie.getJoueur(i).setVictoryCarte(partie.getJoueur(i).getCarteEnMain().get(0));
					}
				}
				
				if (!this.joueurDuTour.isAI()) {
					this.selectionDepuisMain();
				} else {
					this.effectuerToursIAs();
					
				}
			}
			else {
				for (int i = 0; i < partie.getTypePartie(); i++) {
					//distribution des cartes victoire
					partie.getJoueur(i).setVictoryCarte( jeu.popPremiereCarte() );

				}

				this.menuPlacement();
			}return;
		}
		if (partieFinie()) {
			finDePartie();
			return;
		}

		System.out.println("NOUVEAU TOUR " + joueurDuTour + " A vous de jouer ");
		setChanged();
		notifyObservers("NOUVEAU TOUR " + joueurDuTour + " A vous de jouer ");
		
		if (jeu.getCartes().size() > 0 && !isAdvanced()) {// si partie normal on ajoute une carte pour l'affichage
			cartePioche = jeu.popPremiereCarte();// L'abscence de cette ligne provoquait le bug 3 IA calcul score
													// seulement
			joueurDuTour.getCarteEnMain().add(cartePioche);
			setChanged();
			notifyObservers(this.joueurDuTour);
		}
		// joueurDuTour.getCarteEnMain().add(cartePioche);//pour l'affichage elle sera
		// enlevé en fin de tour

		tapis.afficherTapis();

		if (isAdvanced()&& numeroTour!=0) {// si partie advanced

			if (tapis.getNombreDeCartesPoses() > 1) {
				// NE PEUT PAS DEPLACER UNE CARTE AU PREMIER TOUR AVANT DE POSER
				System.out.println("Voici vos carte");
				setChanged();
				notifyObservers(joueurDuTour);
				joueurDuTour.affichageCarteEnMain();

				System.out.println(" Souhaitez vous deplacer une carte avant de placer une carte ? O/N ");
			}
		} else {// si partie normale
			if (tapis.getNombreDeCartesPoses() > 1) {
				// NE PEUT PAS DEPLACER UNE CARTE AU PREMIER TOUR AVANT DE POSER
				System.out.println("Vous avez pioché :" + cartePioche.affichageTapis()
						+ " Souhaitez vous deplacer une carte avant de placer la carte pioché ? O/N ");
			}
		}
		if (tapis.getNombreDeCartesPoses()==1) {
			if (isAdvanced()) {
				this.selectionDepuisMain();
			}else {
				this.menuPlacement();
			}
			
		}
	}
	/**
	 * Gets joueurDuTour
	 * 
	 * @return joueurDuTour
	 */

	public Joueur getJoueurDuTour() {
		return joueurDuTour;
	}
	/**
	 * This method is called in the phase where a real player chose what card move in the console
	 * or in the graphic display. The menu with the card present on the carpet is called.
	 */

	public void menuMouvement() {
		// phase ou l'on choisit avec la commande la carte a deplacer ne change rien
		// pour la partie graphique car on peut choisir la carte a deplacer directement
		// choix de la carte a deplacer
		etatTour.phaseActuel = Phase.MENUMOUVEMENT;

		System.out.println("Quel carte souhaitez vous deplacer ?");
		
			
		nombreDechoix = tapis.menuCarteADeplacer();// menu des carte à deplacer


	}
	/**
	 * This method shows the different moves available for the card given in parameter.
	 * 
	 * @param carteADeplacer
	 */
	

	public void menuMouvement2(Carte carteADeplacer) {
		// donc quand dans cette phase il faut afficher
		// choix de l'emplacement ou poser la carte
		etatTour.phaseActuel = Phase.MENUMOUVEMENT2;

		nombreDechoix = tapis.choixCarteADeplacer(carteADeplacer);// effectue les modifications necessaire pour bouger
																	// la carte et affiche le menu des cases

		tapis.setChangedAndNotify();
	}
	/**
	 * This method generates the different moves possible with the card on your hand that you want to place.
	 *
	 */

	public void menuPlacement() {
		if (etatTour.carteBouge == true) {
			System.out.println("Vous devez maintenant poser une carte");
		}
		etatTour.phaseActuel = Phase.MENUPLACEMENT;
		if (this.numeroTour == 0) {
			if (this.isAdvanced()) {
				tapis.placerCarte(cartePioche, 1, 1);
				this.finDuTour();	
			}else {
				System.out.println("Votre carte à été placer par défaut");
				tapis.placerCarte(jeu.popPremiereCarte(), 1, 1);// première carte placé par defaut

				this.finDuTour();
			}
					
		} else {
			nombreDechoix = tapis.menuDesCoups(tapis.genererPlacementsLegaux(cartePioche));

			System.out.println("Ou souhaitez vous placer votre carte ?");
		}

		tapis.setChangedAndNotify();
	}
	/**
	 * This method allows in a advanced ShapeUp game to chose what card on your hand to place on the carpet
	 * This method displays the card on your hand.
	 */

	public void selectionDepuisMain() {
		// phase ou l'on choisit quelle carte de notre main on souhaite poser, pour le
		// texte
		etatTour.phaseActuel = Phase.SELECTIONDEPUISMAIN;
		if (numeroTour==0) {
			System.out.println("NOUVEAU TOUR " + joueurDuTour + " A vous de jouer ");
			setChanged();
			notifyObservers("NOUVEAU TOUR " + joueurDuTour + " A vous de jouer ");
		}
		System.out.println("Quelle carte voulez vous placer ?");
		joueurDuTour.affichageCarteEnMain();
		setChanged();
		notifyObservers(joueurDuTour);
		tapis.afficherTapis();

	}
	/**
	 * This method is called when the turn is finished.
	 */

	public void finDuTour() {
		
		etatTour.phaseActuel = Phase.FINDUTOUR;
		// rajouter l'index qui garde en mémoire le joueur suivant si besoin après peut
		// etre
		if (isAdvanced()) {
			if (jeu.getCartes().size() > 0) {
				cartePioche = jeu.popPremiereCarte();
				joueurDuTour.getCarteEnMain().add(cartePioche);
			}
		} else {// si partie normale
			if(numeroTour!=0) {
				joueurDuTour.getCarteEnMain().remove(0);// on supprime la carte en main du joueur qui a été ajouté pour
													// l'affichage
			}
			
		}
		numeroTour++;

		effectuerToursIAs();
		debutDuTour();
	}
	/**
	 * This method manage the strategy of AI players according to their difficulty. In the first round
	 * like a real player the card is placed in the case (1,1).
	 * @see StrategieAvecMouveument#effectuerTour(Tapis, Carte, Joueur)
	 * @see StrategiePlacementSeulementAvecCalculScore#effectuerTour(Tapis, Carte, Joueur)
	 */

	public void effectuerToursIAs() {
		if (this.numeroTour==0) {
			if (isAdvanced()) {
				 carteAPlacer = partie.getJoueur(0).getCarteEnMain().get(1);//Pour l'instant l'IA place toujours sa deuxieme carte La preière carte sera donc sa cartevictoire
				 partie.getJoueur(0).getCarteEnMain().remove(carteAPlacer);
				 tapis.placerCarte(carteAPlacer, 1, 1);//placé par defaut 
								 
			}else {
				tapis.placerCarte(jeu.popPremiereCarte(), 1, 1);// première carte placé par defaut
			}
			
		numeroTour++;
		finDuTour();
		}else {
			joueurDuTour = partie.getJoueur(numeroTour % partie.getTypePartie());
		if (isAdvanced()) {
			while (joueurDuTour.isAI()&& joueurDuTour.getCarteEnMain().size() > 1  && !tapis.tapisEstRemplie()) {
				
				effectuerTourIAAdvanced(joueurDuTour);
				joueurDuTour = partie.getJoueur(numeroTour % partie.getTypePartie());
				
			}
		} else {
			while (joueurDuTour.isAI() && jeu.piochePleine() && !tapis.tapisEstRemplie()) {
				effectuerTourIANormal(joueurDuTour);
				joueurDuTour = partie.getJoueur(numeroTour % partie.getTypePartie());
			}
		}
		}
		
	}
	/**
	 * This method notify the observers of Partie.
	 */

	public void setChangedAndNotify(){
		this.setChanged();
		this.notifyObservers();
	}
	/**
	 * This method notify the observers of Partie and give the joueurDuTour from the class Joueur.
	 */

	public void setChangedAndNotifyArgJoueurTour(){
		this.setChanged();
		this.notifyObservers(joueurDuTour);
	}
	/**
	 * This method notify the observers of Partie and give a String.
	 * @param instruction
	 */
	public void setChangedAndNotifyObserverString(String instruction) {
		this.setChanged();
		this.notifyObservers(instruction);
	}
	/**
	 * This method is called when partieFinie is true. It computes the different score of each players
	 * and display it on the console and window.
	 */

	public void finDePartie() {
		etatTour.phaseActuel=Phase.PARTIEFINIE;
		if (isAdvanced()) {
			for (int i = 0; i < partie.getTypePartie(); i++) {
				partie.getJoueur(i).setVictoryCarte(partie.getJoueur(i).getCarteEnMain().get(0));
			}
		}
		afficherScore();

		//doit etre invokeLater car si une partie avec que des IAs il se peut que l'affichage n'ai pas envore été initialisé
		if(nbJoueur==0) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					interfaceTapis.afficherScores();
				}
			});
		}
		else{
			interfaceTapis.afficherScores();
		}

	}


	public void afficherScore() {
		int index;
		tapis.afficherTapis();
		for (index = 0; index < partie.getTypePartie(); index++) { // affichage du score final
			System.out.println("Score du joueur " + partie.getJoueur(index).getPseudo() + " : "
					+ tapis.score(partie.getJoueur(index).getVictoryCarte()) + " avec la carte victoire :"
					+ partie.getJoueur(index).getVictoryCarte().affichageTapis());

		}
	}

	public Partie() {
		partie = this;
		initStrategies();
		this.typeDePartie();
	}
	public void setTapis(Tapis tapis) {
		this.etatTour.phaseActuel=Phase.CHOIXTAPIS;
		this.tapis = tapis;
	}

	public JeuDeCarte getJeu() {
		return jeu;
	}

	public void setJeu(JeuDeCarte jeu) {
		this.jeu = jeu;
	}


	/**
	 * Asks for the number of player(not bots) and changes the game state accordingly
	 * @see Phase#CHOIXJOUEUREELS
	 */
	public void demandeNombreJoueurReels() {// le typePartie
		this.etatTour.phaseActuel=Phase.CHOIXJOUEUREELS;
		String nombreDeJoueurs = String.format("Entrez le nombre de joueur r�els ? (de 0 � " + typePartie + ") :");
		System.out.print(nombreDeJoueurs);
		this.setChangedAndNotifyObserverString(nombreDeJoueurs);
	}

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	/**
	 * Asks for the AI difficulty if there are AI players in the game
	 * @see Phase#CHOIXDIFFICULTEIA
	 */
	public void choixDifficulteeIA() {
		this.etatTour.phaseActuel=Phase.CHOIXDIFFICULTEIA;
		this.joueurCree = new Joueur[this.typePartie];

		if(nbJoueur<typePartie) {//seulement si il y a des IA dans la partie
			System.out.println("Selectionnez la difficulté des IA :");
			int i=0;
			String msg= "";
			String ligne;
			for(Strategie strat : listeStrategies) { //loops over every available strategy
				ligne = i+")" + strat.getNomDifficulté();
				msg += ligne;
				System.out.println(ligne);
				i++;
			}
			

			this.setChangedAndNotifyObserverString("Selectionnez la difficulté des IA :"+msg);
		}else {
			this.nommerJoueur();
		}
	}

	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}


	/**
	 * Creates instances of the Joueur class according to the parameters(numbers of players, numbers of AIs)
	 * @see #typeDePartie()
	 * @see #nombreDeJoueur()
	 */
	public void creerJoueurs() {
		
		
		for (int i = 0; i < this.nbJoueur; i++) { // Creation des joueurs réels
			
			this.joueurCree[i] = new Joueur(this.nomJoueurReel.get(i), false);
		}
		for (int i = this.nbJoueur; i < this.typePartie; i++) { // creation des joueurs virtuels
			this.joueurCree[i] = new Joueur("BOT" + (i - this.nbJoueur + 1), true);// BOT1 puis BOT2 etc..
			
			joueurCree[i].strategie = listeStrategies.get(difficulte);

			
			/*
			if (difficulte == 0) {
				joueurCree[i].strategie = new StrategiePlacementSeulementAvecCalculScore();
			} else if (difficulte == 1) {
				joueurCree[i].strategie = new StrategieAvecMouveument();
			}*/
		}
	}

	/**
	 * names a player
	 * @see Phase#CHOIXNOMJOUEUR
	 */
	public void nommerJoueur() {
		String entrerNomJoueur = "Entrez le nom d'un joueur : ";
		this.etatTour.phaseActuel=Phase.CHOIXNOMJOUEUR;
		
		System.out.print(entrerNomJoueur);
		this.setChangedAndNotifyObserverString(entrerNomJoueur);
	}

	public ArrayList<String> getNomJoueurReel() {
		return nomJoueurReel;
	}

	/**
	 * Choses If the game is normal or advanced
	 * @see Phase#CHOIXVERSION
	 */
	public void typeDePartie() {
		String questionAdvanced = "Voulez vous jouer � la version advanced du Shape Up ? O/N ";
		this.etatTour.phaseActuel=Phase.CHOIXVERSION;
		System.out.println(questionAdvanced);
		setChangedAndNotifyObserverString(questionAdvanced);
	}

	/**
	 * Asks for the number of players (real + AI players)
	 * @see Phase#CHOIXNBJOUEUR
	 */
	public void nombreDeJoueur() {
		this.etatTour.phaseActuel=Phase.CHOIXNBJOUEUR;
		String selectionNombreJoueurs=String.format("ShapeUp à %d ou %d joueurs", minNombreDeJoueurs, maxNombredeJoueurs);
		System.out.println(selectionNombreJoueurs);
		this.setChangedAndNotifyObserverString(selectionNombreJoueurs);
	}

	public void setTypePartie(int typePartie) {
		this.typePartie = typePartie;
	}

	public Joueur getJoueur(int index) {
		return joueurCree[index];
	}

	public int getNbJoueur() {
		return nbJoueur;
	}

	public int getTypePartie() {
		return typePartie;
	}


	/**
	 * Removes the hidden Card from the deck and asks which card placement shape the user wants
	 * @see Tapis#getFormeARespecter()
	 */
	public void hiddeenCarte() {
		
	
		this.hiddenCarte = jeu.popPremiereCarte();
		this.etatTour.phaseActuel=Phase.CHOIXTAPIS;
		this.setChangedAndNotifyObserverString("Quel type de tapis souhaitez vous ?");//Anticiper la cr�ation du Tapis
	}




}
