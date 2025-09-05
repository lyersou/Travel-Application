package dz.imane.travel_app;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class logo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        ImageView welcome= findViewById(R.id.welcome);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                    Intent main = new Intent(getApplicationContext(), intrudection.class);
                    startActivity(main);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }
}
