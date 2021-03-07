package fr.utt.LO02.ShapeUp.Vue;

import java.io.File;
import java.util.*;
import javax.swing.*;

import fr.utt.LO02.ShapeUp.Controleurs.*;
import fr.utt.LO02.ShapeUp.Modele.*;

import java.awt.*;


/**
 * This graphical interface let's the player play the game after the settings have been chosen
 */
public class InterfaceGraphique implements Observer {

	/**
	 * The main frame
	 */
	public JFrame frmMonInterface;
	private Partie partie;
	private Tapis tapis;
	/**
	 * The buttons representing the cards in hand(either 3 cards in advanced or a single card in normal mode) are stored
	 * in an Arraylist
	 */
	private ArrayList<JButton> carteEnMain = new ArrayList<JButton>();
	/**
	 * The cards placed and the possible placements are stored in a 2 dimensional Arraylist
	 */
	private ArrayList<ArrayList<JButton>> casesTapis = new ArrayList<>();

	/**
	 * A south panel that contains the cards in hand and the endTurn button
	 */
	private JPanel southPanel;
	/**
	 * The westPanel contains the victory card if the game is in normal mode or it is not used
	 * It also contains the shape that the placed cards have to respect
	 * @see Joueur#getVictoryCarte()
	 * @see Tapis#getFormeARespecter()
	 */
	private JPanel westPanel;
	/**
	 * The victory button shows the the victory card in normal mode
	 */
	private JButton victoryButton;
	/**
	 * The instruction shows a brief description of what needs to be done by the player or of what just happened after an
	 * AI move
	 */
	private JLabel instruction;
	/**
	 * This panel is used to show the shape of
	 * @see #casesTapis
	 */
	private JPanel panelTapis;
	/**
	 * The internal copy of cases from Tapis
	 * @see Tapis#getCases()
	 */
	private Carte[][] cases;
	/**
	 * The controllers for the cards in hand are stored in an ArrayList
	 * @see ControleurCarteEnMain#ControleurCarteEnMain(Partie, Tapis, JButton, int)
	 */
	private ArrayList <ControleurCarteEnMain> controlleursCartesMains = new ArrayList <>();
	/**
	 * The controllers for the placed cards and possible placements are stored in a 2 dimensional ArrayList
	 * @see ControleurTapis#ControleurTapis(Partie, Tapis, int, int, JButton)
	 * @see #casesTapis
	 */
	private ArrayList <ArrayList<ControleurTapis>> controleursTapis = new ArrayList<>();

	/**
	 * The image that is used as an icon for the buttons that represent possible card placements in casesTapis
	 * @see #casesTapis
	 */
	private ImageIcon imageCoupPossible;
	/**
	 * This button allows to end the turn after having placed a card without having to move a card
	 * @see #southPanel
	 */
	private JButton btnFinTour;

	/**
	 * The controller for the endTurn button
	 * @see #btnFinTour
	 * @see ControleurFinTour#ControleurFinTour(JButton, Partie, InterfaceGraphique)
	 */
	private ControleurFinTour controleurFinTour;

	/**
	 * an imageIcon containg a neutral card; It is used to represent possible moves
	 * @see #casesTapis
	 */
	private ImageIcon imageNeutre;
	/**
	 * Create the application.
	 */
	public InterfaceGraphique(Partie partie) {
		// Cr�ation desdiff�rents objets graphiques du GUI

		this.tapis = partie.getTapis();
		this.partie = partie;
		initialize();// initialiser avant d'ajouter des observers permet'éviter le bug
						// ou les elements de la J frame étaient nuls
		tapis.addObserver(this);
		partie.addObserver(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.frmMonInterface = new JFrame();
		frmMonInterface.setTitle("Ma jolie interface");
		frmMonInterface.setBounds(100, 100, 555, 575);
		frmMonInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.southPanel = new JPanel();
		frmMonInterface.getContentPane().add(southPanel, BorderLayout.SOUTH);

		this.instruction = new JLabel("");
		frmMonInterface.getContentPane().add(instruction, BorderLayout.NORTH);

		this.panelTapis = new JPanel();
		frmMonInterface.getContentPane().add(panelTapis, BorderLayout.CENTER);

		String dir = System.getProperty("user.dir");
		imageNeutre = new ImageIcon(
				dir + File.separator + "src" + File.separator + "fr"+File.separator+"utt"+File.separator+"LO02"+File.separator+"ShapeUp"+File.separator+ "images" + File.separator + "neutre.png");
		imageCoupPossible= imageNeutre;
		this.westPanel = new JPanel(new BorderLayout());

		if (partie.isAdvanced()) {
			for (int i = 0; i < partie.nombreDeCartesEnMain; i++) {
				this.carteEnMain.add(new JButton("", imageNeutre));
				southPanel.add(carteEnMain.get(i));
			}

			for (int i=0; i<partie.nombreDeCartesEnMain; i++) {
				controlleursCartesMains.add(new ControleurCarteEnMain(this.partie,this.tapis, this.carteEnMain.get(i), i));
			}
		}
		else {//partie normale
			this.carteEnMain.add(new JButton("", imageNeutre));
			controlleursCartesMains.add(new ControleurCarteEnMain(partie, tapis, carteEnMain.get(0),0));
			southPanel.add(carteEnMain.get(0));
			frmMonInterface.getContentPane().add(westPanel, BorderLayout.WEST);

			this.victoryButton = new JButton("", imageNeutre);
			westPanel.add(victoryButton, BorderLayout.NORTH);//au Nord la carte victoire au sud la forme a respecter
 		}



		//ajout du bouton FinDeTour
		btnFinTour=new JButton("Fin Du Tour");
		southPanel.add(btnFinTour);
		controleurFinTour=new ControleurFinTour(btnFinTour,partie,this);

		//creation des boutons du tapis
		//les boutons principaux sont au milieu
		/*
		*******
		*PPPPP*
		*PPPPP*
		*PPPPP*
		*******
		//comme cela avec P pour principaux, les autres servent lorsque l'on veut afficher les coups possibles
		 */
		//Attention tapis doit etre initialisé
		for(int i=0; i< tapis.getFormeARespecter().length+2;i++){//nombre lignes
			casesTapis.add(new ArrayList<>());
			controleursTapis.add(new ArrayList<>());
			for(int j=0; j< tapis.getFormeARespecter()[0].length+2; j++){//nombre colonnes
				casesTapis.get(i).add(new JButton());
				//ajout du controleur
				controleursTapis.get(i).add(new ControleurTapis(partie, tapis , i,j,casesTapis.get(i).get(j)));
			}
		}

		//ajout de la forme a respecter
		JPanel westSouth=new JPanel();
		remplirJPanelAvecFormeARespecter(westSouth, tapis.getFormeARespecter());
		westPanel.add(westSouth,BorderLayout.SOUTH);

		//update le tapis après initialisaiton fix le bug du tapis non affiché au premier tour.
		//pototiellement d'autre problèmes d'affichage au premier tour pourrais etre rglés en faisant un update ici
		update(tapis, null);
		update(partie, partie.getJoueurDuTour());
	}


	/**
	 * Fills a Jpanel with buttons that represent a shape that the placed cards have to fit in Is used to show
	 * the different shape options
	 * @see Tapis#getFormeARespecter()
	 * @see #westPanel
	 * @param panel
	 * @param forme
	 */
	private void remplirJPanelAvecFormeARespecter(JPanel panel, Boolean[][] forme){
		//affichage de la forme a respecter a gauche du tapis
		int imax=forme.length;
		int jmax=forme[0].length;
		panel.setLayout(new GridLayout(imax,jmax));
		for(int i=0; i<imax;i++){
			for(int j=0;j<jmax;j++){
				if(forme[i][j]){
					panel.add(new JButton("X"));
				}
				else{
					panel.add(new JButton());
				}
			}
		}
	}

	/**
	 * Shows the score of the game. Is called after a game has ended
	 * @see Partie#afficherScore()
	 * @see Tapis#score(Carte)
	 */
	public void afficherScores(){
		southPanel.removeAll();//on affichera les scores dans le panel SUD
		JTextArea textArea = new JTextArea();
		int index;
		for (index = 0; index < partie.getTypePartie(); index++) { // affichage du score final
			textArea.append("Score du joueur " + partie.getJoueur(index).getPseudo() + " : "
					+ tapis.score(partie.getJoueur(index).getVictoryCarte()) + " avec la carte victoire :"
					+ partie.getJoueur(index).getVictoryCarte().affichageTapis() + '\n');

		}
		textArea.setFont(new Font("Verdana", Font.BOLD, 20));

		southPanel.add(textArea);
		southPanel.repaint();
		southPanel.revalidate();
	}

	/**
	 *Updates the graphical interface
	 * Either shows a new instruction: if arg1 instanceof String
	 * Shows a player cards and victory card if it is a normal game :if o instanceof Partie AND arg1 instanceof Joueur
	 * updates the placed cards if  : o instanceof Tapis AND arg1 == null
	 * @see #instruction
	 * @see #carteEnMain
	 * @see #casesTapis
	 * @param o
	 * @param arg1
	 */
	@Override
	public void update(Observable o, Object arg1) {
		if (arg1 instanceof String)

			this.instruction.setText((String) arg1);

		if (o instanceof Partie && arg1 instanceof Joueur) {

			if (this.partie.isAdvanced()) {

				Joueur joueur = (Joueur) arg1;
				ArrayList<Carte> cartes = joueur.getCarteEnMain();
				for (int i = 0; i < cartes.size(); i++) {
					if (cartes.size() > i) {
						Carte carte = cartes.get(i);
						this.carteEnMain.get(i).setIcon(carte.image);
						carteEnMain.get(i).setVisible(true);
					}
				}
				for(int i=cartes.size();i<partie.nombreDeCartesEnMain;i++){//ne montre plus que 2 cartes si 2 cartes en main
					carteEnMain.get(i).setVisible(false);//rend le bouton invisible si pas assez de carte
				}

			}
			else {
				if(partie.etatTour.cartePlace){
					carteEnMain.get(0).setEnabled(false);//ne pas oublié d'activer plus tard
					carteEnMain.get(0).setIcon(imageNeutre);
				}
				else{
					if(((Joueur) arg1).getCarteEnMain().size()>0) {//pour eviter un crash après la ifn de partie
						carteEnMain.get(0).setEnabled(true);//enable button car desactivé au tour d'avant
						Carte carte = ((Joueur) arg1).getCarteEnMain().get(0);
						this.carteEnMain.get(0).setIcon(carte.image);
						Carte victory = ((Joueur) arg1).getVictoryCarte();
						this.victoryButton.setIcon(victory.image);
					}
				}
			}

		}

		if (o instanceof Tapis && arg1 == null) {
				//si pas besoin d'afficher les coups possibles
				int i = ((Tapis) o).getFormeARespecter().length;
				int j = ((Tapis) o).getFormeARespecter()[0].length;
				this.cases = ((Tapis) o).getCases();


				Carte carte;

				for (int l = 0; l < i; l++) {
					for (int m = 0; m < j; m++) {
						carte = cases[l][m];
						if (carte == null) {
							casesTapis.get(l+1).get(m+1).setIcon(null);
						} else {
							casesTapis.get(l+1).get(m+1).setIcon(carte.image);
						}
					}
				}

			if(partie.etatTour.phaseActuel==Phase.MENUMOUVEMENT2 || (partie.etatTour.phaseActuel==Phase.MENUPLACEMENT && !partie.etatTour.cartePlace)){
				Boolean besoinColonneGauche=false, besoinColonneDroite=false, besoinLigneHaut=false,besoinLigneBas=false;
				for(int l=0; l<tapis.getCoupsLegaux().size();l++){
					Cardinaux decalage = tapis.getDecalageAFaire().get(l);
					if(decalage!=Cardinaux.NULL){
						if(decalage==Cardinaux.DROITE){
							//on peut placer une carte a gauche de la première colonne
							besoinColonneGauche=true;
						}
						else if(decalage==Cardinaux.GAUCHE){
							besoinColonneDroite=true;
						}
						else if(decalage==Cardinaux.BAS){
							besoinLigneHaut=true;
						}
						else if(decalage==Cardinaux.HAUT){
							besoinLigneBas=true;
						}
					}
				}
				i = ((Tapis) o).getFormeARespecter().length+1;//offset de plus caril y a les boutons supplémentaires invisibles
				j = ((Tapis) o).getFormeARespecter()[0].length+1;
				int ldebut=1;
				int mdebut=1;
				if(besoinLigneBas){
					i=i+1;//une ligne de plus ajoutée
				}
				if(besoinLigneHaut){
					ldebut=0;
				}
				if(besoinColonneDroite){
					j=j+1;//une colonne de plus
				}
				if(besoinColonneGauche){
					mdebut=0;
				}

				panelTapis.removeAll();
				panelTapis.setLayout(new GridLayout(i-ldebut, j-mdebut));

				//cases supplementaires en plus du tapis
				for (int l = ldebut; l < i; l++) {
					for (int m = mdebut; m < j; m++) {
						//les icones des boutons supplémentaires sont nullifiés car ils
						if(l==0||m==0){//si une des lignes supplémentaires
							casesTapis.get(l).get(m).setIcon(null);//icone nulle
						}
						else if((m==j-1 && besoinColonneDroite)||(l==i-1 && besoinLigneBas)){
							casesTapis.get(l).get(m).setIcon(null);//icone nulle
						}
						else if(cases[l-1][m-1]==null){//devrait deja etre nulle
							//casesTapis.get(l).get(m).setIcon(null);//icone null
						}


						panelTapis.add(casesTapis.get(l).get(m));
					}
				}
				Coup coup;
				Coordonnees coord;
				for(int l=0; l<tapis.getCoupsLegaux().size();l++){
					coup = tapis.getCoupsLegaux().get(l);
					coord = tapis.positionCarteDeRef(coup);
					i=coord.getLigne();
					j=coord.getColonne();
					//dans les lignes suivantes on rajoute +1 à i et j afin de prendre en compte les boutons supplementaires
					//attention postion par rapport a ref
					if(coup.getPositionParRapportARef()==Cardinaux.BAS){
						casesTapis.get(i+1+1).get(j+1).setIcon(imageCoupPossible);
					}
					else if(coup.getPositionParRapportARef()==Cardinaux.HAUT){
						casesTapis.get(i+1-1).get(j+1).setIcon(imageCoupPossible);
					}
					else if(coup.getPositionParRapportARef()==Cardinaux.DROITE){
						casesTapis.get(i+1).get(j+1+1).setIcon(imageCoupPossible);
					}
					else if(coup.getPositionParRapportARef()==Cardinaux.GAUCHE){
						casesTapis.get(i+1).get(j+1-1).setIcon(imageCoupPossible);
					}
				}


			}
			else{//pas besoin d'afficher les coups possibles
				panelTapis.removeAll();
				panelTapis.setLayout(new GridLayout(i, j));
				for (int l = 0; l < i; l++) {
					for (int m = 0; m < j; m++) {
						panelTapis.add(casesTapis.get(l+1).get(m+1));
					}
				}
			}
			//https://stackoverflow.com/questions/1097366/java-swing-revalidate-vs-repaint
			panelTapis.repaint();
			panelTapis.revalidate();

			partie.setChangedAndNotifyArgJoueurTour();//car une carte viens d'etre posée
		}


	}
}
