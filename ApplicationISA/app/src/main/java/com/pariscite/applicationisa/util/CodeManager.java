package com.pariscite.applicationisa.util;

/**
 * Classe permettant de créer et stocker le code de connexion au module d'exploitation des données.
 */

public class CodeManager {

    //ATTRIBUTS
    String motDePasse;


    //CONSTRUCTEUR

    /**
     * Constructeur, initialise motDePasse à null.
     */
    public CodeManager() {
        this.motDePasse = null;
    }


    //METHODES AFIN DE CRÉER UN MOT DE PASSE:

    /**
     * Méthode permettant de créer le mot de passe si l'expérimentation est en mode vol.
     * @param nbUser un entier représentant le nombre d'utilisateurs.
     * @param aeroDep une chaine de caractère représentant l'aéroport de départ.
     * @param aeroArr une chaine de caractère représentant l'aéroport d'arrivée'.
     * @param echelle un entier représentant l'échelle de mesure des charges mentales.
     * @param jour un entier représentant le jour de l'expérimentation.
     * @param mois un entier représentant le mois de l'expérimentation.
     * @param annee un entier représentant l'année de l'expérimentation.
     */
    public void creationMdpAvion(int nbUser, String aeroDep, String aeroArr, int echelle,int jour,int mois, int annee) {

            StringBuffer bf = new StringBuffer();

            bf.append("v");
            bf.append(nbUser);
            bf.append((int)aeroDep.charAt(0));
            bf.append((int)aeroArr.charAt(1));
            bf.append(echelle*2);
            bf.append(jour);
            bf.append(mois);
            bf.append(annee);

            this.motDePasse = bf.toString();


    }

    /**
     * Méthode permettant de créer le mot de passe si l'expérimentation n'est pas en mode vol.
     * @param nbUser un entier représentant le nombre d'utilisateurs.
     * @param echelle un entier représentant l'échelle de mesure des charges mentales.
     * @param secondes un entier représentant les secondes du temps d'intervalle entre chaque mesures de la charge mentale.
     * @param jour un entier représentant le jour de l'expérimentation.
     * @param mois un entier représentant le mois de l'expérimentation.
     * @param annee un entier représentant l'année de l'expérimentation.
     */
    public void creationMdpNormal(int nbUser,int echelle,int secondes,int jour,int mois,int annee) {
        StringBuffer bf = new StringBuffer();

        bf.append(nbUser);
        bf.append(echelle*2);
        bf.append(nbUser*3);
        bf.append(secondes*4);
        bf.append(jour);
        bf.append(mois);
        bf.append(annee);

        this.motDePasse = bf.toString();
    }

    /**
     * Méthode permettant de retourner le mot de passe
     * @return motDePasse, une chaine de caractère représentant le code à rentrer sur le module d'exploitation des données.
     */
    public String getMotDePasse() {
        return motDePasse;
    }
}
