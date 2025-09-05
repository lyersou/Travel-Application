package dz.imane.travel_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {
    Button metjouruser;

    EditText nom_profile, email_profile, mtpss_profile, age_profile , rayon_profile;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        metjouruser = findViewById(R.id.mettrajour);
        nom_profile = findViewById(R.id.nam_profile);
        email_profile = findViewById(R.id.email_pr);
        mtpss_profile = findViewById(R.id.motpss_pr);
        age_profile = findViewById(R.id.age_pro);
        rayon_profile = findViewById(R.id.Radius_profile);
        drawerLayout = findViewById(R.id.drawer_prf);

        // Récupérer l'id de l'utilisateur depuis SharedPreferences
        int id_utilisateur = 2 ;
        // Appeler la méthode getUtilisateur pour obtenir les informations de l'utilisateur
        getUtilisateur(id_utilisateur);

        metjouruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = age_profile.getText().toString();
                String rayon_pr = rayon_profile.getText().toString();
                int id_user = 1;

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId_utilisateur(id_user);
               // utilisateur.setId_utilisateur(getUserIdFromSharedPreferences()); // Récupérer l'id de l'utilisateur depuis SharedPreferences
                utilisateur.setNom_utilisateur(nom_profile.getText().toString());
                utilisateur.setEmail(email_profile.getText().toString());
                utilisateur.setMot_passe(mtpss_profile.getText().toString());
                utilisateur.setAge(Integer.parseInt(age));
                utilisateur.setRayon_interet(Integer.parseInt(rayon_pr));// le rayon déclarer dans eclipse avec double pas int
                mettreAJourUtilisateur(utilisateur);
            }
        });
        findViewById(R.id.imagemenu_prf).setOnClickListener(new View.OnClickListener() {  // pour image de menu
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.naviview_profile);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {   //pour afficher le bon élément de menu lorsqu'un utilisateur clique sur un élément du menu.
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

    }

/*
    private int getUserIdFromSharedPreferences() { // pour  récupérer l'id de l'utilisateur
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE);
        // Si vous avez stocké l'id de l'utilisateur sous la clé "id_utilisateur"
        return sharedPreferences.getInt("id_utilisateur", -1); // -1 est une valeur par défaut au cas où l'id n'est pas trouvé
    } */

    // *************************
    // pour get utilisateur

    public void getUtilisateur(int id_utilisateur) {

        Call<Utilisateur> utilisateurCall = Apiclient.getService().getUtilisateur(id_utilisateur);

        utilisateurCall.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Utilisateur utilisateur = response.body();

                    if (utilisateur != null) {
                        // Afficher les informations de l'utilisateur dans les EditText
                        nom_profile.setText(utilisateur.getNom_utilisateur());
                        email_profile.setText(utilisateur.getEmail());
                        mtpss_profile.setText(utilisateur.getMot_passe());
                        age_profile.setText(String.valueOf(utilisateur.getAge()));
                        rayon_profile.setText(String.valueOf(utilisateur.getRayon_interet()));
                    } else {
                        Toast.makeText(profile.this, "Utilisateur introuvable", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(profile.this, "Erreur lors de la récupération de l'utilisateur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(profile.this, "Erreur de requête", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Error: " + t.getMessage());
            }
        });
    }


    // *************************
    // pour la mettreAJourUtilisateur
    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        Call<Void> utilisateurCall = Apiclient.getService().mettreAJourUtilisateur(utilisateur);

        utilisateurCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Mise à jour réussie";
                    Toast.makeText(profile.this, message, Toast.LENGTH_LONG).show();
                } else {
                    String message = "une erreur s'est produite. Réessayer plus tard.";
                    Toast.makeText(profile.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "une erreur s'est produite. Réessayer plus tard.";
                Log.e("API Call", "Error: " + message);
                Toast.makeText(profile.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }




    private void switchToactivity_mainLayout() {
        setContentView(R.layout.activity_main);


        navigationView.getMenu().findItem(R.id.home).setChecked(true);
    }
    private void switchToProfileLayout() {  //appelée lorsque l'utilisateur clique sur le bouton "profile" et qui changera le contenu d'activité ainsi que le layout de NavigationView.
        setContentView(R.layout.profile);


        navigationView.getMenu().findItem(R.id.profile).setChecked(true);
    }

    private void switchTocategorierLayout() {
        setContentView(R.layout.categorier_mettrajour);


        navigationView.getMenu().findItem(R.id.categ).setChecked(true);
    }

    private void switchTofavorisLayout() {
        setContentView(R.layout.favoris_list);


        navigationView.getMenu().findItem(R.id.favorier).setChecked(true);
    }
    private void switchTowishlisteLayout() {
        setContentView(R.layout.wisheliste);


        navigationView.getMenu().findItem(R.id.Wishliste).setChecked(true);
    }
    private void switchTodeconnecterLayout(){
        // Fermer l'application

        System.exit(0);
    }

}
