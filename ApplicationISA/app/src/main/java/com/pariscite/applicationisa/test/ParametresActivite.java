package com.pariscite.applicationisa.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.pariscite.applicationisa.R;
import com.pariscite.applicationisa.util.ChoixModeManager;
import com.pariscite.applicationisa.util.DataManager;
import com.pariscite.applicationisa.util.EchelleManager;
import com.pariscite.applicationisa.util.InfoVolManager;
import com.pariscite.applicationisa.util.NbUserManager;
import com.pariscite.applicationisa.util.SignalSonoreManager;
import com.pariscite.applicationisa.util.TimeManager;


public class ParametresActivite extends AppCompatActivity {

    private DataManager donnees;
    private TextView dureeVol;
    private TextView aeroportDepart;
    private TextView aeroportArrivee;
    private TextView nbUtilisateur;
    private TextView intervalleFinal;
    private TextView echelleFinal;
    private TextView modeSignal;
    private InfoVolManager infos;
    private NbUserManager nb;
    private TimeManager time;
    private EchelleManager echelle;
    private SignalSonoreManager signal;

    private ChoixModeManager modeVol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_activite);

        donnees = DataManager.getInstance();
        dureeVol = findViewById(R.id.dureevol);
        aeroportDepart = findViewById(R.id.aeroportdepart);
        aeroportArrivee = findViewById(R.id.aeroportarrivee);
        nbUtilisateur = findViewById(R.id.nbutilisateur);
        intervalleFinal = findViewById(R.id.intervallefinal);
        echelleFinal = findViewById(R.id.echellefinal);
        modeSignal = findViewById(R.id.modesignal);

        modeVol = donnees.getChoixModeVolFinal();
        boolean testModeVol = modeVol.isChoixMode();

        if (testModeVol) {
            infos = donnees.getInfoVol();

            dureeVol.setText(""+infos.getDureeVol());
            aeroportDepart.setText(""+infos.getAeroportDepart());
            aeroportArrivee.setText(""+infos.getAeroportArrivee());


        }



        nb = donnees.getNbUserFinal();
        nbUtilisateur.setText(""+nb.getNbUser());

        time = donnees.getIntervalleFinal();
        intervalleFinal.setText(time.getMinutes()+" : "+time.getSecondes());

        echelle = donnees.getEchelleFinal();
        echelleFinal.setText(""+echelle.getNombreBouton());

        signal = donnees.getModeFinal();
        if((signal.isSignal())==true) {
            modeSignal.setText("Mode sonore");
        }
        else{
            modeSignal.setText("Mode visuel");
        }

    }
}