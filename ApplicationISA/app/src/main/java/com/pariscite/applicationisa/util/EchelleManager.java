package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker l'échelle de mesure des charges mentales.
 */


public class EchelleManager {

    //ATTRIBUT POUR STOCKER LE CHOIX DE L'ÉCHELLE DE MESURE
    private int nombreBouton;

    //CONSTRUCTEUR

    /**
     * Constructeur, initialise le nombre de bouton pour séléctionner sa charge mentale à 2 (le nombre de bouton minimal).
     */
    public EchelleManager() {
        this.nombreBouton = 2;
    }

    //METHODE GET ET SET CLASSIQUE
    public void setNombreBouton(int nombreBouton){
        this.nombreBouton = nombreBouton;
    }

    public int getNombreBouton() {
        return this.nombreBouton;
    }
}