package com.pariscite.applicationisa.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.UserManager;
import com.pariscite.applicationisa.util.Utilisateur;

import java.util.ArrayList;

public class MesureTestMultiActivity extends AppCompatActivity {

    ArrayList<Utilisateur> listeUtilisateurs;

    LinearLayout layoutChargesMentales;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesure_test_multi);

        listeUtilisateurs = UserManager.getInstance().getListeUtilisateur();

        layoutChargesMentales = findViewById(R.id.layout_charges_mentales);

        for (Utilisateur utilisateur : listeUtilisateurs) {
            StringBuffer chargesMentalesText = new StringBuffer("Charges mentales de " + utilisateur.getNom() + ": ");
            ArrayList<Integer> chargesMentales = utilisateur.getChargesMentales();
            for (int i = 0; i < chargesMentales.size(); i++) {
                chargesMentalesText.append(chargesMentales.get(i));
                if (i < chargesMentales.size() - 1) {
                    chargesMentalesText.append(", ");
                }
            }
            TextView textView = new TextView(this);
            textView.setText(chargesMentalesText.toString());
            layoutChargesMentales.addView(textView);
        }
    }

}