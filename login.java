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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.alterac.blurkit.BlurLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    Button btnSeConnecter;
    EditText nomutilisateur, motpasse;
    ImageView txt1;
    private BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        nomutilisateur = findViewById(R.id.usrname);
        motpasse = findViewById(R.id.motepasse);
        ImageView txt1 = findViewById(R.id.register);
        btnSeConnecter = findViewById(R.id.btnSeConnecter); // avec API

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent morning = new Intent(getApplicationContext(), intrudection.class);
                startActivity(morning);
                finish();
            }
        });

        btnSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nomutilisateur.getText().toString();
                String password = motpasse.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    String message = "il y a un champ vide";
                    Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                } else {
                    seConnecter(username, password);
                }
            }
        });
    }

    public void seConnecter(String nomUtilisateur, String motDePasse) {
        Call<Integer> utilisateurCall = Apiclient.getService().seConnecter(nomUtilisateur, motDePasse);

        utilisateurCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Integer resultat = response.body();

                    if (resultat == 2) {
                        String message = "Connexion réussie";
                        Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(login.this, MainActivity.class));
                        finish();
                    } else if (resultat == 1) {
                        String message = "Mot de passe incorrect";
                        Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                    } else {
                        String message = "Utilisateur inconnu";
                        Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    String message = "Erreur de connexion";
                    Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Log.e("API Call", "Error: " + message);
                Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
