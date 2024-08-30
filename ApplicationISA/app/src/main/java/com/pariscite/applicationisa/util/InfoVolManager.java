package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker les informations liées au vol.
 */

public class InfoVolManager {


    //ATTRIBUTS POUR STOCKER LES INFORMATIONS LIÉES AU VOL
    private int dureeVol;
    private String aeroportDepart;
    private String aeroportArrivee;


    /**
     * Méthode pour rentrer les heures de la durée de vol.
     * @param heure un entier représentant les heures de la durée de vol.
     * @param vielleHeure un entier représentant les heures précédemment rentrées (>0 uniquement si l'utilisateur avait déjà rentré une valeur avant).
     */
    public void setHeures(int heure, int vielleHeure){
        dureeVol -= vielleHeure*60;
        dureeVol += heure*60;
    }

    /**
     * Méthode pour rentrer les minutes de la durée de vol.
     * @param min un entier représentant les minutes de la durée de vol.
     * @param vielleMin un entier représentant les minutes précédemment rentrées (>0 uniquement si l'utilisateur avait déjà rentré une valeur avant).
     */
    public void setMinutes(int min, int vielleMin){
        dureeVol -= vielleMin;
        dureeVol += min;
    }

    //METHODE GET ET SET CLASSIQUE


    public void setAeroportDepart(String aeroportDepart) {
        this.aeroportDepart = aeroportDepart;
    }

    public void setAeroportArrivee(String AeroportArrivee) {
        this.aeroportArrivee = AeroportArrivee;
    }

    public int getDureeVol() {
        return dureeVol;
    }

    public String getAeroportDepart() {
        return aeroportDepart;
    }

    public String getAeroportArrivee() {
        return aeroportArrivee;
    }

}
