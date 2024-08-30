package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker le temps imparti de choix des charges mentales.
 */

public class DureeMesureManager {

    //ATTRIBUT POUR STOCKER LA DUREE DE MESURE
    private int minutes;
    private int secondes;
    //CONSTRUCTEUR PAR DÉFAUT

    /**
     * Constructeur permet d'initialiser les minutes et les secondes à 0.
     */
    public DureeMesureManager() {
        this.minutes = 0;
        this.secondes = 0;
    }

    //MÉTHODES GET ET SET
    // setMinutes et setSecondes permettent d'assigner le nouvel intervalle de temps.
    public void setMinutes(int minutes){
        this.minutes = minutes;
    }
    public void setSecondes(int secondes){
        this.secondes = secondes;
    }

    public int getMinutes() {
        return this.minutes;
    }
    public int getSecondes() {
        return this.secondes;
    }
}
