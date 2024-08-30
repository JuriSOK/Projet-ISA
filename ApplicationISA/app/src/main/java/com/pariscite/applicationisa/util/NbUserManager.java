package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker le nombre d'utilisateurs.
 */


public class NbUserManager {


    //ATTRIBUT POUR STOCKER LE NOMBRE D'UTILISATEUR
    private int nbUser;

    /**
     * Constructeur, initialise le nombre d'utilisateurs à 1 (nombre minimal d'utilisateurs).
     */
    public NbUserManager() {
        this.nbUser = 1;
    }



    //METHODE GET ET SET CLASSIQUE
    public void setNbUser(int nbUser) {
        this.nbUser = nbUser;
    }

    public int getNbUser() {
        return nbUser;
    }
}
