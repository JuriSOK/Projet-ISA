package com.pariscite.applicationisa.util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe permettant de réaliser l'envoi des données vers le serveur web.
 */

public class SauvegardeEtEnvoi {

    //http://10.0.2.2/php/index.php
    //https://reqres.in/
    private String donneesAEnvoyer;
    private String urlChaine;
    private DataManager donnees;
    private UserManager donneesUtilisateurs;
    private Context context;

    /**
     * Constructeur, permet de créer la chaine de caractère comportant les données de l'expérimentation, qui sera envoyée par la suite au serveur web.
     * @param context l'activité dans laquelle on se trouve.
     * @param url l'url cible vers le script php qui recevra la requete.
     */
    public SauvegardeEtEnvoi(Context context, String url){
        this.context = context;
        StringBuffer donneesBuffer = new StringBuffer();
        donnees = DataManager.getInstance();
        donneesUtilisateurs = UserManager.getInstance();
        donneesBuffer.append(donnees.getCodeFinal().getMotDePasse());

        this.urlChaine = url;

        if(donnees.getChoixModeVolFinal().isChoixMode()){
            donneesBuffer.append(":");
            donneesBuffer.append((int)(donnees.getInfoVol().getDureeVol())/60);
            donneesBuffer.append(".");
            donneesBuffer.append((int)(donnees.getInfoVol().getDureeVol())%60);
            donneesBuffer.append(".");
            donneesBuffer.append(donnees.getInfoVol().getAeroportDepart());
            donneesBuffer.append(".");
            donneesBuffer.append(donnees.getInfoVol().getAeroportArrivee());
        }

        for(Utilisateur u: donneesUtilisateurs.getListeUtilisateur()){
            donneesBuffer.append(":");
            donneesBuffer.append(u.getNom());
            for(int charge: u.getChargesMentales()){
                donneesBuffer.append(".");
                donneesBuffer.append(charge);
            }
        }

        donneesAEnvoyer = donneesBuffer.toString();

    }

    /**
     * Méthode permettant d'envoyer une requete HTTP vers l'url cible avec les données de l'expérimentation.
     */
    public void envoyerRequettePost(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject postData = new JSONObject();
        try {
            postData.put("fname2289A%@@oui", donneesAEnvoyer);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlChaine, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Response: "+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
