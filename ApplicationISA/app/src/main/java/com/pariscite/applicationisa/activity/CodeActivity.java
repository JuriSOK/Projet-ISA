package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.CodeManager;
import com.pariscite.applicationisa.util.DataManager;
import com.pariscite.applicationisa.util.SauvegardeEtEnvoi;

import java.time.LocalDateTime;

/**
 * Classe permettant de créer et d'afficher le code de connexion au module, elle permet aussi de faire la requete HTTP permettant l'envoi des données.
 */

public class CodeActivity extends AppCompatActivity {

    //ATTRIBUTS:

    TextView vueCode;
    CodeManager code;

    DataManager donnees;

    //ATTRIBUT AFIN D'OBTENIR LA DATE DU JOUR DE L'EXPÉRIMENTATION
    LocalDateTime date;

    /**
     * Méthode permettant de bloquer l'utilisation du bouton retour des téléphones android, ce bouton
     * pouvant être la source d'erreurs et de bugs.
     */

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), "Retour impossible", Toast.LENGTH_SHORT).show();
    }

    /**
     * Méthode permettant de créer l'activité, elle créée aussi le code pour se connecter au module
     * et fait la requette HTTP pour l'envoi des données vers le module.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        date = LocalDateTime.now();

        vueCode = findViewById(R.id.codeView);
        code = new CodeManager();
        donnees = DataManager.getInstance();

        //condition pour regarder si le mode vol est activé, car le code est différent en fonction des cas
        if (donnees.getChoixModeVolFinal().isChoixMode()) {
            code.creationMdpAvion(donnees.getNbUserFinal().getNbUser(),donnees.getInfoVol().getAeroportDepart(),donnees.getInfoVol().getAeroportArrivee(),donnees.getEchelleFinal().getNombreBouton(),date.getDayOfMonth(),date.getMonthValue(),date.getYear());
        }
        else {
            code.creationMdpNormal(donnees.getNbUserFinal().getNbUser(),donnees.getEchelleFinal().getNombreBouton(),donnees.getIntervalleFinal().getSecondes(),date.getDayOfMonth(),date.getMonthValue(),date.getYear());
        }

        //enregistrement du code dans l'instance de DataManager
        donnees.setCodeFinal(code);

        //instanciation de la classe SauvegardeEtEnvoi avec l'url cible
        SauvegardeEtEnvoi sauv = new SauvegardeEtEnvoi(this, "https://aymericl.fr/php/scriptcible.php");
        //Requette pour envoyer les données vers le module d'exploitation des données
        sauv.envoyerRequettePost();

        //affichage du mot de passe
        vueCode.setText(code.getMotDePasse());

    }
}