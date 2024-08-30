package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.ChoixModeManager;
import com.pariscite.applicationisa.util.DataManager;
import com.pariscite.applicationisa.util.DureeMesureManager;
import com.pariscite.applicationisa.util.EchelleManager;
import com.pariscite.applicationisa.util.NbUserManager;
import com.pariscite.applicationisa.util.SignalSonoreManager;
import com.pariscite.applicationisa.util.TimeManager;

/**
 * Classe permettant de rentrer les paramètres liés à l'expérimentation.
 */

public class ParametresMesureActivity extends AppCompatActivity {


    //ATTRIBUTS:

    /*DECLARATION D'UNE VARIABLE DE CLASSE DataManager POUR STOCKER LES PARAMÉTRAGES
    DE MANIERE PERMANENTE
      */
    private DataManager donnees;

    /*DECLARATION D'UNE VARIABLE DE CLASSE TimeManager POUR STOCKER LE CHOIX D'INTERVALLE
    DE MANIERE LOCAL SUR CETTE ACTIVITÉ

     */
    private TimeManager intervalleCourant;

    //DECLARATION DES NUMBERPICKERS POUR LES MINUTES ET SECONDES POUR CHOISIR L'INTERVALLE DE MESURE
    private NumberPicker minutes;
    private NumberPicker secondes;

    //DECLARATION DES NUMBERPICKERS POUR LES MINUTES ET SECONDES POUR CHOISIR LA DURÉE DE MESURE

    private NumberPicker minutesDureeMesure;
    private NumberPicker secondesDureeMesure;


    /*DECLARATION D'UNE VARIABLE DE CLASSE EchelleManager POUR STOCKER LE CHOIX D'ÉCHELLE
    DE MANIERE LOCAL SUR CETTE ACTIVITÉ

     */
    private EchelleManager echelleCourante;

    /*DECLARATION D'UNE VARIABLE DE CLASSE NbUserManager POUR STOCKER LE NOMBRE D'UTILISATEUR
    DE MANIERE LOCAL SUR CETTE ACTIVITÉ

     */
    private NbUserManager nbUtiCourant;

    //DECLARATION DU BOUTON SUIVANT POUR PASSER À LA PROCHAINE ACTIVITÉ
    private Button boutonSuivant;

    //DECLARATION DU BOUTON SUIVANT POUR PASSER À L'ACTIVITÉ PRÉCÉDENTE.
    private Button boutonPrecedant;

    //DECLARATION DU BOUTON POUR INCREMENTER/DECREMENTER L'ÉCHELLE DE MESURE

    private ImageButton bouton_incrementer_echelle;

    private ImageButton bouton_decrementer_echelle;

    //DECLARATION DU TEXTVIEW POUR AFFICHER L'ÉCHELLE APRÈS CHAQUE ACTION
    private TextView affichageEchelle;

    //DECLARATION DU BOUTON POUR INCREMENTER/DECREMENTER LE NOMBRE D'UTILISATEUR
    private ImageButton bouton_incrementer_nbUti;
    private ImageButton bouton_decrementer_nbUti;

    //DECLARATION DU TEXTVIEW POUR AFFICHER LE NOMBRE D'UTILISATEUR APRÈS CHAQUE ACTION
    private TextView affichageNbUti;

    /*DECLARATION D'UNE VARIABLE DE CLASSE SignalSonoreManager POUR STOCKER LE CHOIX DE MODE DE MESURE
    DE MANIERE LOCAL SUR CETTE ACTIVITÉ

     */

    private SignalSonoreManager signalCourant;

    /*
    DECLARATION D'UN SWITCH (bouton type interrupteur) POUR POUVOIR ALTERNER ENTRE LES 2 MODES DE MESURES.
     */

    private Switch switchSignal;


     /*DECLARATION D'UNE VARIABLE DE CLASSE ChoixModeManager afin de pouvoir récupérer l'information
     pour limiter le nombre d'utilisateur ou non. (Si le mode Vol est activé), alors le nombre d'utilisateurs est limité à 3).
      */

    private ChoixModeManager choixModeCourant;


    /* DECLARATION D'UNE VARIABLE DE CLASSE DureeMesureManager afin de pouvoir récupérer la durée maximal pour
     faire une mesure.

     */
    private DureeMesureManager dureeMesureCourant;


    //MÉTHODES:

    /**
     * Méthode permmettant de rentrer les minutes de l'intervalle de temps entre les mesures des charges mentales.
     */
    private void setMinutes() {

        //écouteur de changement de valeur pour le Numberpicker des minutes
        minutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                intervalleCourant.setMinutes(newVal);
            }
        });
    }

    /**
     * Méthode permmettant de rentrer les secondes de l'intervalle de temps entre les mesures des charges mentales.
     */
    private void setSecondes() {
        //écouteur de changement de valeur pour le Numberpicker des secondes

        secondes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                intervalleCourant.setSecondes(newVal);
            }
        });
    }

    /**
     * Méthode permmettant de rentrer les minutes du temps imparti pour séléctionner la charge mentale.
     */
    private void setMinutesDureeMesure() {

        //écouteur de changement de valeur pour le Numberpicker des minutes
        minutesDureeMesure.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dureeMesureCourant.setMinutes(newVal);
            }
        });
    }

    /**
     * Méthode permmettant de rentrer les secondes du temps imparti pour séléctionner la charge mentale.
     */
    private void setSecondesDureeMesure() {
        //écouteur de changement de valeur pour le Numberpicker des secondes

        secondesDureeMesure.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
               dureeMesureCourant.setSecondes(newVal);
            }
        });
    }


    /**
     * Méthode permmettant d'incrémenter l'échelle de mesure des charges mentales.
     */
    private void incrementEchelle() {
        //écouteur de clic incrémentation échelle de mesure

        bouton_incrementer_echelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valeurActuelle = echelleCourante.getNombreBouton();
                if (valeurActuelle < 10) { // Vérifie si la valeur est inférieure à 10
                    int nouvelleValeur = valeurActuelle + 1;
                    echelleCourante.setNombreBouton(nouvelleValeur);
                    majEtatBoutonEchelle(nouvelleValeur);
                    affichageEchelle.setText(String.valueOf(nouvelleValeur));
                }

            }
        });
    }

    /**
     * Méthode permmettant de décrémenter l'échelle de mesure des charges mentales.
     */

    private void decrementEchelle() {
        //écouteur de clic décrémentation échelle de mesure

        bouton_decrementer_echelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valeurActuelle = echelleCourante.getNombreBouton();

                if (valeurActuelle > 2) {
                    int nouvelleValeur = valeurActuelle - 1;
                    echelleCourante.setNombreBouton(nouvelleValeur);
                    majEtatBoutonEchelle(nouvelleValeur);
                    affichageEchelle.setText(String.valueOf(echelleCourante.getNombreBouton()));
                }
            }
        });
    }


    /**
     * Méthode permmettant d'incrémenter le nombre d'utilisateurs.
     */
    private void incrementUtilisateur() {
        //écouteur de clic incrémentation nombre utilisateur

        bouton_incrementer_nbUti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bloqueur = 10;
                choixModeCourant = donnees.getChoixModeVolFinal();
                boolean verif = choixModeCourant.isChoixMode();

                int valeurActuelle = nbUtiCourant.getNbUser();

                if (verif) {
                    bloqueur = 3;
                }
                if (valeurActuelle < bloqueur) { // Vérifie si la valeur est inférieure à 10
                    int nouvelleValeur = valeurActuelle + 1;
                    nbUtiCourant.setNbUser(nouvelleValeur);
                    majEtatBoutonUti(nouvelleValeur);
                    affichageNbUti.setText(String.valueOf(nouvelleValeur));
                }
            }
        });

    }

    /**
     * Méthode permmettant de décrémenter le nombre d'utilisateurs.
     */
    private void decrementUtilisateur() {
        //écouteur de clic décrémentation nombre utilisateur

        bouton_decrementer_nbUti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valeurActuelle = nbUtiCourant.getNbUser();
                if (valeurActuelle > 1) {
                    int nouvelleValeur = valeurActuelle - 1;
                    nbUtiCourant.setNbUser(nouvelleValeur);
                    majEtatBoutonUti(nouvelleValeur);
                    affichageNbUti.setText(String.valueOf(nouvelleValeur));
                }
            }
        });
    }

    /**
     * Méthode permmettant de d'initialiser les valeurs des numberPickers.
     */
    private void setMinMax() {
        //LIMITE DES NUMBERPICKERS : (En minutes et secondes)
        this.minutes.setMaxValue(10);
        this.minutes.setMinValue(0);
        this.secondes.setMaxValue(59);
        this.secondes.setMinValue(0);
        this.minutesDureeMesure.setMaxValue(10);
        this.minutesDureeMesure.setMinValue(0);
        this.secondesDureeMesure.setMaxValue(59);
        this.secondesDureeMesure.setMinValue(10);
    }

    /**
     * Méthode permmettant de d'incrémenter l'échelle de mesure des charges mentales.
     */
    private void setSwitchSignal() {
        //écouteur de changement pour le signal sonore

        switchSignal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signalCourant.setSignal(isChecked);
                if (isChecked) {
                    Toast.makeText(ParametresMesureActivity.this, "Le signal sonore est activé", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ParametresMesureActivity.this, "Le signal sonore est désactivé", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Méthode permettant de passer à l'activité précédente quand le bouton précédent est séléctionné.
     */
    private void activitePrecedante() {
        //ECOUTEUR DE CLIC AFIN DE PASSER À L'ACTIVITÉ PRÉCÉDENT
        boutonPrecedant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choixNomUti = new Intent(getApplicationContext(), ChoixModeActivity.class);
                startActivity(choixNomUti);
            }
        });
    }

    /**
     * Méthode permettant de passer à l'activité suivante quand le bouton suivant est séléctionné.
     */
    private void activiteSuivante() {
        //ECOUTEUR DE CLIC AFIN DE PASSER À L'ACTIVITÉ SUIVANTE
        boutonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choixNomUti = new Intent(getApplicationContext(), ChoixNomUtiActivity.class);
                dureeMesureCourant.setMinutes(minutesDureeMesure.getValue());
                dureeMesureCourant.setSecondes(secondesDureeMesure.getValue());

                //STOCKAGE DES DONNEES PERMANENTS DANS DONNEES QUI EST DE CLASSE DATAMANAGER

                donnees.setEchelleFinal(echelleCourante);
                donnees.setIntervallelFinal(intervalleCourant);
                donnees.setNbUserFinal(nbUtiCourant);
                donnees.setModeFinal(signalCourant);
                donnees.setDureeFinal(dureeMesureCourant);

                startActivity(choixNomUti);
            }
        });
    }

    /**
     * Méthode permettant d'activer ou de désactiver le bouton de décrémentation de l'échelle de mesure.
     * @param valeur la valeur de l'échelle, qui ne doit pas être inférieur à 0.
     */
    private void majEtatBoutonEchelle(int valeur) {
        if (valeur > 0) {
            bouton_decrementer_echelle.setEnabled(true);
        } else {
            bouton_decrementer_echelle.setEnabled(false);
        }

    }

    /**
     * Méthode permettant d'activer ou de désactiver le bouton de décrémentation du nombre d'utilisateurs.
     * @param valeur la valeur de l'échelle, qui ne doit pas être inférieur à 0.
     */
    private void majEtatBoutonUti(int valeur) {
        if (valeur > 0) {
            bouton_decrementer_nbUti.setEnabled(true);
        } else {
            bouton_decrementer_nbUti.setEnabled(false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_mesure);

        //INSTANCIATION DE LA VARIABLE DE CLASSE DATAMANAGER

        donnees = DataManager.getInstance();

        //ASSIGNATION DU BOUTON SUIVANT GRÂCE à l'ID
        boutonSuivant = findViewById(R.id.boutonSuivant);
        //ASSIGNATION DU BOUTON PRÉCÉDENT AFIN D'EFFECTUER UN RETOUR SEULEMENT AVEC L'APPLICATION
        boutonPrecedant = findViewById(R.id.boutonPrecedant);

        //INSTANCIATION POUR L'INTERVALLE
        intervalleCourant = new TimeManager();


        //ASSIGNATION DES NUMBERPICKERS heures ET minutes GRÂCE à leurs ID
        minutes = findViewById(R.id.minutes);
        secondes = findViewById(R.id.secondes);

        //ASSIGNATION DES NUMBERPICKERS heuresDureeMesure ET minutesDureeMesure GRÂCE à leurs ID
        minutesDureeMesure = findViewById(R.id.minutes2);
        secondesDureeMesure = findViewById(R.id.secondes2);


        //INSTANCIATION POUR L'ÉCHELLE
        echelleCourante = new EchelleManager();

        //INSTANCIATION POUR NOMBRE UTILISATEUR

        nbUtiCourant = new NbUserManager();

        //INSTANCIATION POUR SIGNAL
        signalCourant = new SignalSonoreManager();

        //INSTANCIATION POUR LA DUREE
        dureeMesureCourant = new DureeMesureManager();

        //ASSIGNATION DES BOUTONS POUR INCREMENTER/DECREMENTER L'ÉCHELLE GRÂCE à leurs ID
        bouton_incrementer_echelle = findViewById(R.id.augmentEchelle);
        bouton_decrementer_echelle = findViewById(R.id.baisseEchelle);

        //ASSIGNATION DU TEXTVIEW POUR AFFICHER L'ÉCHELLE GRÂCE À LEUR ID
        affichageEchelle = findViewById(R.id.valeurEchelle);


        //ASSIGNATION DES BOUTONS POUR INCREMENTER/DECREMENTER L'ÉCHELLE GRÂCE à leurs ID
        bouton_decrementer_nbUti = findViewById(R.id.baisseUti);
        bouton_incrementer_nbUti = findViewById(R.id.augmentUti);
        //ASSIGNATION DU TEXTVIEW POUR AFFICHER L'ÉCHELLE GRÂCE À LEUR ID
        affichageNbUti = findViewById(R.id.valeurNombreUti);


        //ASSIGNATION DU SWITCH POUR MODIFIER L'ÉTAT DU SWITCHER GRÂCE À L'ID
        switchSignal = findViewById(R.id.switchSonore);


        //APPELS DE TOUTES LES MÉTHODES.
        this.setMinutes();
        this.setSecondes();
        this.setMinutesDureeMesure();
        this.setSecondesDureeMesure();
        this.setSwitchSignal();
        this.setMinMax();
        this.activitePrecedante();
        this.activiteSuivante();
        this.decrementEchelle();
        this.incrementEchelle();
        this.decrementUtilisateur();
        this.incrementUtilisateur();

    }

}
