package com.pariscite.applicationisa.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.DataManager;
import com.pariscite.applicationisa.util.UserManager;

/**
 * Classe permettant de rentrer les noms des utilisateurs participants à l'experimentation.
 */


public class ChoixNomUtiActivity extends AppCompatActivity {

    //ATTRIBUTS
    UserManager donnees; //POUR RECUPERER L'INSTANCE DE USERMANAGER
    private EditText userNameEditText; //INVITE DE TEXTE POUR L'UTILISATEUR
    private Button nextButton; //BOUTON AJOUTER
    private Button boutonPrecedant;   //BOUTON PRECEDENT

    private TextView userNumberTextView;  //AFFICHAGE DU NUMERO DE L'UTILISATEUR POUR POUVOIR RENTRER SON NOM.

    private int currentUserNumber = 0; //INITIALISE L'INDICE DE L'UTILISATEURS DE DEPART

    private int totalUser = DataManager.getInstance().getNbUserFinal().getNbUser(); //RECUPERE LE NOMBRE TOTAL D'UTILISATEURS

    private AlertDialog.Builder builder; //constructeur de dialogue

    //MÉTHODES

    /**
     * Méthode permettant d'afficher le numéro de l'utilisateur dont
     * le nom doit être renseigné.
     */
    private void updateCurrentUserNumber() {
        this.userNumberTextView.setText("Utilisateur " + (currentUserNumber+1));
    }


    /**
     * Méthode permettant de récupérer le nom de l'utilisateur, et de passer à l'activité
     * suivante quand tous les noms ont été rentrés.
     */

    private void nextUser() {
        //METHODE POUR LE BOUTON AJOUTER
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString().trim(); //RECUPERE LE NOM DE L'UTILISATEUR


                if (!userName.isEmpty()) {  //REGARDE SI L'UTILISATEUR A BIEN REMPLI LE CHAMP


                    UserManager.getInstance().addUsername(currentUserNumber,userName); //RENTRE LE NOM DANS LE USERMANAGER DONNEES

                    //userNameEditText.getText().clear();

                    if (UserManager.getInstance().getUserNames().size() == totalUser) { //PERMET DE CRÉER LES UTILISATEURS EN FONCTION DU NOM ENTRÉE.

                        builder.setTitle("Alerte")
                                .setMessage("Souhaitez vous démarrer l'expérimentation ?")
                                .setCancelable(true)
                                .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        for (int i = 0; i <DataManager.getInstance().getNbUserFinal().getNbUser();i++ ) {

                                            donnees.addUser(donnees.getUserNames().get(i));
                                        }

                                        if (DataManager.getInstance().getModeFinal().isSignal()) {
                                            Intent choix = new Intent(getApplicationContext(), SonActivity.class);
                                            startActivity(choix);
                                        } else {
                                            Intent choixActivite = new Intent(getApplicationContext(), RedActivity.class);
                                            startActivity(choixActivite);
                                        }

                                    }
                                })
                                .setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        userNameEditText.getText().clear();
                                        UserManager.getInstance().getUserNames().remove(currentUserNumber);
                                    }
                                })
                                .show();

                    }


                    //AFFICHE LE PROCHAIN CHAMP DE TEXTE SEULEMENT SI TOUS LES UTILISATEURS N'ONT PAS ÉTÉ RENTRES
                    if(UserManager.getInstance().getUserNames().size() < totalUser) {
                        currentUserNumber++;
                        updateCurrentUserNumber();
                        userNameEditText.getText().clear();
                    }

                } else {
                    //DEMANDE A L'UTILISATEUR DE BIEN RENTRER TOUS LES NOMS NECESSAIRES
                    Toast.makeText(ChoixNomUtiActivity.this, "Veuillez saisir un nom d'utilisateur", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Méthode permettant de modififier le nom d'un utilisateur rentré précédemment.
     */
    private void pastUser() {
        boutonPrecedant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUserNumber > 0) {
                    currentUserNumber--;
                    updateCurrentUserNumber();
                    userNameEditText.getText().clear();
                    UserManager.getInstance().getUserNames().remove(currentUserNumber);
                }
            }
        });
    }

    /**
     * méthode permettant de supprimer le texte indicatif dans le champ de saisie du nom d'utilisateur.
     */
    private void clearText() {
        userNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    userNameEditText.setHint(" ");
                }
                else {
                    if (userNameEditText.getText().toString().isEmpty()) {
                        userNameEditText.setText("Nom d'utilisateur");
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_nom_uti);

        //POUR L'ALERTE
        builder = new AlertDialog.Builder(this);

        //LES finViewById SERVENT A LIER LES ATTRIBUTS DE LA CLASSE AUX ELEMENTS XML
        donnees = UserManager.getInstance();
        userNameEditText = findViewById(R.id.edit_text_user_name);
        nextButton = findViewById(R.id.button_next);
        boutonPrecedant = findViewById(R.id.button_past);
        userNumberTextView = findViewById(R.id.text_user_number);


        //APPEL DES MÉTHODES:


        this.nextUser();
        this.pastUser();
        this.clearText();

    }

}