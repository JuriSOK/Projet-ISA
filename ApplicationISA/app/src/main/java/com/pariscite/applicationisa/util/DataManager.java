package com.pariscite.applicationisa.util;

/**
 * Classe permettant de stocker les différentes données de l'expérimentation en un même endroit, les paramètres, les noms d'utilisateurs ou les charges mentales par exemple.
 */


//import android.icu.text.IDNA;

public class DataManager{


    /*CRÉATION DE L'UNIQUE INSTANCE DE DataManager afin de pouvoir récuperer toutes les données
    au même endroit */

    private static DataManager instance = new DataManager(); // Unique instance de DataManager



    //VARIABLE AFIN DE STOCKER INTERVALLE DE TEMPS
    private TimeManager intervallelFinal;


    //VARIABLE AFIN DE STOCKER L'ÉCHELLE DE MESURE

    private EchelleManager echelleFinal;

    //VARIABLE AFIN DE STOCKER Le nombre d'utilisateur
    private NbUserManager nbUserFinal;

    //VARIABLE AFIN DE STOCKER LE MODE DE MESURE (Visuel ou Sonore)

    private SignalSonoreManager modeFinal;

    //VARIABLE AFIN DE STOCKER LES INFORMATIOnS LIÉES AU VOL

    private InfoVolManager infos;

    //VARIABLE AFIN DE STOCKER L'ENVIRONNEMENT (En vol ou autres)

    private ChoixModeManager choixModeVolFinal;

    //VARIABLE AFIN DE STOCKER LE CODE D'ACCESS:

    private CodeManager codeFinal;

    //VARIABLE AFIN DE STOCKER LA DURÉE D'UNE MESURE:

    private DureeMesureManager dureeFinal;


    //CONSTRUCTEUR PRIVÉ (Empêche la création d'autre instance).

    /**
     * Constructeur privé, empêche la création d'une deuxième instance.
     */
    private DataManager () {

    }

    /**
     * Méthode permettant de récupérer l'unique instance de DataManager.
     * @return instance de type DataManager, l'unique instance de DataManager.
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }


    //GET ET SET INTERVALLE DE TEMPS

    public void setIntervallelFinal(TimeManager intervalle) {
        this.intervallelFinal = intervalle;
    }


    public TimeManager getIntervalleFinal() {
        return this.intervallelFinal;
    }

    //GET ET SET ECHELLE DE MESURE

    public void setEchelleFinal (EchelleManager echelle) {
        this.echelleFinal = echelle;
    }

    public EchelleManager getEchelleFinal() {
        return this.echelleFinal;
    }

    //GET ET SET NOMBRE UTILISATEUR


    public void setNbUserFinal(NbUserManager nbUserFinal) {
        this.nbUserFinal = nbUserFinal;
    }

    public NbUserManager getNbUserFinal() {
        return nbUserFinal;
    }

    //GET ET SET MODE EXPÉRIMENTATION (Sonore ou visuel)


    public void setModeFinal(SignalSonoreManager modeFinal) {
        this.modeFinal = modeFinal;
    }

    public SignalSonoreManager getModeFinal() {
        return modeFinal;
    }

    //GET ET SET INFORMATIONS LIÉES VOL


    public void setInfoVol(InfoVolManager infos){
        this.infos = infos;
    }

    public InfoVolManager getInfoVol(){
        return infos;
    }

    //GET ET SET CHOIX DU MODE LIÉ À L'ENVIRONNEMENT


    public ChoixModeManager getChoixModeVolFinal() {
        return choixModeVolFinal;
    }

    public void setChoixModeVolFinal(ChoixModeManager choixModeVolFinal) {
        this.choixModeVolFinal = choixModeVolFinal;
    }

    //GET ET SET LIÉE AU CODE D'ACCÈS:


    public CodeManager getCodeFinal() {
        return codeFinal;
    }

    public void setCodeFinal(CodeManager codeFinal) {
        this.codeFinal = codeFinal;
    }

    //GET ET SET LIÉE À LA DURÉE D'UNE MESURE:


    public DureeMesureManager getDureeFinal() {
        return dureeFinal;
    }

    public void setDureeFinal(DureeMesureManager dureeFinal) {
        this.dureeFinal = dureeFinal;
    }
}
