package dz.imane.travel_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dz.imane.travel_app.ApiService;
import dz.imane.travel_app.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class categorier_inscrire extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button button;
    private List<FirstLevelItemDataModel> mList;
    private ItemAdapter adapter;
    int selectedCount;
    private CheckBox[] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorier_inscrire);

        recyclerView = findViewById(R.id.main_recyclervie);
        button = findViewById(R.id.buttonSubmit);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        //list1
        List<NestedItemDataModel> nestedList1 = new ArrayList<>();
        nestedList1.add(new NestedItemDataModel("Offices de tourisme et stations de rangers", false, 1));
        nestedList1.add(new NestedItemDataModel("Lieux emblématiques et de commémoration", false, 2));
        nestedList1.add(new NestedItemDataModel("Sites historiques et patrimoniaux", false, 3));
        nestedList1.add(new NestedItemDataModel("Centres de conférences et mairies", false, 4));
        nestedList1.add(new NestedItemDataModel("Places culturelles et musées", false, 5));
        nestedList1.add(new NestedItemDataModel("Lieux de culte touristiques", false, 6));

        List<NestedItemDataModel> nestedList2 = new ArrayList<>();
        nestedList2.add(new NestedItemDataModel("Foréts", false, 7));
        nestedList2.add(new NestedItemDataModel("Sites aquatiques", false, 8));
        nestedList2.add(new NestedItemDataModel("Montagnes", false, 9));
        nestedList2.add(new NestedItemDataModel("Déserts et dunes", false, 10));
        nestedList2.add(new NestedItemDataModel("Zones protégées et parcs nationaux", false, 11));

        List<NestedItemDataModel> nestedList3 = new ArrayList<>();
        nestedList3.add(new NestedItemDataModel("Lieu de culte", false, 12));
        nestedList3.add(new NestedItemDataModel("Parcs et zoo", false, 13));
        nestedList3.add(new NestedItemDataModel("Cinémas", false, 14));

        List<NestedItemDataModel> nestedList4 = new ArrayList<>();
        nestedList4.add(new NestedItemDataModel("Restaurants", false, 15));
        nestedList4.add(new NestedItemDataModel("Fast-food", false, 16));
        nestedList4.add(new NestedItemDataModel("Cafés", false, 17));

        List<NestedItemDataModel> nestedList5 = new ArrayList<>();
        nestedList5.add(new NestedItemDataModel("Supermarchés", false, 18));
        nestedList5.add(new NestedItemDataModel("Places de marché", false, 19));
        nestedList5.add(new NestedItemDataModel("Centres commerciaux", false, 20));
        nestedList5.add(new NestedItemDataModel("Alimentation et épicerie", false, 21));
        nestedList5.add(new NestedItemDataModel("Vètements et mode", false, 22));
        nestedList5.add(new NestedItemDataModel("Santé et beauté", false, 23));
        nestedList5.add(new NestedItemDataModel("Livres, papeterie et cadeaux", false, 24));
        nestedList5.add(new NestedItemDataModel("électronique et technologie", false, 25));
        nestedList5.add(new NestedItemDataModel("Sports et plein air", false, 26));
        nestedList5.add(new NestedItemDataModel("Magasins spécialisés", false, 27));


        mList.add(new FirstLevelItemDataModel(nestedList1, "Sites touristiques"));
        mList.add(new FirstLevelItemDataModel(nestedList2, "Attraits naturels"));
        mList.add(new FirstLevelItemDataModel(nestedList3, "Divertissement et religion"));
        mList.add(new FirstLevelItemDataModel(nestedList4, "Restauration"));
        mList.add(new FirstLevelItemDataModel(nestedList5, "Sites commerciaux"));

        adapter = new ItemAdapter(mList, this);
        recyclerView.setAdapter(adapter);

       /* checkBoxes = new CheckBox[adapter.getItemCount() * 6];
        int index = 0;
        for (FirstLevelItemDataModel item : mList) {
            List<NestedItemDataModel> nestedItems = item.getNestedList();
            for (NestedItemDataModel nestedItem : nestedItems) {
                CheckBox checkBox = new CheckBox(this);
                nestedItem.setChecked(false); // DÃ©finir isChecked Ã  false par dÃ©faut
                nestedItem.setCheckBox(checkBox);
                checkBoxes[index] = checkBox;
                index++;
            }
        }*/

      /*  for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selectedCount = 0;
                    for (CheckBox checkBox : checkBoxes) {
                        if (checkBox.isChecked()) {
                            selectedCount++;
                            int categoryId = checkBox.getId();
                            int utilisateurId = getUserIdFromSharedPreferences();
                            ajouterCategorieUtilisateur(utilisateurId, categoryId);
                        }
                    }
                }
            });
        }*/


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<NestedItemDataModel> selectedItemsList = new ArrayList();

                for(FirstLevelItemDataModel item : mList){
                    for(NestedItemDataModel nestedItemDataModel : item.getNestedList()){
                        if(nestedItemDataModel.isChecked()){
                            selectedItemsList.add(nestedItemDataModel);
                            selectedCount++;
                        }
                    }
                }

                if(selectedCount >= 5){

                    for(NestedItemDataModel item : selectedItemsList){
                        int categoryId = item.getCat_id();



                        int utilisateurId = 6; // getUserIdFromSharedPreferences();

                        ajouterCategorieUtilisateur(categoryId, utilisateurId);
                    }




                }else{

                    Toast.makeText(categorier_inscrire.this, "Veuillez choisir au moins 5 catégories pour améliorer votre expèrience.", Toast.LENGTH_LONG).show();

                }




             /*   if (selectedCount >= 5) {
                    for (CheckBox checkBox : checkBoxes) {
                        if (checkBox.isChecked()) {
                            int categoryId = checkBox.getId();
                            int utilisateurId = getUserIdFromSharedPreferences();
                            ajouterCategorieUtilisateur(utilisateurId, categoryId);

                            String message = "CatÃ©gorie enregistrÃ©e";
                            Toast.makeText(categorier_inscrire.this, message, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(categorier_inscrire.this, MainActivity.class));
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(categorier_inscrire.this, "Veuillez choisir au moins 5 catÃ©gories pour amÃ©liorer votre expÃ©rience.", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }




    public boolean onMenuItemClick(MenuItem item) {
        CheckBox checkBox = findCheckBoxByItemId(item.getItemId());
        if (checkBox != null) {
            checkBox.setChecked(!checkBox.isChecked());

            // RÃ©cupÃ©rer l'ID de la checkbox sÃ©lectionnÃ©e
            int idCategorie = checkBox.getId();

            // Envoyer l'ID de la checkbox Ã  la mÃ©thode ajouterCategorieUtilisateur
            int utilisateurId = getUserIdFromSharedPreferences();
            ajouterCategorieUtilisateur(utilisateurId, idCategorie);

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

    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id_utilisateur", -1);
    }

    private void ajouterCategorieUtilisateur(int idUtilisateur, int idCategorie) {
        // Appeler la fonction pour ajouter la catÃ©gorie sÃ©lectionnÃ©e

        Call<Void> call = Apiclient.getService().ajouterCategorieUtilisateur(idUtilisateur, idCategorie);

        System.out.println("URL: "+call.request().url());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message ="Categorie ajoutée avec succés";
                    Toast.makeText(categorier_inscrire.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(categorier_inscrire.this, MainActivity.class));
                    finish();
                } else {
                    String message = "Une erreur s'est produite. RÃ©essayez plus tard.";
                    Log.e("API Call", "Error: " + message);
                    Toast.makeText(categorier_inscrire.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "Une erreur s'est produite. RÃ©essayez plus tard.";
                Log.e("API Call", "Error: " + message);
                Toast.makeText(categorier_inscrire.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
