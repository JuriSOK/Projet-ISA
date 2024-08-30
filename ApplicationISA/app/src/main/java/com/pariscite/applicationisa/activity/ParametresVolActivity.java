package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.InfoVolManager;
import com.pariscite.applicationisa.util.DataManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe permettant de rentrer les paramètres de vol liés à l'expérimentation lorsque le mode vol est activé.
 */


public class ParametresVolActivity extends AppCompatActivity {

    //ATTRIBUTS:


    /*DECLARATION D'UN ATTRIBUT DE CLASSE DataManager POUR STOCKER LES PARAMÉTRAGES
    DE MANIERE PERMANENTE*/
    private DataManager donneesFinales;
    /*DECLARATION D'UN ATTRIBUT DE CLASSE InfoVolManager POUR STOCKER LES INFORMATIONS LIÉS AU VOL
    DE MANIÈRE LOCAL SUR CETTE ACTIVITÉ*/
    private InfoVolManager donnees;
    //DECLARATION DES NUMBERPICKERS POUR LES HEURES ET MINUTES POUR DÉFINIR LE TEMPS PRÉVU DE VOL.
    private NumberPicker dureeHeure;
    private NumberPicker dureeMin;
    //DECLARATION DES BOUTONS POUR PASSER À LA PROCHAINE ACTIVITÉ OU REVENIR AU DEBUT.
    private Button boutonSuivant;
    private Button boutonPrecedant;
    //DECLARATION DES ATTRIBUTS SERVANT A STOCKER LA LISTE DES AERODROMES
    private String[] listeAerodrome;
    private ArrayList<String> arrayListAerodromes;
    private ArrayAdapter<String> adapter;
    //DECLARATION DES ATTRIBUTS POUR AFFICHER LES MENUS DEROULANTS
    private TextInputLayout afficherAerodromes;
    private AutoCompleteTextView aerodromes_correspondants;
    private TextInputLayout afficherAerodromesArrivee;
    private AutoCompleteTextView aerodromes_correspondantsArrivee;
    //DECLARATION DES BOUTON POUR VALIDER LE CHOIX DES AERODROMES
    private MaterialButton boutonArrivee;
    private MaterialButton boutonDepart;




    //METHODES:

    /**
     * Méthode permettant de choisir les heures de la durée du vol.
     */
    private void dureeHeures(){
        //METHODE POUR CHOISIR LA DURÉE DU VOL EN HEURES
        if (dureeHeure != null) {
            dureeHeure.setMinValue(0);
            dureeHeure.setMaxValue(23);
            //dureeHeure.setWrapSelectorWheel(true);
            dureeHeure.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    donnees.setHeures(newVal, oldVal);
                }
            });
        }
    }

    /**
     * Méthode permettant de choisir les minutes de la durée de vol.
     */
    private void dureeMinutes(){
        //METHODE POUR CHOISIR LA DURÉE DU VOL EN MINUTES
        if (dureeMin != null) {
            dureeMin.setMinValue(0);
            dureeMin.setMaxValue(59);
            //dureeMin.setWrapSelectorWheel(true);
            dureeMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    donnees.setMinutes(newVal, oldVal);
                }
            });
        }
    }
    /**
     * Méthode permettant de passer à l'activité suivante quand le bouton suivant est séléctionné.
     */
    private void activiteSuivante(){
        //METHODE POUR PASSER À L'ACTIVITÉ SUIVANTE.
        boutonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donneesFinales.setInfoVol(donnees);
                if (donneesFinales.getInfoVol().getAeroportArrivee() != null & donneesFinales.getInfoVol().getAeroportDepart() !=null) {
                    Intent parametresMesure = new Intent(getApplicationContext(), ParametresMesureActivity.class);
                    startActivity(parametresMesure);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Veuillez confirmer", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    /**
     * Méthode permettant de passer à l'activité précédente quand le bouton précédent est séléctionné.
     */
    private void activitePrecedante(){
        //METHODE POUR PASSER À L'ACTIVITÉ PRÉCÉDENTE.
        boutonPrecedant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choixNomUti = new Intent(getApplicationContext(), ChoixModeActivity.class);
                startActivity(choixNomUti);
            }
        });
    }

    /**
     * Méthode permettant de valider le choix de l'aérodrome de départ.
     */
    private void boutonValiderDepart(){
        //METHODE POUR VALIDER L'AERODROME DE DEPART
        boutonDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!aerodromes_correspondants.getText().toString().isEmpty()){
                    Toast.makeText(ParametresVolActivity.this, aerodromes_correspondants.getText().toString(), Toast.LENGTH_SHORT).show();
                    donnees.setAeroportDepart(aerodromes_correspondants.getText().toString());
                }
            }
        });
    }
    /**
     * Méthode permettant de valider le choix de l'aérodrome d'arrivée.
     */
    private void boutonValiderArrivee(){
        //METHODE POUR VALIDER L'AERODROME D'ARRIVEE
        boutonArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!aerodromes_correspondantsArrivee.getText().toString().isEmpty()){
                    Toast.makeText(ParametresVolActivity.this, aerodromes_correspondantsArrivee.getText().toString(), Toast.LENGTH_SHORT).show();
                    donnees.setAeroportArrivee(aerodromes_correspondantsArrivee.getText().toString());
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_vol);
        //INSTANCIATION DE TOUTE LES VARIABLES.
        donnees = new InfoVolManager();
        dureeHeure = findViewById(R.id.dureeHeures);
        dureeMin = findViewById(R.id.dureeMinutes);
        donneesFinales = DataManager.getInstance();
        //https://www.youtube.com/watch?v=4i5Zob0g8LY https://www.youtube.com/watch?v=Mb8v_9GFkAQ sources pour les listes
        //CREATION DE LA LISTE SERVANT DE SOURCE AU MENU DEROULANT
        listeAerodrome = getResources().getStringArray(R.array.aeroportsListe);
        arrayListAerodromes = new ArrayList<>();
        Collections.addAll(arrayListAerodromes, listeAerodrome);
        adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayListAerodromes);
        //INSTANCIATION DES MENUS DEROULANTS
        afficherAerodromes=(TextInputLayout)findViewById(R.id.IDaerodromes);
        aerodromes_correspondants=(AutoCompleteTextView)findViewById(R.id.aerodromes);
        aerodromes_correspondants.setAdapter(adapter);
        afficherAerodromesArrivee=(TextInputLayout)findViewById(R.id.IDaerodromesArrivee);
        aerodromes_correspondantsArrivee=(AutoCompleteTextView)findViewById(R.id.aerodromesArrivee);
        aerodromes_correspondantsArrivee.setAdapter(adapter);
        //INSTANCIATION DES BOUTONS POUR VALIDER LES CHOIX D'AERODROME
        boutonArrivee = findViewById(R.id.boutonConfirmerArrivee);
        boutonDepart = findViewById(R.id.boutonConfirmerDepart);
        //INSTANCIATION DU BOUTON PRÉCÉDENT ET SUIVANT.
        boutonPrecedant = findViewById(R.id.boutonPrecedant);
        boutonSuivant = findViewById(R.id.boutonSuivant);
        //APPEL DES METHODES
        this.dureeHeures();
        this.dureeMinutes();
        this.activiteSuivante();
        this.activitePrecedante();
        this.boutonValiderDepart();
        this.boutonValiderArrivee();
    }
}