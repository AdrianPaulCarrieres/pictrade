package ca.qc.cgmatane.pictrade.vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.pictrade.modele.Commerce;

public interface VueRecherche  {
    public void listeCommerceEnAttente();
    public void afficherLesCommerces();
    public void setListeCommerce(List<Commerce> listeCommerce);
    public void setListeCommercePourAdapteur(List<HashMap<String, String>> listeCommercePourAdapteur);
    public void naviguerAfficherCommerce(int idCommerce);
    public void setNomCommerce(ArrayList<Commerce> nomCommerce);
    public void setListeFavori(ArrayList<Commerce> listeFavori);
    public boolean demarrerLeFiltrage(String text);
}
