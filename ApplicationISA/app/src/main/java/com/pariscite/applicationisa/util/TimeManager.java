package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker l'intervalle de temps entre chaque mesures de la charge mentale du ou des utilisateurs.
 */


public class TimeManager {

    //ATTRIBUT POUR STOCKER L'INTERVALLE DE MESURE
    private int minutes;
    private int secondes;

    //-----CONSTRUCTEUR-----

    /**
     * Constructeur, initialise les minites et les secondes à 0.
     */
    public TimeManager() {
        this.minutes = 0;
        this.secondes = 0;
    }

    //-----METHODES-----


    // setMinutes et setSecondes permettent d'assigner le nouvel intervalle de temps.
    public void setMinutes(int minutes){
        this.minutes = minutes;
    }
    public void setSecondes(int secondes){
        this.secondes = secondes;
    }

    //METHODE GET ET SET CLASSIQUE

    public int getMinutes() {
        return this.minutes;
    }
    public int getSecondes() {
        return this.secondes;
    }


}



