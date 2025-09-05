package dz.imane.travel_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.alterac.blurkit.BlurLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class voirPoi extends AppCompatActivity {

    private RatingBar ratingBar;
    private float userRating = 0.0f;
    private BlurLayout blurLayout1;
    private DrawerLayout drawerLayout;

    private MainActivity mainActivity ;
    EditText commentEditText , nom_poi , distance_poi ,adresse_poi;
    ImageButton submitComment;

    Poi poi;

    Button favorisButton ,wishButton;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voirpoi);
        ratingBar = findViewById(R.id.ratingBar);
        commentEditText = findViewById(R.id.comment_edittext);
        submitComment = findViewById(R.id.submitComment);
        drawerLayout = findViewById(R.id.drawer_poi);


        distance_poi = findViewById(R.id.distance);
        adresse_poi = findViewById(R.id.adresse);
        nom_poi =  findViewById(R.id.nom_du_poi);

        // RÃ©cupÃ©rer les donnÃ©es passÃ©es depuis MainActivity
        String distance = getIntent().getStringExtra("distance");
        String nom = getIntent().getStringExtra("nom");
        String adresse = getIntent().getStringExtra("adresse");
        int id_poi = getIntent().getIntExtra("id_poi", -1);
        double aLong = getIntent().getDoubleExtra("long", 0.0);
        double alat =  getIntent().getDoubleExtra("lat", 0.0);
        int id_cat = getIntent().getIntExtra("id_cat", -1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            poi = (Poi) bundle.getSerializable("poi");
        }

        // Afficher les donnÃ©es dans les champs de texte correspondants
        distance_poi.setText(distance);
        nom_poi.setText(nom);
        adresse_poi.setText(adresse);



        blurLayout1 = findViewById(R.id.blur7);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // pour interface blur
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        findViewById(R.id.imagemenu_poi).setOnClickListener(new View.OnClickListener() {  // pour image de menu
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.naviview_poi);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {   //pour afficher le bon Ã©lÃ©ment de menu lorsqu'un utilisateur clique sur un Ã©lÃ©ment du menu.
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    switchToactivity_mainLayout();
                } else if (id == R.id.profile) {
                    switchToProfileLayout();

                } else if (id == R.id.categ) {

                    switchTocategorierLayout();

                } else if (id == R.id.favorier) {
                    switchTofavorisLayout();
                } else if (id == R.id.Wishliste) {
                    switchTowishlisteLayout();
                } else if (id == R.id.deconnecter) {
                    switchTodeconnecterLayout();
                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // pour rating


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = rating;
                Toast.makeText(voirPoi.this, "Votre note est de " + rating, Toast.LENGTH_SHORT).show();
                if (rating > 0) {
                    int id_utilisateur =  1; //getUserIdFromSharedPreferences(); // RÃ©cupÃ©rer l'id de l'utilisateur depuis SharedPreferences
                    int idpoi = id_poi; // RÃ©cupÃ©rer l'id du POI
                    int noteInt = Math.round(rating);


                    //   Poi poi = new Poi();
                    //   poi.setId_poi(Integer.parseInt(String.valueOf(idpoi)));
                    poi.setNote(noteInt);
                    ajouterNotePOI(id_utilisateur,poi);
                }
            }
        });
        // pour commentaire

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                if (comment.isEmpty()) { // pour test si la champ est vide
                    Toast.makeText(voirPoi.this, "Veuillez entrer un commentaire", Toast.LENGTH_SHORT).show();
                } else {
                    int id_utilisateur = 1; //getUserIdFromSharedPreferences();

                    ajouterCommentaire(comment, id_utilisateur, poi);
                }
            }
        });

        // pour favoritis list
        favorisButton = findViewById(R.id.btnfav);   // avec API
        favorisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = Apiclient.getClient().create(ApiService.class);

                int id_utilisateur = 1;      //getUserIdFromSharedPreferences(); // RÃ©cupÃ©rer l'id de l'utilisateur depuis SharedPreferences


                Poi poi = new Poi() ;
                poi.setId_poi(Integer.parseInt(String.valueOf(id_poi)));
                poi.setAdresse(adresse);
                poi.setDistance(0);
                poi.setNom_poi(nom);
                poi.setLongitude(aLong);
                poi.setLatitude(alat);
                poi.setId_categorie(Integer.parseInt(String.valueOf(id_cat)));

                // ajouterPOIDansFavoris(id_utilisateur,poi);


                Call<Void> call = apiService.ajouterPOIDansFavoris(id_utilisateur, poi);


                System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>> Url demandÃ©e "+call.request().url());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {

                            String nompoi = poi.getNom_poi();
                            Double dis = poi.getDistance();
                            String adrr = poi.getAdresse();
                            int idpoi = poi.getId_poi();
                            int idcat = poi.getId_categorie();
                            Double lon = poi.getLongitude();
                            Double la = poi.getLatitude();

                            Favorie favorie = new Favorie();

                            favorie.setNom_poi(nompoi);
                            favorie.setId_poi(idpoi);
                            favorie.setId_categorie(idcat);
                            favorie.setLongitude(lon);
                            favorie.setLatitude(la);

                            Toast.makeText(voirPoi.this, "POi ajoutÃ© avec succÃ¨s", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(voirPoi.this, favoris_list.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(voirPoi.this, "Une erreur s'est produite lors de l'ajout . Veuillez rÃ©essayer plus tard.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(voirPoi.this, "Une erreur s'est produite lors de l'ajout . Veuillez rÃ©essayer plus tard.", Toast.LENGTH_LONG).show();
                    }
                });

            }

        });



        // pour wishlist
        wishButton = findViewById(R.id.btnwish);   // avec API
        wishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = Apiclient.getClient().create(ApiService.class);

                int id_utilisateur = 1;      //getUserIdFromSharedPreferences(); // RÃ©cupÃ©rer l'id de l'utilisateur depuis SharedPreferences


                Poi poi = new Poi() ;
                poi.setId_poi(Integer.parseInt(String.valueOf(id_poi)));
                poi.setAdresse(adresse);
                poi.setDistance(0);
                poi.setNom_poi(nom);
                poi.setLongitude(aLong);
                poi.setLatitude(alat);
                poi.setId_categorie(Integer.parseInt(String.valueOf(id_cat)));


                Call<Void> call = apiService.ajouterPoiDansWishlist(id_utilisateur, poi);


                System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>> Url demandÃ©e "+call.request().url());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {

                            String nompoi = poi.getNom_poi();
                            Double dis = poi.getDistance();
                            String adrr = poi.getAdresse();
                            int idpoi = poi.getId_poi();
                            int idcat = poi.getId_categorie();
                            Double lon = poi.getLongitude();
                            Double la = poi.getLatitude();

                            Wish wish = new Wish();

                            wish.setNom_poi(nompoi);
                            wish.setId_poi(idpoi);
                            wish.setId_categorie(idcat);
                            wish.setLongitude(lon);
                            wish.setLatitude(la);

                            Toast.makeText(voirPoi.this, "POi ajoutÃ© avec succÃ¨s", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(voirPoi.this, wisheliste.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(voirPoi.this, "Une erreur s'est produite lors de l'ajout . Veuillez rÃ©essayer plus tard.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(voirPoi.this, "Une erreur s'est produite lors de l'ajout . Veuillez rÃ©essayer plus tard.", Toast.LENGTH_LONG).show();
                    }
                });

            }

        });


    }

    private void switchToactivity_mainLayout() {
        setContentView(R.layout.activity_main);

        navigationView.getMenu().findItem(R.id.home).setChecked(true);
    }
    private void switchToProfileLayout() {  //appelÃ©e lorsque l'utilisateur clique sur le bouton "profile" et qui changera le contenu d'activitÃ© ainsi que le layout de NavigationView.
        setContentView(R.layout.profile);


        navigationView.getMenu().findItem(R.id.profile).setChecked(true);
    }

    private void switchTocategorierLayout() {
        setContentView(R.layout.categorier_mettrajour);


        navigationView.getMenu().findItem(R.id.categ).setChecked(true);
    }

    private void switchTofavorisLayout() {
        //setContentView(R.layout.favoris_list);

        navigationView.getMenu().findItem(R.id.favorier).setChecked(true);

        Intent intent = new Intent(voirPoi.this, favoris_list.class);
        startActivity(intent);
    }
    private void switchTowishlisteLayout() {
        setContentView(R.layout.wisheliste);


        navigationView.getMenu().findItem(R.id.Wishliste).setChecked(true);
    }
    private void switchTodeconnecterLayout(){
        // Fermer l'application

        System.exit(0);
    }




  /*  private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE); // le nom de SharedPreferencesNam
        return sharedPreferences.getInt("id_utilisateur", -1);
    }*/


    private int getcategorierId() {
        // RÃ©cupÃ©rer l'ID du POI actuel
        // Remplacez cette mÃ©thode par la logique appropriÃ©e pour obtenir l'ID du POI
        return 1;
    }

    private void ajouterNotePOI(int id_utilisateur, Poi poi) {
        // Remplacez cet appel par l'appel rÃ©el Ã  votre API avec les valeurs appropriÃ©es
        Call<Void> noterPoiCall = Apiclient.getService().ajouterNotePOI(id_utilisateur, poi);

        noterPoiCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "POI noté avec succés";
                    Toast.makeText(voirPoi.this, message, Toast.LENGTH_LONG).show();
                } else {
                    String message = "Une erreur s'est produite lors de la notation du POI. Veuillez réessayer plus tard.";
                    Toast.makeText(voirPoi.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "Une erreur s'est produite lors de la notation du POI. Veuillez réessayer plus tard.";
                Toast.makeText(voirPoi.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void ajouterCommentaire(String commentaire, int id_utilisateur, Poi poi) {  // pour la commentaire
        Call<Void> ajouterCommentaireCall = Apiclient.getService().ajouterCommentaire(commentaire, id_utilisateur, poi);

        System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>> "+ajouterCommentaireCall.request().url());

        ajouterCommentaireCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(voirPoi.this, "Commentaire ajouté avec succés", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(voirPoi.this, "Une erreur s'est produite lors de l'ajout du commentaire. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(voirPoi.this, "Une erreur s'est produite lors de l'ajout du commentaire. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
            }
        });
    }


}




