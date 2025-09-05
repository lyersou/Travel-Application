
package dz.imane.travel_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dz.imane.travel_app.Apiclient;
import dz.imane.travel_app.MainActivity;
import dz.imane.travel_app.R;
import dz.imane.travel_app.Utilisateur;
import dz.imane.travel_app.intrudection;
import io.alterac.blurkit.BlurLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class inscription extends AppCompatActivity {
    EditText nom_utilisateur, mot_passe, age_insc, email , Rayon_interet;

    Button btninsciption;
    RadioGroup rad1;
    ImageView txt2;

    private BlurLayout blurLayout1;
    private Object message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        nom_utilisateur = findViewById(R.id.nom);
        email = findViewById(R.id.editTextEmail);
        mot_passe = findViewById(R.id.editmotpss);
        age_insc = findViewById(R.id.editTextAge);
        ImageView txt2 = findViewById(R.id.back);
        btninsciption = findViewById(R.id.inscrire); // Api
        rad1 = findViewById(R.id.radioGroupGender);
        Rayon_interet = findViewById(R.id.editTextRadius);

        blurLayout1 = findViewById(R.id.blur1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // pour interface blur
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent morning = new Intent(getApplicationContext(), intrudection.class);
                startActivity(morning);
                finish();
            }
        });
        btninsciption.setOnClickListener(new View.OnClickListener() { // for API method
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nom_utilisateur.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(mot_passe.getText().toString()) || TextUtils.isEmpty(age_insc.getText().toString()) || rad1.getCheckedRadioButtonId() == -1) {
                    String message = "il y a un champ vide ";
                    Toast.makeText(inscription.this, message, Toast.LENGTH_LONG).show();
                } else {
                    String age = age_insc.getText().toString();
                    int selectedGenderId = rad1.getCheckedRadioButtonId();
                    RadioButton selectedGender = findViewById(selectedGenderId);
                    String sexe = selectedGender.getText().toString();
                    String rayon = Rayon_interet.getText().toString();

                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setNom_utilisateur(nom_utilisateur.getText().toString());
                    utilisateur.setEmail(email.getText().toString());
                    utilisateur.setMot_passe(mot_passe.getText().toString());
                    utilisateur.setAge(Integer.parseInt(age));
                    utilisateur.setSexe(sexe);
                    utilisateur.setRayon_interet(Integer.parseInt(rayon));// le rayon déclarer dans eclipse avec double pas int
                    enregistrer(utilisateur);
                }
            }
        });
    }

    public void enregistrer(Utilisateur utilisateur) {
        Call<Void> utilisateurCall = Apiclient.getService().enregistrer(utilisateur);

        utilisateurCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "inscription réussie";
                    Toast.makeText(inscription.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(inscription.this, categorier_inscrire.class));
                    finish();
                } else {
                    String message = "une erreur s'est produite. Réessayer plus tard kkk.";
                    Log.e("API Call", "Error: " + message);
                    Toast.makeText(inscription.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "une erreur s'est produite. Réessayer plus tard.";
                Log.e("API Call", "Error: " + message);
                Toast.makeText(inscription.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

