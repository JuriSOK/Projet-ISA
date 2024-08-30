package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.Toast;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.UserManager;

/**
 * Classe permettant d'afficher le signal visuel.
 */

public class RedActivity extends AppCompatActivity {

    //MÉTHODES:

    /**
     * Méthode permettant de passer à l'activité de mesure des charges lorsque le signal visuel est terminé.
     */
    private void passerALaProchaineActivite() {
        if (UserManager.getInstance().getUserNames().size() > 1) {
            Intent nextActivity = new Intent(getApplicationContext(), MesureMultiActivity.class);
            startActivity(nextActivity);
        } else {
            Intent nextActivity = new Intent(getApplicationContext(), ChargeMentaleSeulActivity.class);
            startActivity(nextActivity);
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
        setContentView(R.layout.activity_red);


        // Création d'un objet Handler pour exécuter une tâche différée
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                passerALaProchaineActivite();
            }
        }, 2000); // 2000 millisecondes = 2 secondes
    }



}
