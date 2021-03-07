package fr.utt.LO02.ShapeUp.Vue;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import fr.utt.LO02.ShapeUp.Controleurs.*;
import fr.utt.LO02.ShapeUp.Modele.*;

// VueTexte est une seconde vue du Mod�le qui fonctionne en paral�le de la premi�re
// C'est un Runnable (un Thread) qui permet � l'utilisateur de ne pas rester bloquer
// dans la console

/**
 * this Reads an input string and calls the controller with it
 */
public class VueLigneCommande  implements Observer, Runnable{
    private Partie partie;
    private ControleurTexte controleur;
    // Comme pour le GUI, le constructeur repose sur cet Interrupteur du Mod�le
    public VueLigneCommande(Partie partie, ControleurTexte controleur) {
        this.partie=partie;
        this.controleur = controleur;

        // On cr�e un Thread sur ...this ....qui est la VueTexte
        Thread t  = new Thread(this);
        // On d�marre le Thread ...... on fait appel � la m�thode run()
        t.start();
    }

    @Override
    //*******************************************************************************************
    // Gestion de la console mode texte
    /**
     * Takes the input strings and calls the controller with them
     * @see ControleurTexte#ControleurTexte(Partie)
     */
    public void run() {
        String saisie=null;//
        boolean quitter =false;


        do{
            saisie=this.lireChaine();
            // le contr�leur de la VueTexte est ici
            // Quand on saisie C � la console alors on invoque la m�thode appuyer()
            controleur.inputTexte(saisie);

        }while(true);
    }
    //*******************************************************************************************

    /**
     * Reads a string from input and returns it
     * @return inputString
     */
    public String lireChaine(){
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in ));
        String resultat=null;
        try {
            resultat=br.readLine();
        } catch (IOException e) {}
        return resultat;
    }

    @Override
    // Comme pour le GUI, VueTexte est un Observer qui impl�mente l'interface Observer
    // N�cessit� de donner le code de update
    public void update(Observable o, Object arg) {


    }

}