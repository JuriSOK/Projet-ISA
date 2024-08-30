package com.pariscite.applicationisa.activity;



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
import com.pariscite.applicationisa.util.EchelleManager;
import com.pariscite.applicationisa.util.UserManager;
import com.pariscite.applicationisa.util.Utilisateur;

import java.util.ArrayList;
import java.util.StringTokenizer;

// VIDEO TUTO BOUTONS DYNAMIQUES : youtube.com/watch?v=6WgbycAIOUg

/**
 * La classe ChargeMentaleSeulActivity représente l'activité peremettant à l'utilisateur de rentrer sa charge mentale
 * lorsqu'il est seul à réaliser l'expérimentation.
 */

public class ChargeMentaleSeulActivity extends AppCompatActivity {
    private UserManager utilisateurListe; //POUR RECUPERER LA LISTE DES UTILISATEURS
    private ArrayList<Utilisateur> utilisateur; //LISTE DES UTILISATEURS
    private DataManager donneesEchelle; //POUR RECUPERER L'ECHELLE
    private EchelleManager echelle; //ECHELLE DE MESURE POUR LA RECONNAISSANCE VOCALE
    private int echelle_valeur; //ECHELLE DE MESURE LOCALE
    private LinearLayout buttonContainer; //CONTENEUR POUR LES BOUTONS
    private Button bouton_stop; //BOUTON STOP
    private ImageButton boutonMicro; //BOUTON MICRO
    private SpeechRecognizer Reco_Vocale; //RECONNAISSANCE VOCALE
    private Context context; //CONTEXTE
    private int valeurChargeMentale; //CHARGE MENTALE STOCKÉE POUR L'UTILISATEUR COURANT
    private TextView nomUtilisateur; //POUR L'AFFICHAGE DU NOM
    private long temps; //POUR L'INTERVALLE DE TEMPS
    private long temps_restant; //TEMPS RESTANT JUSQU'AU PASSAGE A L'ACTIVITE DE COMPTEUR CHARGE MENTALE
    private boolean compteur_en_cours; //ETAT DU COMPTEUR
    private CountDownTimer mCountDownTimer; //COMPTEUR
    private AlertDialog.Builder builder; //constructeur de dialogue
    private Intent vocalAction; //POUR LA RECONNAISSANCE VOCALE.

    //METHODES

    /**
     * Méthode permettant de démarrer le compteur.
     */
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(temps_restant, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                temps_restant = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                compteur_en_cours = false;

                Reco_Vocale.stopListening();
                utilisateur.get(0).ajouterChargeMentale(echelle.getNombreBouton());//VALLMAX
                Intent nextActivity = new Intent(getApplicationContext(), CompteurChargeMentaleActivity.class);
                startActivity(nextActivity);

            }
        }.start();
        compteur_en_cours = true;
    }

    /**
     * Méthode permettant de créer dynamiquement les boutons de mesure des charges mentales.
     */
    private void boutonsMesure(){
        //BOUCLE PERMETTANT DE CONSTRUIRE LES BOUTONS DYNAMIQUES
        for (int i = 1; i <= echelle_valeur; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i));
            button.setId(i); // Définit un ID unique pour chaque bouton
            button.setOnClickListener(new View.OnClickListener() {
                /*METHODE POUR RECUPERER LA MESURE EN CAS DE CLIQUE DU BOUTON
                  ET PASSAGE A L'ACTIVITE COMPTEUR*/
                @Override
                public void onClick(View view) {

                    mCountDownTimer.cancel();
                    // Gérez l'événement clic pour chaque bouton
                    Button clickedButton = (Button) view;
                    int buttonId = clickedButton.getId();
                    utilisateur.get(0).ajouterChargeMentale(buttonId);
                    Intent choixActivite = new Intent(getApplicationContext(), CompteurChargeMentaleActivity.class);
                    startActivity(choixActivite);
                    // Vous pouvez utiliser buttonId pour déterminer quelle action prendre
                }
            });
            buttonContainer.addView(button); //AJOUTER LE BOUTON AU CONTENEUR
        }
    }

    /**
     * Méthode permettant de créer le bouton pour lancer l'enregistrement de la voix.
     */
    private void boutonVoix(){

        //BOUTON AFIN D'ACTIVER LA RECONAISSANCE VOCALE ASSOCIÉE À LA MÉTHODE seTonTouchListener().
        boutonMicro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //LORSQUE LE BOUTON EST MAINTENU, L'ÉCOUTE EST DÉBUTÉ
                    case MotionEvent.ACTION_DOWN: {
                        boutonMicro.setImageDrawable(ContextCompat.getDrawable(ChargeMentaleSeulActivity.this, R.drawable.micro_on)); //Change le sprite du micro
                        Reco_Vocale.startListening(vocalAction); //Record la saisie vocale
                        return true;
                    }
                    //LORSQUE LE BOUTON EST RELACHÉ, L'ÉCOUTE EST TERMINÉ
                    case MotionEvent.ACTION_UP: {
                        boutonMicro.setImageDrawable(ContextCompat.getDrawable(ChargeMentaleSeulActivity.this, R.drawable.micro_off)); //Change le sprite du micro
                        Reco_Vocale.stopListening(); //Stop le record de la saisie vocale
                        return true;
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

                //CODE AFIN DE RÉCUPÉRER LE DERNIER MOT DIT PAR L'UTILISATEUR (QUI EST LA VALEUR DE LA CHARGE MENTALE).
                String testReco = resultatVoc.get(resultatVoc.size()-1);

                StringTokenizer test = new StringTokenizer(testReco, " ");

                String dernierMot = "";
                while (test.hasMoreTokens()) { //Tant qu'il reste des mots qui suivent
                    dernierMot = test.nextToken(); //On stock le dernier String
                }

                try {
                    //ON CONVERTIT LA VALEUR DE LA CHARGE MENTALE EN INT (INITIALEMENT EN STRING).
                    valeurChargeMentale = Integer.parseInt(dernierMot);

                    //encadrement de la valeur de la charge mentale saisie via reconnaissance vocale (en fonction de l'échelle).
                    if((echelle_valeur >= valeurChargeMentale) && (valeurChargeMentale > 0)){
                        //SI LA MESURE RÉCUPÉRÉE EST BIEN COMPRISE ENTRE L'ÉCHELLE, ON PASSE AU COMPTEUR ET ON AJOUTE LA CHARGE MENTALE.
                        Intent choixActivite = new Intent(getApplicationContext(), CompteurChargeMentaleActivity.class);
                        Toast.makeText(context, "Valeur choisie: " + valeurChargeMentale, Toast.LENGTH_SHORT).show();
                        utilisateur.get(0).ajouterChargeMentale(valeurChargeMentale);
                        Reco_Vocale.stopListening();
                        mCountDownTimer.cancel();
                        startActivity(choixActivite);
                    }else{ //Si la valeur n'est pas présente sur l'échelle de mesure
                        Toast.makeText(context, "Valeur hors échelle de mesure: " + valeurChargeMentale, Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) { //S'il y a une erreur de conversion
                    Toast.makeText(context, "Mot non-reconnu: " + dernierMot, Toast.LENGTH_SHORT).show();
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
                                startTimer();
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * Méthode permettant de bloquer l'utilisation du bouton retour des téléphones android, ce bouton
     * pouvant être la source d'erreurs et de bugs.
     */

    @Override
    public void onBackPressed() {

        Toast.makeText(context, "Retour impossible", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_mentale_seul);

        //INSTANCIATION

        //POUR L'ALERTE
        builder = new AlertDialog.Builder(this);

        //DÉFINIT LE TEMPS SOUHAITEZ
        this.temps += ((DataManager.getInstance().getDureeFinal().getMinutes()*60)*1000) + (DataManager.getInstance().getDureeFinal().getSecondes()*1000);    //SECONDES EN MILLISECONDES
        this.temps_restant = this.temps;

        context = this; //LE CONTEXTE

        utilisateurListe = UserManager.getInstance(); //RECUEPERE L'INSTANCE DE USERMANAGER
        utilisateur = new ArrayList<Utilisateur>();
        utilisateur = utilisateurListe.getListeUtilisateur(); //RECUPERE LA LISTE D'UTILISATEUR DE USERMANAGER

        donneesEchelle = DataManager.getInstance(); //RECUPERE L'INSTANCE DE DATAMANAGER

        echelle = donneesEchelle.getEchelleFinal(); //récupération de l'instance de l'attribut de type EchelleManager dans DataManager
        echelle_valeur = echelle.getNombreBouton(); //récupération de la valeur de l'échelle dans la classe EchelleManager.

        //Reconnaissance vocale
        Reco_Vocale = SpeechRecognizer.createSpeechRecognizer(this);
        vocalAction = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//Spécification de l'action à effectuer à savoir la reconnaissance vocale

        //LES finViewById SERVENT A LIER LES ATTRIBUTS DE LA CLASSE AUX ELEMENTS XML
        buttonContainer = findViewById(R.id.buttonContainer);
        bouton_stop = findViewById(R.id.button_stop);
        boutonMicro = findViewById(R.id.boutonMicro);
        nomUtilisateur = findViewById(R.id.mUtilisateurTextView);
        nomUtilisateur.setText(utilisateur.get(0).getNom());

        //APPEL DES MÉTHODES.
        this.startTimer();
        this.boutonsMesure();
        this.boutonStop();
        this.boutonVoix();
        this.reconnaissanceVocale();

    }
}