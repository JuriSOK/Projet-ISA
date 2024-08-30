package com.pariscite.applicationisa.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.DataManager;
import com.pariscite.applicationisa.util.UserManager;
import com.pariscite.applicationisa.util.Utilisateur;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Classe permettant de mesurer les charges mentales de tous les utilisateurs.
 */



public class MesureMultiActivity extends AppCompatActivity {


    //ATTRIBUTS

    // déclararation d'une liste d'utilisateurs.
    private ArrayList<Utilisateur> utilisateurs;

    // déclararation d'un entier pour gérer les index de la liste
    private int utilisateurActuelIndex = 0;

    //déclaration d'une variable pour pouvoir intéragir sur le Linear Layout (XML)
    private LinearLayout layoutMesures;

    //déclaration d'une variable pour pouvoir intéragir sur le champ de texte pour afficher les noms d'utilisateurs (XML)
    private TextView nomUtilisateurTextView;

    //declaration d'un entier final et statique, qui représente l'échelle de mesure.
    //Elle est déclarée finale car sa valeur ne changera plus jamais.
    private static final int MAX_ECHELLE_MESURE = DataManager.getInstance().getEchelleFinal().getNombreBouton();

    //declaration d'une variable de type UserManager qui va permettre de stocker définitivement.
    private UserManager listeUtilisateurs;

    //declaration d'un bouton pour arrêter l'expérimentation.
    private Button bouton_stop;

    //declaration d'un bouton pour lancer la reconaissance vocale.
    private ImageButton boutonMicro;

    //déclaration d'un attribut de type SpeechRecognizer pour la reconnaissance vocale.
    private SpeechRecognizer Reco_Vocale;
    private Context contexte; //CONTEXTE

    //Attribut de type booléen pour dire si active la reconaissance vocale ou non.
    private boolean isListening;

    //Attribut afin de stocker la valeur de la charge mentale de la mesure courante.
    private int valeurChargeMentale;
    private long temps; //POUR L'INTERVALLE DE TEMPS
    private long temps_restant; //TEMPS RESTANT JUSQU'AU PASSAGE A L'ACTIVITE DE COMPTEUR CHARGE MENTALE
    private boolean compteur_en_cours; //ETAT DU COMPTEUR
    private CountDownTimer mCountDownTimer; //COMPTEUR
    private AlertDialog.Builder builder; //constructeur de dialogue




    //METHODES

    /**
     * Méthode permettant de démarrer le compteur.
     */
    private void startTimer(int index){
        mCountDownTimer = new CountDownTimer(temps_restant, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                temps_restant = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                compteur_en_cours = false;


                if (utilisateurActuelIndex ==  0) {
                    utilisateurs.get(0).ajouterChargeMentale(MAX_ECHELLE_MESURE);//VALLMAX
                }

                for(int i = index; i<utilisateurs.size();i+=1){
                    if(i>0) { // verif pour eviter de rajouter une charge mentale à l'utilisateur principal
                        utilisateurs.get(i).ajouterChargeMentale(0);
                    }
                }

                Intent nextActivity = new Intent(getApplicationContext(), CompteurChargeMentaleActivity.class);
                startActivity(nextActivity);

            }
        }.start();
        compteur_en_cours = true;
    }

    /**
     * Méthode pour créer le bouton stop, permettant de terminer l'expérimentation.
     */
    private void boutonStop(){
        /*METHODE DU BOUTON STOP
        PERMET DE TERMINER L'EXPERIMENTATION*/
        bouton_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.cancel();
                //https://www.youtube.com/watch?v=athzb2q-sWw (pour la fenêtre de dialogue)
                builder.setTitle("Alerte")
                        .setMessage("Confirmer l'arrêt de l'expérimentation ?")
                        .setCancelable(true)
                        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCountDownTimer.cancel();
                                Intent choixActivite = new Intent(getApplicationContext(), CodeActivity.class);
                                startActivity(choixActivite);
                            }
                        })
                        .setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startTimer(utilisateurActuelIndex);
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * Méthode permettant de créer le bouton pour lancer l'enregistrement de la voix.
     */
    private void boutonVoix() {
        //BOUTON AFIN D'ACTIVER LA RECONAISSANCE VOCALE ASSOCIÉE À LA MÉTHODE seTonTouchListener().
        boutonMicro.setOnTouchListener(new View.OnTouchListener() { //Instancie le bouton au toucher
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //LORSQUE LE BOUTON EST MAINTENU, L'ÉCOUTE EST DÉBUTÉ
                        boutonMicro.setImageDrawable(ContextCompat.getDrawable(MesureMultiActivity.this, R.drawable.micro_on)); //Change le sprite du micro
                        startListening(); //MÉTHODE AFIN DE LANCER LA RECONNAISSANCE VOCALE.
                        break;
                    }
                    //LORSQUE LE BOUTON EST RELACHÉ, L'ÉCOUTE EST TERMINÉ
                    case MotionEvent.ACTION_UP: {
                        boutonMicro.setImageDrawable(ContextCompat.getDrawable(MesureMultiActivity.this, R.drawable.micro_off)); //Change le sprite du micro
                        stopListening(); //MÉTHODE AFIN D'ARRÊTER LA RECONNAISSANCE VOCALE.
                        break;
                    }
                }
                return false;
            }
        });
    }

    /**
     * Cette méthode permet de traiter les données reçues par le micro, prend le dernier mot enregistré par le micro.
     */
    private void reconnaissanceVocale(){



        //MÉTHODE LIÉE À LA RECONNAISSANCE VOCALE (S'ACTIVE LORSQUE LA MÉTHODE STARTLISTENING EST APPELLÉE).
        Reco_Vocale.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            //MÉTHODE POUR TRAITER LES RÉSULTATS.
            @Override
            public void onResults(Bundle results) {
                //Liste afin de stocker les différentes phrases dites à l'oral.
                ArrayList<String> resultatVoc = results.getStringArrayList(Reco_Vocale.RESULTS_RECOGNITION);
                String testReco = resultatVoc.get(resultatVoc.size()-1);

                //CODE AFIN DE RÉCUPÉRER LE DERNIER MOT DIT PAR L'UTILISATEUR (QUI EST LA VALEUR DE LA CHARGE MENTALE).
                StringTokenizer test = new StringTokenizer(testReco, " ");

                String dernierMot = "";
                while (test.hasMoreTokens()) { //Tant qu'il reste des mots qui suivent
                    dernierMot = test.nextToken(); //On stock le dernier String
                }

                try {
                    //ON CONVERTIT LA VALEUR DE LA CHARGE MENTALE EN INT (INITIALEMENT EN STRING).
                    valeurChargeMentale = Integer.parseInt(dernierMot); //On essaye de convertir le String en int
                    //encadrement de la valeur de la charge mentale saisie via reconnaissance vocale (en fonction de l'échelle).
                    if((MAX_ECHELLE_MESURE >= valeurChargeMentale) && (valeurChargeMentale > 0)){

                        //SI LA MESURE RÉCUPÉRÉE EST BIEN COMPRISE ENTRE L'ÉCHELLE, ON PASSE AU COMPTEUR ET ON AJOUTE LA CHARGE MENTALE.
                        Toast.makeText(contexte, "Valeur choisie: " + valeurChargeMentale, Toast.LENGTH_SHORT).show();
                        utilisateurs.get(utilisateurActuelIndex).ajouterChargeMentale(valeurChargeMentale);

                        //ON PASSE AU PROCHAIN UTILISATEUR
                        utilisateurActuelIndex++;



                        //SI TOUT LES UTILISATEURS ONT RÉÇU LEUR CHARGE MENTALE, LE COMPTEUR (L'activité associée au compteur)  EST LANCÉE
                        if (utilisateurActuelIndex >= utilisateurs.size()) {
                            utilisateurActuelIndex = 0;

                            mCountDownTimer.cancel();
                            startActivity(new Intent(getApplicationContext(), CompteurChargeMentaleActivity.class));
                        }
                        //SINON ON APPELLE RÉCURSIVEMENT LA MÉTHODE POUR LE PROCHAIN UTILISATEUR.
                        else {
                            mCountDownTimer.cancel();
                            mesurerChargeMentale(utilisateurs.get(utilisateurActuelIndex));
                        }

                    }
                    else{ //Si la valeur est au dessus de l'échelle sélectionné
                        Toast.makeText(contexte, "Valeur hors échelle de mesure: " + valeurChargeMentale, Toast.LENGTH_SHORT).show();

                    }
                } catch (NumberFormatException e) {
                    //Si la conversion a échoué
                    Toast.makeText(contexte, "Mot non-reconnu: " + dernierMot, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    /**
     * Méthode permettant d'activer la reconnaissance vocale.
     */
    private void startListening() {

        //PASSE LA VALEUR DU BOOLEEN À VRAI.
        isListening = true;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        Reco_Vocale.startListening(intent);
    }

    /**
     * Méthode permettant d'arrêter la reconnaissance vocale.
     */
    private void stopListening() {

        //PASSE LA VALEUR DU BOOLEEN À FAUX.
        isListening = false;
        Reco_Vocale.stopListening();
    }

    /**
     * Méthode récursive permettant de créer les boutons de mesure de la charge mentale pour chaque utilisateurs.
     * @param utilisateur de type Utilisateur, l'utilisateur dont la charge est mesurée.
     */
    private void mesurerChargeMentale(Utilisateur utilisateur) {
        layoutMesures.removeAllViews();

        //AFFICHAGE DU NOM D'UTILISATEUR DANS LE TEXTVIEW
        nomUtilisateurTextView.setText(utilisateur.getNom());

        //CRÉATION DE BOUTON DYNAMIQUE AFIN D'AJUSTER LE NOMBRE DE BOUTONS SELON L'ECHELLE DE MESURE
        for (int i = 1; i<=MAX_ECHELLE_MESURE; i++) {
            Button boutonMesure = new Button(this);
            boutonMesure.setText(String.valueOf(i));

            boutonMesure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Convertir avec un parseur de la valeur DU BOUTON VERS UN INT.
                    int chargeMentale = Integer.parseInt(((Button) v).getText().toString());
                    utilisateur.ajouterChargeMentale(chargeMentale);

                    /*
                    Condition qui vérifie que lorsque tout les utilisateurs ont reçu leurs charges mentales,
                    alors on passe à l'activité pour patienter.
                     */
                    utilisateurActuelIndex++;
                    if (utilisateurActuelIndex >= utilisateurs.size()) {
                        utilisateurActuelIndex = 0;

                        mCountDownTimer.cancel();
                        startActivity(new Intent(getApplicationContext(), CompteurChargeMentaleActivity.class));
                    }
                    else { //On re-apelle la méthode afin de mesurer les autres utilisateurs. (après avoir incrémenter l'indice).
                        mCountDownTimer.cancel();
                        mesurerChargeMentale(utilisateurs.get(utilisateurActuelIndex));
                    }

                }
            });
            layoutMesures.addView(boutonMesure);
        }
        startTimer(utilisateurActuelIndex);
    }

    /**
     * Méthode permettant de bloquer l'utilisation du bouton retour des téléphones android, ce bouton
     * pouvant être la source d'erreurs et de bugs.
     */
    @Override
    public void onBackPressed() {

        Toast.makeText(contexte, "Retour impossible", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesure_multi);

        //POUR L'ALERTE
        builder = new AlertDialog.Builder(this);


        //DÉFINIT LE TEMPS SOUHAITEZ
        temps += ((DataManager.getInstance().getDureeFinal().getMinutes()*60)*1000) + (DataManager.getInstance().getDureeFinal().getSecondes()*1000);;    //SECONDES EN MILLISECONDES
        temps_restant = temps;

        //Instanciation du bouton stop grâce à l'identifiant.
        bouton_stop = findViewById(R.id.buttonStop);

        //recupération de la seul instance de la classe UserManager.

        listeUtilisateurs = UserManager.getInstance();
        boutonMicro = findViewById(R.id.boutonMicro);
        //Instanciation du layout stop grâce à l'identifiant.
        layoutMesures = findViewById(R.id.layout_mesures);

        //Instanciation du textView stop grâce à l'identifiant.
        nomUtilisateurTextView = findViewById(R.id.mUtilisateurTextView);

        //récupération de la liste d'utilisateurs dans la liste utilisateurs.

        utilisateurs = listeUtilisateurs.getListeUtilisateur();


        //CONTEXTE
        contexte = this;

        //RECONNAISSANCE VOCALE
        Reco_Vocale = SpeechRecognizer.createSpeechRecognizer(this);


        //DÉCLARATION DU BOOLEAN ISLISTENING
        isListening = false;

        this.boutonStop();
        this.boutonVoix();
        this.reconnaissanceVocale();
        //Première appel de la méthode mesurerChargeMentale des appels récursifs sur le premier utilisateur.
        this.mesurerChargeMentale(utilisateurs.get(utilisateurActuelIndex));

    }

}