package dz.imane.travel_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wisheliste extends AppCompatActivity implements WishAdapter.OnWishRemoveListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ImageView img;
    private RecyclerView recyclerView;
    private WishAdapter wishAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisheliste);
        // buttonfav = findViewById(R.id.voirpoi);
        drawerLayout = findViewById(R.id.drawer_wish);
        recyclerView = findViewById(R.id.main_recyclervie_wish);

        // Set the layout manager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Create an instance of the adapter and set it to the RecyclerView
        wishAdapter = new WishAdapter(new ArrayList<Wish>(), this);

        recyclerView.setAdapter(wishAdapter);

        // Fetch the favorites from the API and update the adapter
        getWishes();

      /*  buttonfav.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
            Intent intent = new Intent(favoris_list.this, voirPoi.class);
            startActivity(intent);
            finish();
    }

    });*/

        findViewById(R.id.imagemenu_wish).setOnClickListener(new View.OnClickListener() {  // pour image de menu
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.naviview_wish);
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

    private void getWishes() {
        int id_utilisateur = 1; //getUserIdFromSharedPreferences(); // Obtenez l'ID de l'utilisateur à partir de SharedPreferences

        Call<ArrayList<Wish>> favoritesCall = Apiclient.getService().getWish(id_utilisateur);

        favoritesCall.enqueue(new Callback<ArrayList<Wish>>() {
            @Override
            public void onResponse(Call<ArrayList<Wish>> call, Response<ArrayList<Wish>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Wish> wishes = response.body();
                    if (wishes != null) {
                        // Mettez à jour l'adaptateur avec les favoris récupérés

                        wishAdapter.updatewishes(wishes);
                    }
                } else {
                    Toast.makeText(wisheliste.this, "Une erreur s'est produite lors de la récupération des favoris. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Wish>> call, Throwable t) {
                Toast.makeText(wisheliste.this, "Une erreur s'est produite lors de la récupération des favoris. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
            }
        });
    }



    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE); // le nom de SharedPreferencesNam
        return sharedPreferences.getInt("id_utilisateur", -1);
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
    //



    // ...
    @Override
    public void onWishRemove(int position) {
        Wish wishItem = wishAdapter.getItem(position);
        int id_utilisateur = 1; //getUserIdFromSharedPreferences();
        Poi poi = new Poi();
        poi.setId_poi(wishItem.getId_poi()); // Utilisez la méthode appropriée pour récupérer l'ID du POI
        poi.setId_categorie(wishItem.getId_categorie()); // Utilisez la méthode appropriée pour récupérer l'ID de la catégorie du POI
        poi.setLongitude(wishItem.getLongitude()); // Utilisez la méthode appropriée pour récupérer la longitude du POI
        poi.setLatitude(wishItem.getLatitude()); // Utilisez la méthode appropriée pour récupérer la latitude du POI

        supprimerPOIDansFavoris(id_utilisateur, poi);
    }

    private void supprimerPOIDansFavoris(int id_utilisateur, Poi poi) {
        Call<Void> PoiCall = Apiclient.getService().supprimerPoiDansWishlist(id_utilisateur, poi);

        PoiCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Suppression réussie, mettez à jour localement la liste des favoris en fonction de vos besoins
                    Toast.makeText(wisheliste.this, "POI supprimé avec succès", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(wisheliste.this, "Une erreur s'est produite lors de la suppression. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(wisheliste.this, "Une erreur s'est produite lors de la suppression. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
            }
        });
    }



}
