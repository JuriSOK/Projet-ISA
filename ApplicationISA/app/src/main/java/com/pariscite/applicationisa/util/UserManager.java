package com.pariscite.applicationisa.util;

import java.util.ArrayList;

/**
 * Classe permettant de stocker la liste des informations liées aux utilisateurs.
 */

public class UserManager {


     /*CRÉATION DE L'UNIQUE INSTANCE DE UserManager afin de pouvoir récuperer toutes les données
    au même endroit */

    private static UserManager instance = new UserManager();

    //VARIABLE AFIN DE STOCKER LA LISTE DES UTILISATEURS
    private ArrayList<Utilisateur> utilisateurs;

    //VARIABLE AFIN DE STOCKER LA LISTE DES NOMS D'UTILISATEURS
    private ArrayList<String> nomUtilisateur;


    //CONSTRUCTEUR PRIVÉ (Empêche la création d'autre instance).

    /**
     * Constructeur privé, empêche la création d'une deuxième instance.
     */
    private UserManager(){
        utilisateurs = new ArrayList<Utilisateur>();
        nomUtilisateur = new ArrayList<String>();
    }


    /**
     * Méthode permettant de renvoyer l'unique instance de la classe.
     * @return l'unique instance de la classe.
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }


    //METHODE GET ET SET

    public void addUsername(int indice,String userName) {
        nomUtilisateur.add(indice,userName);
        // Vous pouvez ajouter ici la logique pour sauvegarder les noms d'utilisateur de manière permanente, par exemple dans SharedPreferences ou dans une base de données.
    }

    public void addUser(String userName) {
        utilisateurs.add(new Utilisateur(userName));
    }


    public ArrayList<String> getUserNames() {
        return nomUtilisateur;
    }


    public ArrayList<Utilisateur> getListeUtilisateur() {
        return utilisateurs;
    }

    public void setListeUtilisateur(ArrayList<Utilisateur> listeUtilisateur) {
        this.utilisateurs = listeUtilisateur;
    }


}
