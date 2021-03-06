package ca.qc.cgmatane.pictrade.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import ca.qc.cgmatane.pictrade.R;
import ca.qc.cgmatane.pictrade.controleur.ControleurAfficherCommerce;
import ca.qc.cgmatane.pictrade.donnee.Dictionnaire;
import ca.qc.cgmatane.pictrade.modele.Commerce;

public class AfficherCommerce extends AppCompatActivity implements VueAfficherCommerce, Dictionnaire, GestureDetector.OnGestureListener{
    private Commerce commerce;
    private ControleurAfficherCommerce controleurAfficherCommerce
            = new ControleurAfficherCommerce(this);

    protected Intent intentionModifierCommerce;
    protected Intent intentionPartagerCommerceDebut;
    protected Intent intentionPartagerCommerceFin;
    protected Intent intentionGalerie;
    private Bundle parametres;

    private ProgressBar vueAfficherCommerceEnAttente;
    private TextView vueAfficherNomCommerce;
    private TextView vueAfficherContactCommerce;
    private TextView vueAfficherAdresseCommerce;
    private TextView vueAfficherHoraireOuvertureCommerce;
    private TextView vueAfficherHoraireFermetureCommerce;
    private Button vueAfficherCommerceActionNaviguerPartagerCommerce;
    private Button vueAfficherCommerceActionNaviguerModifierCommerce;
    private Button vueAfficherCommerceActionNaviguerGalerie;
    private CheckBox vueAfficherCommerceMettreEnFavori;

    private boolean isFavori;
    protected GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_commerce);

        mDetector = new GestureDetectorCompat(this, this);
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
        vueAfficherCommerceEnAttente.setTranslationZ(1);
    }

    @Override
    public void afficherCommerce() {
        vueAfficherCommerceEnAttente.setVisibility(View.INVISIBLE);

        vueAfficherNomCommerce = (TextView) findViewById(R.id.vue_afficher_commerce_nom);
        vueAfficherContactCommerce = (TextView) findViewById(R.id.vue_afficher_commerce_contact);
        vueAfficherAdresseCommerce = (TextView) findViewById(R.id.vue_afficher_commerce_adresse);

        vueAfficherHoraireOuvertureCommerce = (TextView) findViewById(R.id.vue_afficher_commerce_horaire_ouverture);

        vueAfficherHoraireFermetureCommerce = (TextView) findViewById(R.id.vue_afficher_commerce_horaire_fermeture);

        vueAfficherNomCommerce.setText(commerce.getNom());
        vueAfficherContactCommerce.setText(commerce.getContact());
        vueAfficherAdresseCommerce.setText(commerce.getAdresse());
        if (commerce.getHoraireOuverture() != null){
            vueAfficherHoraireOuvertureCommerce.setText(commerce.getHoraireOuverture().toString());
        }
        if (commerce.getHoraireFermeture() != null){
            vueAfficherHoraireFermetureCommerce.setText(commerce.getHoraireFermeture().toString());
        }

        vueAfficherCommerceActionNaviguerModifierCommerce = (Button) findViewById(R.id.vue_afficher_commerce_action_naviguer_modifier_commerce);

        vueAfficherCommerceActionNaviguerModifierCommerce.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controleurAfficherCommerce.actionNaviguerModifierCommerce();
                    }
                }
        );

        vueAfficherCommerceActionNaviguerPartagerCommerce = (Button) findViewById(R.id.vue_afficher_commerce_action_naviguer_partager_commerce);

        vueAfficherCommerceActionNaviguerPartagerCommerce.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controleurAfficherCommerce.actionNaviguerPartagerCommerce();
                    }
                }
        );

        vueAfficherCommerceMettreEnFavori = (CheckBox) findViewById(R.id.vue_afficher_commerce_mettre_favori);

        vueAfficherCommerceMettreEnFavori.setChecked(controleurAfficherCommerce.isFavori());

        vueAfficherCommerceMettreEnFavori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controleurAfficherCommerce.setFavori(vueAfficherCommerceMettreEnFavori.isChecked());
            }
        });

        vueAfficherCommerceActionNaviguerGalerie = (Button) findViewById(R.id.vue_afficher_commerce_action_naviguer_galerie);

        vueAfficherCommerceActionNaviguerGalerie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controleurAfficherCommerce.actionNaviguerGalerie();
                    }
                }
        );

    }

    @Override
    public void afficherFavori(boolean bool){
        vueAfficherCommerceMettreEnFavori.setChecked(bool);

        vueAfficherCommerceActionNaviguerGalerie = (Button) findViewById(R.id.vue_afficher_commerce_action_naviguer_galerie);

        vueAfficherCommerceActionNaviguerGalerie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controleurAfficherCommerce.actionNaviguerGalerie();
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

    @Override
    public void naviguerPartagerCommerce(){
        intentionPartagerCommerceDebut = new Intent();
        intentionPartagerCommerceDebut.setAction(Intent.ACTION_SEND);

        intentionPartagerCommerceDebut.putExtra(Intent.EXTRA_TEXT, "Salut, je tenais à te partager ce lieu, je te le conseille vivement : " + commerce.getNom());

        intentionPartagerCommerceDebut.setType("text/plain");

        intentionPartagerCommerceFin = Intent.createChooser(intentionPartagerCommerceDebut, null);
        startActivity(intentionPartagerCommerceFin);

    }

    @Override
    public void naviguerGalerie() {
        intentionGalerie = new Intent(AfficherCommerce.this, Galerie.class);
        intentionGalerie.putExtra(CLE_ID_COMMERCE, commerce.getId());
        startActivity(intentionGalerie);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        controleurAfficherCommerce.actionNaviguerPartagerCommerce();
    }

    @Override
    public boolean onFling(MotionEvent movementDeDepart, MotionEvent MovementDeplacement, float velocityX, float velocityY) {
        float diffY = MovementDeplacement.getY() - movementDeDepart.getY();
        float diffX = MovementDeplacement.getX() - movementDeDepart.getX();
        if (Math.abs(diffX) > Math.abs(diffY)) {
            //swipe droite ou gauche
            if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                if (diffX > 0) {
                    onSwipeDroite();
                } else {
                    onSwipeGauche();
                }
            }
        }
        return true;
    }

    private void onSwipeGauche() {
        controleurAfficherCommerce.actionNaviguerModifierCommerce();
    }

    private void onSwipeDroite() {
        controleurAfficherCommerce.actionNaviguerGalerie();
    }

}

