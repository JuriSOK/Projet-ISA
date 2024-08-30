package com.pariscite.applicationisa.activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.Toast;

import com.pariscite.applicationisa.R;

/**
 * Première activité visible par l'utilisateur, permet de commencer à paramétrer l'expérimentation.
 */


//CLASSE 100% FONCTIONNELLE NE PAS TOUCHER S'IL VOUS PLAÎT.

public class MainActivity extends AppCompatActivity {

    //ATTRIBUTS:

    //DECLARATION DU BOUTON DEMARRER POUR ACCÉDER À LA PROCHAINE ACTIVITÉ
    private Button demarrer;


    //METHODES:

    /**
     * Méthode permettant de passer à l'activité de choix du mode lorsque le bouton "démarrer" est sélectionné.
     */
    private void demarrer() {
        this.demarrer.setOnClickListener(new View.OnClickListener() {
            @Override

            //METHODE POUR QUE LORSQUE QUE LE BOUTON EST CLIQUÉ, CELA NOUS REDIRIGE VERS L'ACTIVITÉ
            public void onClick(View v) {
                Intent nextActivity = new Intent(getApplicationContext(), ChoixModeActivity.class);
                startActivity(nextActivity);
            }
        });
    }

    /**
     * Méthode permettant de demander l'accès au micro du téléphone.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        //réponse de l'autorisation de la reconnaissance vocale
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission accordée",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission refusée",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ASSIGNATION DU BOUTON DEMARRER GRÂCE à l'ID
        this.demarrer = findViewById(R.id.boutonDemarrer);

        //APPEL DE LA MÉTHODE ACTIVITÉ SUIVANTE.
        this.demarrer();

    }

}