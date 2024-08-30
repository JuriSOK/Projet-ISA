package com.pariscite.applicationisa.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.UserManager;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Classe permettant d'afficher le signal sonore.
 */

public class SonActivity extends AppCompatActivity {

    //ATTRIBUTS:
    private MediaPlayer mediaPlayer; //ATTRIBUT POUR GÉRER LA LECTURE DU MÉDIA.

    //MÉTHODES:

    /**
     * Méthode permettant de passer à l'activité de mesure des charges lorsque le signal sonore est terminé.
     */

    public void passerALaProchaineActivite() {
        if (UserManager.getInstance().getUserNames().size() > 1) {
            Intent nextActivity = new Intent(getApplicationContext(), MesureMultiActivity.class);
            startActivity(nextActivity);
        } else {
            Intent nextActivity = new Intent(getApplicationContext(), ChargeMentaleSeulActivity.class);
            startActivity(nextActivity);
        }
    }

    /**
     * Méthode permettant de libérer le lecteur audio à la fin du signal sonore.
     */
    protected void onDestroy() {
        super.onDestroy();
        // Libérer les ressources du lecteur audio
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Méthode permettant de bloquer l'utilisation du bouton retour des téléphones android, ce bouton
     * pouvant être la source d'erreurs et de bugs.
     */

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), "Retour impossible", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son);

        mediaPlayer = MediaPlayer.create(this, R.raw.son2);//l'audio a été mis dans le fichier raw
        mediaPlayer.start(); //lancer l'audio

        // Création d'un objet Handler pour exécuter une tâche différée
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop(); //mettre fin à l'audio
                mediaPlayer.release(); //arreter la lecture du son
                passerALaProchaineActivite(); //passer à la methode suivante
            }
        }, 5000); // 5000 millisecondes = 5 secondes

    }


}