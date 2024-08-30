package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.test.MesureTestMultiActivity;
import com.pariscite.applicationisa.util.DataManager;
import com.pariscite.applicationisa.util.TimeManager;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Classe permettant d'afficher un compteur de temps réprésentant l'intervalle de temps entre chaque mesures
 * de la charge mentale.
 */

public class CompteurChargeMentaleActivity extends AppCompatActivity {

    //ATTRIBUTS
    private DataManager donnees; //DONNEES DANS LE DATAMANAGER, POUR RECUPERER L'INTERVALLE DE TEMPS
    private TimeManager donnee_temps; //INTERVALLE DE TEMPS
    private TextView compteur; //POUR AFFICHER LE COMPTEUR
    private CountDownTimer mCountDownTimer; //COMPTEUR
    private Button bouton_stop; //BOUTON STOP
    private static long temps; //POUR L'INTERVALLE DE TEMPS
    private long temps_restant; //TEMPS RESTANT JUSQU'AU PASSAGE A L'ACTIVITE DE MESURE DE LA CHARGE MENTALE
    private boolean compteur_en_cours; //ETAT DU COMPTEUR
    private AlertDialog.Builder builder; //constructeur de dialogue
    private Context context;



    //MÉTHODES

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
     * Méthode permettant de démarrer le compteur.
     */
     private void startTimer(){
         mCountDownTimer = new CountDownTimer(temps_restant, 1000) {
             @Override
             public void onTick(long millisUntilFinished) {
                 temps_restant = millisUntilFinished;
                 updateCountDownText();
             }

             @Override
             public void onFinish() {
                 compteur_en_cours = false;
                 //si le signal sonoore et activé
                 if (DataManager.getInstance().getModeFinal().isSignal()) {
                     Intent choix = new Intent(getApplicationContext(), SonActivity.class);
                     startActivity(choix);
                 } else {
                     Intent choixActivite = new Intent(getApplicationContext(), RedActivity.class);
                     startActivity(choixActivite);
                 }
             }
         }.start();
         compteur_en_cours = true;
     }

    /**
     * Méthode permettant de mettre à jour l'affichage du compteur.
     */
    private void updateCountDownText(){
        int minutes = (int)(temps_restant/1000)/60;
        int secondes = (int)(temps_restant/1000)%60;

        String temps_restant_formate = String.format(Locale.getDefault(), "%02d:%02d", minutes, secondes);

        compteur.setText(temps_restant_formate);
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
        setContentView(R.layout.activity_compteur_charge_mentale);

        context = this;

        //POUR L'ALERTE
        builder = new AlertDialog.Builder(this);

        donnees = DataManager.getInstance();         //RECUPERER INSTANCE DE DATAMANAGER
        donnee_temps = donnees.getIntervalleFinal(); //RECUPERER INTERVALLE FINAL
        temps = donnee_temps.getMinutes()*60000;     //MINUTES EN MILLISECONDES
        temps += donnee_temps.getSecondes()*1000;    //SECONDES EN MILLISECONDES
        temps_restant = temps;                       //TEMPS RESTANT INITIALISE AU TEMPS TOTAL

        //LES finViewById SERVENT A LIER LES ATTRIBUTS DE LA CLASSE AUX ELEMENTS XML
        bouton_stop = findViewById(R.id.button_stop);
        compteur = findViewById(R.id.chronometer);


        //APPEL DES MÉTHODES


        this.boutonStop();
        this.updateCountDownText();
        this.startTimer();

    }

}