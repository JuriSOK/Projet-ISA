package com.pariscite.applicationisa.util;

/**
 * Classe permetant de stocker le choix du mode, vol ou standard.
 */

public class ChoixModeManager {


    //ATTRIBUT POUR STOCKER LE CHOIX
    boolean choixMode;

    /**
     * Constructeur permet d'initialiser la valeur du boolean choixMode à false.
     */
    public ChoixModeManager() {
        this.choixMode = false;

    }


    //METHODE GET ET SET CLASSIQUE

    /**
     * Méthode retournant le choix du mode vol ou standard.
     * @return choixMode, un booleen représentant le choix du mode.
     */
    public boolean isChoixMode() {
        return choixMode;
    }

    /**
     * Méthode permettant de mettre à jour le choix du mode vol ou standard.
     * @param choixMode un booleen représentant le choix du mode, true pour le mode vol, false sinon.
     */
    public void setChoixMode(boolean choixMode) {
        this.choixMode = choixMode;
    }
}
