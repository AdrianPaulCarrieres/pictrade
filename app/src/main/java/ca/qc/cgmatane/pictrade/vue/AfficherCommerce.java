package ca.qc.cgmatane.pictrade.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import ca.qc.cgmatane.pictrade.R;
import ca.qc.cgmatane.pictrade.controleur.ControleurAfficherCommerce;
import ca.qc.cgmatane.pictrade.donnee.Dictionnaire;
import ca.qc.cgmatane.pictrade.modele.Commerce;

public class AfficherCommerce extends AppCompatActivity implements VueAfficherCommerce, Dictionnaire {
    private Commerce commerce;
    private ControleurAfficherCommerce controleurAfficherCommerce
            = new ControleurAfficherCommerce(this);

    protected Intent intentionModifierCommerce;
    private Bundle parametres;

    private ProgressBar vueAfficherCommerceEnAttente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_commerce);

        parametres = this.getIntent().getExtras();
        vueAfficherCommerceEnAttente =
                (ProgressBar) findViewById(R.id.vue_afficher_commerce_en_attente);
        controleurAfficherCommerce.onCreate(getApplicationContext());



    }


    @Override
    public Bundle getParametres() {
        return parametres;
    }

    @Override
    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }


    @Override
    public void commerceEnAttente() {
        vueAfficherCommerceEnAttente.setVisibility(View.VISIBLE);
    }

    @Override
    public void afficherCommerce() {
        vueAfficherCommerceEnAttente.setVisibility(View.INVISIBLE);

        TextView vueAfficherNomCommerce = (TextView) findViewById(R.id.vue_afficher_commerce_nom);
        TextView vueAfficherContactCommerce =
                (TextView) findViewById(R.id.vue_afficher_commerce_contact);
        TextView vueAfficherAdresseCommerce =
                (TextView) findViewById(R.id.vue_afficher_commerce_adresse);
        TextView vueAfficherHoraireOuvertureCommerce =
                (TextView) findViewById(R.id.vue_afficher_commerce_horaire_ouverture);
        TextView vueAfficherHoraireFermetureCommerce =
                (TextView) findViewById(R.id.vue_afficher_commerce_horaire_fermeture);

        vueAfficherNomCommerce.setText(commerce.getNom());
        vueAfficherContactCommerce.setText(commerce.getContact());
        vueAfficherAdresseCommerce.setText(commerce.getAdresse());
        if (commerce.getHoraireOuverture() != null){
            vueAfficherHoraireOuvertureCommerce.setText(commerce.getHoraireOuverture().toString());
        }
        if (commerce.getHoraireFermeture() != null){
            vueAfficherHoraireFermetureCommerce.setText(commerce.getHoraireFermeture().toString());
        }

        Button vueAfficherCommerceActionNaviguerModifierCommerce =
                (Button) findViewById(R.id.vue_afficher_commerce_action_naviguer_modifier_commerce);

        vueAfficherCommerceActionNaviguerModifierCommerce.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controleurAfficherCommerce.actionNaviguerModifierCommerce();
                    }
                }
        );
    }


    protected void onActivityResult(int activite, int resultat, Intent donnees) {
        super.onActivityResult(activite, resultat, donnees);
        controleurAfficherCommerce.onActivityResult(activite);
    }

    @Override
    public void naviguerModifierCommerce(Commerce commerce) {
        intentionModifierCommerce = new Intent(AfficherCommerce.this, ModifierCommerce.class);
        intentionModifierCommerce.putExtra(CLE_COMMERCE, commerce.obtenirCommerceHashMap());
        startActivityForResult(intentionModifierCommerce,ControleurAfficherCommerce.ACTIVITE_MODIFIER_COMMERCE);
    }
}

