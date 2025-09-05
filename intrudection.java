package dz.imane.travel_app;

import static dz.imane.travel_app.R.id.intrubtn1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import io.alterac.blurkit.BlurLayout;

public class intrudection extends AppCompatActivity {
    Button intrbtn1, intrbtn2;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intrudection);
        Button intrbtn1 = findViewById(R.id.intrubtn1);
        Button intrbtn2 = findViewById(R.id.intrubtn2);



        intrbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent morning = new Intent(getApplicationContext(), login.class);
                startActivity(morning);
                finish();
            }
        });
        intrbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent morning = new Intent(getApplicationContext(), inscription.class);
                startActivity(morning);
                finish();
            }
        });


    }
}
