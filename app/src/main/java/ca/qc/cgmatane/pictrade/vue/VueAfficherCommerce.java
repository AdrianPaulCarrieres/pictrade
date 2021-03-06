package ca.qc.cgmatane.pictrade.vue;

import android.os.Bundle;

import ca.qc.cgmatane.pictrade.modele.Commerce;

public interface VueAfficherCommerce {
    public Bundle getParametres();
    public void commerceEnAttente();
    public void afficherCommerce();
    public void setCommerce(Commerce commerce);
    public void naviguerModifierCommerce(Commerce commerce);
    public void naviguerPartagerCommerce();
    public void afficherFavori(boolean bool);
    public void naviguerGalerie();
}
