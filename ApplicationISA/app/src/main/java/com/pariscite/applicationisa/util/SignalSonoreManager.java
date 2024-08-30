package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker le mdoe de signal, sonore ou visuel.
 */


public class SignalSonoreManager {

    //ATTRIBUT POUR STOCKER LE CHOIX

    boolean signal;

    /**
     * Constructeur, initialise la valeur du signal à false.
     */
    public SignalSonoreManager () {
        this.signal = false;
    }




    //METHODE GET ET SET CLASSIQUE
    public void setSignal(boolean signal) {
        this.signal = signal;
    }


    public boolean isSignal() {
        return signal;
    }
}
