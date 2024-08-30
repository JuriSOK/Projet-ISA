package com.pariscite.applicationisa.util;

import java.util.ArrayList;

/**
 * Classe permettant de stocker les informations liées à un utilisateur, son nom et ses charges mentales par exemple.
 */

public class Utilisateur {
    private String nom;
    private ArrayList<Integer> chargesMentales;

    /**
     * COnstructeur, permet de d'initialiser le nom de l'uitilisateur et de créer une liste de ses charges mentales.
     * @param nom une chaine de caractère représentant le nom.
     */
    public Utilisateur(String nom) {
        this.nom = nom;
        this.chargesMentales = new ArrayList<Integer>();

    }

    /**
     * Méthode pour ajouter une charge mentale à la liste des charges de l'utilisateur.
     * @param charge un entier représentant la charge mentale.
     */
    public void ajouterChargeMentale(int charge) {
        chargesMentales.add(charge);
    }


    //METHODES GETS
    public ArrayList<Integer> getChargesMentales() {
        return chargesMentales;
    }

    public String getNom() {
        return nom;
    }
}
