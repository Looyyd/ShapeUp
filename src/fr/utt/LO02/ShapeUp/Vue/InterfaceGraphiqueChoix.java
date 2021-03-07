package fr.utt.LO02.ShapeUp.Vue;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import fr.utt.LO02.ShapeUp.Controleurs.ControleurBtnChoix;
import fr.utt.LO02.ShapeUp.Controleurs.ControleurTextField;
import fr.utt.LO02.ShapeUp.Controleurs.ControleurTexte;
import fr.utt.LO02.ShapeUp.Modele.Partie;
import fr.utt.LO02.ShapeUp.Modele.Phase;
import fr.utt.LO02.ShapeUp.Modele.Tapis;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This graphical interface lets the player chose the game settings such as the number of players, the number
 * of human players etc...
 *
 */
public class InterfaceGraphiqueChoix implements Observer {
	//TODO ca serait mieux si l'interface utilisait des boucles for pour ne pas avoir a modifier le code si un paramètre change
	/**
	 * The main frame
	 */
	public JFrame frame;
	private ArrayList<JButton> btn=new ArrayList<JButton>();
	private JLabel consigne;
	private Partie partie;
	private ArrayList<ControleurBtnChoix> ctrlBtnChoix= new ArrayList<ControleurBtnChoix>();
	private JTextField textField;
	private ArrayList<ControleurTextField> ctrlTexteField= new ArrayList<ControleurTextField>();
	private int cpt=0;
	private ControleurTexte ctrlTexte;
	private String texteOui = "OUI";
	private String texteNon = "NON";
	
	
	public String getTexteOui(){
		return this.texteOui;
	}
	public String getTexteNon() {
		return this.texteNon;
	}
	/**
	 * Creates the application
	 * @param partie
	 * @param ctrlTexte
	 */
	public InterfaceGraphiqueChoix(Partie partie, ControleurTexte ctrlTexte) {
		this.partie=partie;
		this.partie.addObserver(this);
		this.ctrlTexte=ctrlTexte;
		ctrlTexte.addObserver(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		this.btn.add(new JButton(texteOui));
		btn.get(0).setBounds(69, 145, 89, 23);
		frame.getContentPane().add(btn.get(0));
		
		this.btn.add(new JButton(texteNon));
		btn.get(1).setBounds(261, 145, 89, 23);
		frame.getContentPane().add(btn.get(1));
		
		this.consigne = new JLabel("");
		consigne.setBounds(10, 77, 414, 23);
		frame.getContentPane().add(consigne);
		
		
		
		for (int i=0; i<2; i++) {//boutons oui non
			this.ctrlBtnChoix.add(new ControleurBtnChoix(this.partie, this.btn.get(i), this));
			this.ctrlBtnChoix.get(i).addObserver(this);
		}
		
		
		
		update(partie, "Voulez vous jouer à la version advanced du Shape Up ? O/N ");
	}


	/**
	 * The update either shows a new instruciton if arg1 is a string an o is Partie
	 * or if o is a Controleur(BtnChoix or TextField or ControleurTexte)
	 * it shows a new choice screen depending on the state of Partie
	 * @see Phase
	 * @see ControleurBtnChoix#ControleurBtnChoix(Partie, JButton, InterfaceGraphiqueChoix) 
	 * @see ControleurTexte#ControleurTexte(Partie) 
	 * @see ControleurTextField#ControleurTextField(Partie, JTextField) 
	 * @param o
	 * @param arg1
	 */
	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof String && o instanceof Partie) {
			this.consigne.setText((String) arg1);
			
		}
		
		
		if (o instanceof ControleurBtnChoix|| o instanceof ControleurTextField || o instanceof ControleurTexte) {
			if (this.partie.etatTour.phaseActuel==Phase.CHOIXNBJOUEUR) {
				for(Integer i= partie.minNombreDeJoueurs;i<= partie.maxNombredeJoueurs;i++){
					btn.get(i- partie.minNombreDeJoueurs).setText(i.toString());//si on augement le nombre d'options (pas juste 2 et 3) il faudra ajouté le bouton au pannel
				}
				frame.repaint();
				frame.revalidate();
			}
			else if(this.partie.etatTour.phaseActuel==Phase.CHOIXJOUEUREELS) {
				//System.out.println("ok");
				this.btn.add(new JButton("2"));
				frame.getContentPane().add(btn.get(2));
				this.ctrlBtnChoix.add(new ControleurBtnChoix(this.partie, this.btn.get(2), this));
				this.ctrlBtnChoix.get(2).addObserver(this);
				
				if (partie.getTypePartie()==3) {
					this.btn.add(new JButton("3"));
					frame.getContentPane().add(btn.get(3));
					this.ctrlBtnChoix.add(new ControleurBtnChoix(this.partie, this.btn.get(3), this));
					this.ctrlBtnChoix.get(3).addObserver(this);
				}
								
				for (int i=0; i<partie.getTypePartie()+1; i++) {
					this.btn.get(i).setBounds(10+80*i, 145, 89, 80);
					this.btn.get(i).setText(String.valueOf(i));
				}
				frame.repaint();
				frame.revalidate();
			}
			else if (this.partie.etatTour.phaseActuel==Phase.CHOIXDIFFICULTEIA) {
				frame.getContentPane().remove(btn.get(2));
				if (partie.getTypePartie()==3) {
					frame.getContentPane().remove(btn.get(3));
				}
				btn.get(0).setBounds(49, 145, 130, 23);
				this.btn.get(0).setText("Intermédiare");
				btn.get(1).setBounds(261, 145, 89, 23);
				this.btn.get(1).setText("Difficile");
				frame.repaint();
				frame.revalidate();
			}
			else if (this.partie.etatTour.phaseActuel==Phase.CHOIXNOMJOUEUR) {
				if (this.cpt==0) {
					frame.getContentPane().remove(this.btn.get(0));
					frame.getContentPane().remove(this.btn.get(1));
					frame.getContentPane().remove(this.btn.get(2));
					if(partie.getTypePartie()==3) {
						
						frame.getContentPane().remove(this.btn.get(3));
				}
				
					this.cpt=1;
				}else {
					this.frame.getContentPane().remove(this.textField);
				}
				
				this.textField = new JTextField();
				this.textField.setText("");
				textField.setBounds(88, 203, 247, 20);
				frame.getContentPane().add(textField);
				textField.setColumns(10);
				this.ctrlTexteField.add(new ControleurTextField(this.partie,this.textField ));
				for(int i=0; i<this.ctrlTexteField.size(); i++) {
					this.ctrlTexteField.get(i).addObserver(this);	
				}
				frame.repaint();
				frame.revalidate();
				
			}
			else if (this.partie.etatTour.phaseActuel==Phase.CHOIXTAPIS) {
				/*
				if (partie.getNbJoueur()!=0) {
					
					this.frame.getContentPane().remove(this.textField);
				}*/
				this.frame.getContentPane().removeAll();
				this.frame.getContentPane().setLayout(new BorderLayout());
				this.frame.getContentPane().add(consigne, BorderLayout.NORTH);
				/*Dans la partie nord il y a la consigne dans la partie sud les differents choix de tapis*/

				JPanel southTapis =new  JPanel();
				southTapis.setLayout(new GridLayout(1, partie.getTapis().getListesFormes().size()));

				for (int i=0; i<this.partie.getTapis().getListesFormes().size(); i++) {
					JPanel panelGrid = new JPanel();

					panelGrid.setLayout(new BorderLayout());
					this.btn.get(i).setText(String.valueOf(i));
					//btn.get(i).setBounds(69+100*i, 145, 89, 23);
					panelGrid.add(btn.get(i),BorderLayout.NORTH);//le choix du tapis en haut

					JPanel forme = new JPanel();
					remplirJPanelAvecFormeARespecter(forme, partie.getTapis().getListesFormes().get(i));
					panelGrid.add(forme,BorderLayout.SOUTH);

					southTapis.add(panelGrid);
					
				}
				this.frame.getContentPane().add(southTapis,BorderLayout.SOUTH);
				southTapis.repaint();
				southTapis.revalidate();
				frame.repaint();
				frame.revalidate();

			}
		}
	}
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
}

