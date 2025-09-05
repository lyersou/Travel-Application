package dz.imane.travel_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class categorier_mettrajour extends AppCompatActivity {
    Button btn8;

    private RecyclerView recyclerView;
    private Button button;
    private List<FirstLevelItemDataModel> mList;
    private ItemAdapter adapter;
    int selectedCount;
    private CheckBox[] checkBoxes;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Button btn7 ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorier_mettrajour);

        btn7 = findViewById(R.id.buttonSubmit1); // avec api

        drawerLayout = findViewById(R.id.drawer_cat);


        recyclerView = findViewById(R.id.main_recyclervie1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        // list1
        List<NestedItemDataModel> nestedList1 = new ArrayList<>();
        nestedList1.add(new NestedItemDataModel("Offices de tourisme et stations de rangers", false, 1));
        nestedList1.add(new NestedItemDataModel("Lieux emblÃ©matiques et de commÃ©moration", false, 2));
        nestedList1.add(new NestedItemDataModel("Sites historiques et patrimoniaux", false, 3));
        nestedList1.add(new NestedItemDataModel("Centres de confÃ©rences et mairies", false, 4));
        nestedList1.add(new NestedItemDataModel("Places culturelles et musÃ©es", false, 5));
        nestedList1.add(new NestedItemDataModel("Lieux de culte touristiques", false, 6));

        List<NestedItemDataModel> nestedList2 = new ArrayList<>();
        nestedList2.add(new NestedItemDataModel("ForÃªts", false, 7));
        nestedList2.add(new NestedItemDataModel("Sites aquatiques", false, 8));
        nestedList2.add(new NestedItemDataModel("Montagnes", false, 9));
        nestedList2.add(new NestedItemDataModel("DÃ©serts et dunes", false, 10));
        nestedList2.add(new NestedItemDataModel("Zones protÃ©gÃ©es et parcs nationaux", false, 11));

        List<NestedItemDataModel> nestedList3 = new ArrayList<>();
        nestedList3.add(new NestedItemDataModel("Lieu de culte", false, 12));
        nestedList3.add(new NestedItemDataModel("Parcs et zoo", false, 13));
        nestedList3.add(new NestedItemDataModel("CinÃ©mas", false, 14));

        List<NestedItemDataModel> nestedList4 = new ArrayList<>();
        nestedList4.add(new NestedItemDataModel("Restaurants", false, 15));
        nestedList4.add(new NestedItemDataModel("Fast-food", false, 16));
        nestedList4.add(new NestedItemDataModel("CafÃ©s", false, 17));

        List<NestedItemDataModel> nestedList5 = new ArrayList<>();
        nestedList5.add(new NestedItemDataModel("SupermarchÃ©s", false, 18));
        nestedList5.add(new NestedItemDataModel("Places de marchÃ©", false, 19));
        nestedList5.add(new NestedItemDataModel("Centres commerciaux", false, 20));
        nestedList5.add(new NestedItemDataModel("Alimentation et Ã©picerie", false, 21));
        nestedList5.add(new NestedItemDataModel("VÃªtements et mode", false, 22));
        nestedList5.add(new NestedItemDataModel("SantÃ© et beautÃ©", false, 23));
        nestedList5.add(new NestedItemDataModel("Livres, papeterie et cadeaux", false, 24));
        nestedList5.add(new NestedItemDataModel("Ã‰lectronique et technologie", false, 25));
        nestedList5.add(new NestedItemDataModel("Sports et plein air", false, 26));
        nestedList5.add(new NestedItemDataModel("Magasins spÃ©cialisÃ©s", false, 27));


        mList.add(new FirstLevelItemDataModel(nestedList1, "Sites touristiques"));
        mList.add(new FirstLevelItemDataModel(nestedList2, "Attraits naturels"));
        mList.add(new FirstLevelItemDataModel(nestedList3, "Divertissement et religion"));
        mList.add(new FirstLevelItemDataModel(nestedList4, "Restauration"));
        mList.add(new FirstLevelItemDataModel(nestedList5, "Sites commerciaux"));


        int id_utilisateur = 1 ;
        getCategorieUtilisateur(id_utilisateur);


        adapter = new ItemAdapter(mList, this);
        recyclerView.setAdapter(adapter);

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selectedCategories = new ArrayList<>();

                for (FirstLevelItemDataModel item : mList) {
                    for (NestedItemDataModel nestedItem : item.getNestedList()) {
                        if (nestedItem.isChecked()) {
                            selectedCategories.add(nestedItem.getCat_id());
                            selectedCount++;
                        }
                    }
                }

                if (selectedCount >= 5) {
                    //  int utilisateurId = getUserIdFromSharedPreferences();
                    int utilisateurId = 1;
                    mettreAjourCategorieUtilisateur(utilisateurId, selectedCategories);
                    Intent morning = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(morning);
                    finish();
                } else {
                    Toast.makeText(categorier_mettrajour.this, "Veuillez choisir au moins 5 catÃ©gories pour amÃ©liorer votre expÃ©rience.", Toast.LENGTH_LONG).show();
                }
            }

        });

        findViewById(R.id.imagemenu_cat).setOnClickListener(new View.OnClickListener() {  // pour image de menu
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.naviview_cat);
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
    }

    public boolean onMenuItemClick(MenuItem item) {
        CheckBox checkBox = findCheckBoxByItemId(item.getItemId());
        if (checkBox != null) {
            checkBox.setChecked(!checkBox.isChecked());

            // RÃ©cupÃ©rer l'ID de la checkbox sÃ©lectionnÃ©e
            int idCategorie = checkBox.getId();

            // Envoyer l'ID de la checkbox Ã  la mÃ©thode mettreAjourCategorieUtilisateur
            // int utilisateurId = getUserIdFromSharedPreferences();
            int utilisateurId = 1;
            mettreAjourCategorieUtilisateur(utilisateurId, new ArrayList<Integer>(){{ add(idCategorie); }});

            return true;
        }
        return false;
    }

    private CheckBox findCheckBoxByItemId(int itemId) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox != null && checkBox.getId() == itemId) {
                return checkBox;
            }
        }
        return null;
    }

    private int getCategoryId(CheckBox checkBox) {
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBox != null && checkBox == checkBoxes[i]) {
                return i + 1;
            }
        }
        return -1;
    }

   /* private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id_utilisateur", -1);
    }*/

    private void mettreAjourCategorieUtilisateur(int idUtilisateur, ArrayList<Integer> categories) {
        // Appeler la fonction pour mettre Ã  jour les catÃ©gories de l'utilisateur

        Call<Void> call = Apiclient.getService().mettreAjourCategorieUtilisateur(idUtilisateur, categories);

        System.out.println("URL: " + call.request().url());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API", "CatÃ©gories mises Ã  jour avec succÃ¨s");
                } else {
                    String message = "Une erreur s'est produite. RÃ©essayez plus tard  kkk.";
                    Log.e("API Call", "Error: " + message);
                    Toast.makeText(categorier_mettrajour.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "Une erreur s'est produite. RÃ©essayez plus tard.";
                Log.e("API Call", "Error: " + message);
                Toast.makeText(categorier_mettrajour.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Method to get user categories and update checkboxes
    private void getCategorieUtilisateur(int id_utilisateur) {
        Call<ArrayList<Categorier>> call = Apiclient.getService().getCategorieUtilisateur(id_utilisateur);
        call.enqueue(new Callback<ArrayList<Categorier>>() {
            @Override
            public void onResponse(Call<ArrayList<Categorier>> call, Response<ArrayList<Categorier>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Categorier> categories = response.body();
                    updateCheckboxStates(categories);
                } else {
                    Toast.makeText(categorier_mettrajour.this, "Une erreur s'est produite lors de la rÃ©cupÃ©ration des catÃ©gories de l'utilisateur    kkk.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Categorier>> call, Throwable t) {
                Toast.makeText(categorier_mettrajour.this, "Une erreur s'est produite lors de la rÃ©cupÃ©ration des catÃ©gories de l'utilisateur.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateCheckboxStates(ArrayList<Categorier> categories) {
        for (Categorier category : categories) {
            int categoryId = category.getid_categorie();
            findCheckBoxById(categoryId);
        }
    }


    private void findCheckBoxById(int categoryId) {
        for (FirstLevelItemDataModel item : mList) {
            for (NestedItemDataModel nestedItem : item.getNestedList()) {
                if (nestedItem.getCat_id() == categoryId) {
                    nestedItem.setChecked(true);
                }
            }
        }
    }


    private void switchToactivity_mainLayout() {
        setContentView(R.layout.activity_main);

        navigationView.getMenu().findItem(R.id.home).setChecked(true);
    }
    private void switchToProfileLayout() {  //appelÃƒÂ©e lorsque l'utilisateur clique sur le bouton "profile" et qui changera le contenu d'activitÃƒÂ© ainsi que le layout de NavigationView.
        //  setContentView(R.layout.profile);


        navigationView.getMenu().findItem(R.id.profile).setChecked(true);
        Intent intent = new Intent(categorier_mettrajour.this, profile.class);
        startActivity(intent);
    }

    private void switchTocategorierLayout() {
        // setContentView(R.layout.categorier_mettrajour);


        navigationView.getMenu().findItem(R.id.categ).setChecked(true);
        Intent intent = new Intent(categorier_mettrajour.this, categorier_mettrajour.class);
        startActivity(intent);
    }

    private void switchTofavorisLayout() {
        //setContentView(R.layout.favoris_list);

        navigationView.getMenu().findItem(R.id.favorier).setChecked(true);

        Intent intent = new Intent(categorier_mettrajour.this, favoris_list.class);
        startActivity(intent);
    }
    private void switchTowishlisteLayout() {
        //  setContentView(R.layout.wisheliste);


        navigationView.getMenu().findItem(R.id.Wishliste).setChecked(true);
        Intent intent = new Intent(categorier_mettrajour.this, wisheliste.class);
        startActivity(intent);
    }
    private void switchTodeconnecterLayout(){
        // Fermer l'application

        System.exit(0);
    }
}
