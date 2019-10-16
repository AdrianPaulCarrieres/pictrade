package ca.qc.cgmatane.pictrade.controleur;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.PointOfInterest;

import java.util.HashMap;

import ca.qc.cgmatane.pictrade.donnee.CommerceDAO;
import ca.qc.cgmatane.pictrade.donnee.Dictionnaire;
import ca.qc.cgmatane.pictrade.modele.Commerce;
import ca.qc.cgmatane.pictrade.vue.VueAfficherCommerce;

public class ControleurAfficherCommerce implements Controleur, Dictionnaire {
    static final public int ACTIVITE_MODIFIER_COMMERCE = 1;

    private VueAfficherCommerce vue;
    private CommerceDAO accesseurCommerce;
    private Commerce commerce;
    private AsyncTask <HashMap<String,String>,String,Commerce>  recupererCommerce
            = new RecupererCommerce();

    private HashMap<String,String> parametresPost;

    public ControleurAfficherCommerce(VueAfficherCommerce vue) {
        this.vue = vue;
        this.accesseurCommerce= CommerceDAO.getInstance();
    }

    @Override
    public void onCreate(Context applicationContext) {
        Bundle parametres = vue.getParametres();
        parametresPost = new HashMap<>();

        PointOfInterest pointDInteret = (PointOfInterest) parametres.get(POINT_D_INTERET);
        if (pointDInteret != null){
            parametresPost.put(CLE_PLACEID_COMMERCE,pointDInteret.placeId);
            parametresPost.put(CLE_NOM_COMMERCE,pointDInteret.name);
            parametresPost.put(CLE_LONGITUDE_COMMERCE,pointDInteret.latLng.longitude+"");
            parametresPost.put(CLE_LATITUDE_COMMERCE,pointDInteret.latLng.longitude+"");
        }else{
            int id = (Integer) parametres.get(CLE_ID_COMMERCE);
            parametresPost.put(CLE_ID_COMMERCE,id+"");
        }

        lancerTacheRecupererCommerce();

    }

    private void lancerTacheRecupererCommerce(){
        recupererCommerce.execute(parametresPost);
    }



    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        lancerTacheRecupererCommerce();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int activite) {
        switch(activite)
        {
            case ACTIVITE_MODIFIER_COMMERCE:
                lancerTacheRecupererCommerce();
                break;
        }
    }

    public void actionNaviguerModifierCommerce() {
        vue.naviguerModifierCommerce(commerce);
    }


    private class RecupererCommerce extends AsyncTask<HashMap<String,String>,String,Commerce> {


        @Override
        protected Commerce doInBackground(HashMap<String, String>... hashMaps) {
            Commerce commerce =  accesseurCommerce.recupererCommerce(hashMaps[0]);
           return commerce;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vue.commerceEnAttente();
        }


        @Override
        protected void onPostExecute(Commerce commerceRecuperer) {
            commerce = commerceRecuperer;
            vue.setCommerce(commerce);
            vue.afficherCommerce();
            super.onPostExecute(commerceRecuperer);
        }
    }
}
