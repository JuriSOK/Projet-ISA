package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.ChoixModeManager;
import com.pariscite.applicationisa.util.DataManager;

/**
 * Classe représentant la deuxième activité vue par l'utilisateur, elle permet de sélectionner le mode, vol ou standard,
 * le mode vol permettant de rentrer des informations supplémentaires liées au vol.
 */

//CLASSE 100% FONCTIONNELLE NE PAS TOUCHER S'IL VOUS PLAÎT.

public class ChoixModeActivity extends AppCompatActivity {

    //ATTRIBUTS

    //DECLARATION DU BOUTON ACTIVER ET DESACTIVER POUR CHOISIR LE MODE
    private Button activer;
    private Button desactiver;


    /*DECLARATION D'UNE VARIABLE DE CLASSE ChoixModeManager POUR STOCKER LE CHOIX
    DE MANIERE LOCAL SUR CETTE ACTIVITÉ
     */
    private ChoixModeManager choixModeCourant;

     /*DECLARATION D'UNE VARIABLE DE CLASSE DataManager POUR STOCKER LE CHOIX
    DE MANIERE PERMANENTE
      */
    private DataManager donnees;


    //MÉTHODES:

    /**
     * Méthode pour créer le bouton permettant de choisir le mode vol.
     */
    private void activiteModeVol() {

        //ECOUTEUR DE CLICK DU BOUTON ACTIVER
        activer.setOnClickListener(new View.OnClickListener() {
            @Override
            //METHODE POUR QUE LORSQUE QUE LE BOUTON EST CLIQUÉ, CELA NOUS REDIRIGE VERS L'ACTIVITÉ
            //Si l'utilisateur appuie sur "activer", alors son choix est stocké.
            /*
            Argument: Une variable de type Vue.
            Retour: Vide
             */
            public void onClick(View v) {

                choixModeCourant.setChoixMode(true);
                donnees.setChoixModeVolFinal(choixModeCourant);
                Intent parametresVolActivity = new Intent(getApplicationContext(), ParametresVolActivity.class);
                startActivity((parametresVolActivity));
            }
        });
    }

    /**
     * Méthode pour créer le bouton permettant de choisir le mode standard (refuser le mode vol).
     */
    private void activiteParaMesure() {
        //ECOUTEUR DE CLICK DU BOUTON DESACTIVER
        desactiver.setOnClickListener(new View.OnClickListener() {
            @Override
            //METHODE POUR QUE LORSQUE QUE LE BOUTON EST CLIQUÉ, CELA NOUS REDIRIGE VERS L'ACTIVITÉ
            //Si l'utilisateur appuie sur "activer", alors son choix est stocké.
            /*
            Argument: Une variable de type Vue.
            Retour: Vide
             */
            public void onClick(View v) {
                choixModeCourant.setChoixMode(false);
                donnees.setChoixModeVolFinal(choixModeCourant);
                Intent parametresMesure = new Intent(getApplicationContext(), ParametresMesureActivity.class);
                startActivity(parametresMesure);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_mode);


        //INSTANCIATION
        donnees = DataManager.getInstance();
        choixModeCourant = new ChoixModeManager();


        //ASSIGNATION DU BOUTON ACTIVER GRÂCE à l'ID
        this.activer = findViewById(R.id.accepter);


        //ASSIGNATION DU BOUTON DESACTIVER GRÂCE à l'ID
        this.desactiver = findViewById(R.id.refuser);

        //APPEL DES MÉTHODES "activiteModeVol" et "activiteParaMesure"

        this.activiteModeVol();
        this.activiteParaMesure();




    }
}