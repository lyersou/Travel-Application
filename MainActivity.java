package dz.imane.travel_app;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    final static String TAG = "Main_OSM";
    final static int PERMISSION_REQUEST_CODE = 101;

    private MapController mapController;

    private Location lastKnownLocation;  // Ajout de la variable lastKnownLocation
    private MapView mapView;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Button recommander;
    MyLocationNewOverlay locationOverlay;
    LocationManager locationManager;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        recommander = findViewById(R.id.recommandation);


        init();   // appelÃ©e pour initialiser la carte. Elle vÃ©rifie d'abord si les autorisations requises

        findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {  // pour image de menu
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.naviview);
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




        // pour get recommandation


        recommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                ApiService apiService = Apiclient.getClient().create(ApiService.class);

                int id_utilisateur = 1;

                String myFormat = "MMMM d, yyyy HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                String date = sdf.format(new Date());
                if (lastKnownLocation != null) {

                    Call<ArrayList<Poi>> call = apiService.getRecommendationsList(lastKnownLocation.getLongitude(),
                            lastKnownLocation.getLatitude(),
                            id_utilisateur,
                            date);

                    System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>> Url demandÃ©e "+call.request().url());

                    call.enqueue(new Callback<ArrayList<Poi>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Poi>> call, Response<ArrayList<Poi>> response) {
                            if (response.isSuccessful()) {
                                //L'affichage des POIs
                                ArrayList<Poi> poisList = response.body();

                                mapView.getOverlays().clear();

                                mapView.getOverlays().add(locationOverlay);

                                for(Poi poi : poisList){

                                    //

                                    GeoPoint point = new GeoPoint(poi.getLatitude(), poi.getLongitude());
                                    // Valeur d'id_categorie invalide, dÃ©finir une valeur par dÃ©faut
                                    Drawable  avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_location_on_24);

                                    String idCategorie = poi.getCategories().toString() ;
                                    System.out.println("-->>>>>>>>>>>>>>>>>>>>>>>>>>>  "+idCategorie);

                                    if (idCategorie.equalsIgnoreCase("[Offices de tourisme et stations de rangers]") ||
                                            idCategorie.equalsIgnoreCase("[Lieux emblÃ©matiques et de commÃ©moration]") ||
                                            idCategorie.equalsIgnoreCase("[Sites historiques et patrimoniaux]") ||
                                            idCategorie.equalsIgnoreCase("[Centres de confÃ©rences et mairies]") ||
                                            idCategorie.equalsIgnoreCase("[Places culturelles et musÃ©es]") ||
                                            idCategorie.equalsIgnoreCase("[Lieux de culte touristiques]")) {
                                        avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_account_balance_24);
                                    } else if (idCategorie.equalsIgnoreCase("[ForÃªts]") ||
                                            idCategorie.equalsIgnoreCase("[Sites aquatiques]") ||
                                            idCategorie.equalsIgnoreCase("[Montagnes]") ||
                                            idCategorie.equalsIgnoreCase("[DÃ©serts et dunes]") ||
                                            idCategorie.equalsIgnoreCase("[Zones protÃ©gÃ©es et parcs nationaux]")) {
                                        avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_park_24);
                                    } else if (idCategorie.equalsIgnoreCase("[Lieu de culte]") ||
                                            idCategorie.equalsIgnoreCase("[Parcs et zoo]") ||
                                            idCategorie.equalsIgnoreCase("[CinÃ©mas]")) {
                                        avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_mosque_24);
                                    } else if (idCategorie.equalsIgnoreCase("[Restaurants]") ||
                                            idCategorie.equalsIgnoreCase("[Fast-food]") ||
                                            idCategorie.equalsIgnoreCase("[CafÃ©s]")) {
                                        avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_restaurant_24);
                                    } else if (idCategorie.equalsIgnoreCase("[SupermarchÃ©s]") ||
                                            idCategorie.equalsIgnoreCase("[Places de marchÃ©]") ||
                                            idCategorie.equalsIgnoreCase("[Centres commerciaux]") ||
                                            idCategorie.equalsIgnoreCase("[Alimentation et Ã©picerie]") ||
                                            idCategorie.equalsIgnoreCase("[VÃªtements et mode]") ||
                                            idCategorie.equalsIgnoreCase("[SantÃ© et beautÃ©]") ||
                                            idCategorie.equalsIgnoreCase("[Sites aquatiques]") ||
                                            idCategorie.equalsIgnoreCase("[Livres, papeterie et cadeaux]") ||
                                            idCategorie.equalsIgnoreCase("[Ã‰lectronique et technologie]") ||
                                            idCategorie.equalsIgnoreCase("[Sports et plein air]") ||
                                            idCategorie.equalsIgnoreCase("[Magasins spÃ©cialisÃ©s]")) {
                                        avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_business_center_24);
                                    }

                                    //  Drawable avatarDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.baseline_location_on_24);
                                    Marker marker = new Marker(mapView);
                                    marker.setPosition(point);
                                    marker.setTitle(poi.getNom_poi());
                                    marker.setIcon(avatarDrawable);
                                    mapView.getOverlays().add(marker);

                                    marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                                            displayDetailsPOI(poi);
                                            return false;
                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Poi>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "ProblÃ¨me inattendu !!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // GÃ©rer le cas oÃ¹ la localisation n'est pas disponible
                    Toast.makeText(MainActivity.this, "La localisation n'est pas disponible", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }



    /*private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPreferencesName", Context.MODE_PRIVATE); // le nom de SharedPreferencesNam
        return sharedPreferences.getInt("id_utilisateur", -1);
    }*/



    private void switchToactivity_mainLayout() {
        setContentView(R.layout.activity_main);

        navigationView.getMenu().findItem(R.id.home).setChecked(true);
    }
    private void switchToProfileLayout() {  //appelÃ©e lorsque l'utilisateur clique sur le bouton "profile" et qui changera le contenu d'activitÃ© ainsi que le layout de NavigationView.
      //  setContentView(R.layout.profile);


        navigationView.getMenu().findItem(R.id.profile).setChecked(true);
        Intent intent = new Intent(MainActivity.this, profile.class);
        startActivity(intent);
    }

    private void switchTocategorierLayout() {
       // setContentView(R.layout.categorier_mettrajour);


        navigationView.getMenu().findItem(R.id.categ).setChecked(true);
        Intent intent = new Intent(MainActivity.this, categorier_mettrajour.class);
        startActivity(intent);
    }

    private void switchTofavorisLayout() {
        //setContentView(R.layout.favoris_list);

        navigationView.getMenu().findItem(R.id.favorier).setChecked(true);

        Intent intent = new Intent(MainActivity.this, favoris_list.class);
        startActivity(intent);
    }
    private void switchTowishlisteLayout() {
      //  setContentView(R.layout.wisheliste);


        navigationView.getMenu().findItem(R.id.Wishliste).setChecked(true);
        Intent intent = new Intent(MainActivity.this, wisheliste.class);
        startActivity(intent);
    }
    private void switchTodeconnecterLayout(){
        // Fermer l'application

        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mapView!=null)
            mapView.onResume();
        if(locationOverlay!=null) {
            locationOverlay.enableMyLocation();
            locationOverlay.enableFollowLocation();
            if(mapView!=null) mapView.getOverlays().add(locationOverlay);
        }
    }

    void init() {
        String[] permissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        else
            setupMap();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSION_REQUEST_CODE)
            return;
        int idxPermWriteStorage = -1;
        int idxPermAccessFineLocation = -1;
        int idxPermAccessCoarseLocation = -1;
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                idxPermWriteStorage = i;
            if (permissions[i].equals(android.Manifest.permission.ACCESS_FINE_LOCATION))
                idxPermAccessFineLocation = i;
            if (permissions[i].equals(android.Manifest.permission.ACCESS_COARSE_LOCATION))
                idxPermAccessCoarseLocation = i;

        }
        if (grantResults[idxPermWriteStorage] == PackageManager.PERMISSION_GRANTED
                && grantResults[idxPermAccessFineLocation] == PackageManager.PERMISSION_GRANTED
                && grantResults[idxPermAccessCoarseLocation] == PackageManager.PERMISSION_GRANTED)
            setupMap();
    }

    void setupMap() {
        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        HandlerThread otherThread = new HandlerThread("TraitementLocalisation");
        otherThread.start();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, otherThread.getLooper());

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        // Log.d(TAG,"Zonne level :" + mapView.getZoomLevelDouble());
        // mapView.setBuiltInZoomControls(true);
        //  mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        mapView.setMultiTouchControls(true);

        Configuration.getInstance().setUserAgentValue("Nadjib-App-POI");

        //Un MapController est utilisÃ© pour contrÃ´ler le zoom par dÃ©faut de la carte
        mapController = (MapController)mapView.getController();
        mapController.setZoom(15);

        //   GeoPoint point2 = new GeoPoint(36.19198889792566, 5.410554100448387);
        //   mapController.setCenter(point2);

        //Pour l'affichage d'un avatar rerÃ©sentant l'utilisateur (sa localisation) sur la carte
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this),mapView);

        //Pour que locationOverlay reÃ§oit des mises Ã  jour sur la position de l'utilisateur
        locationOverlay.enableMyLocation();

        //Pour indiquer que l'avatar doit suivre la position obtenue par le fournisseur dÃ©fini
        locationOverlay.enableFollowLocation();
        mapView.getOverlays().add(locationOverlay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mapView!=null)
            mapView.onPause();
        if(locationOverlay!=null) {
            locationOverlay.disableMyLocation();
            locationOverlay.disableFollowLocation();
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location!=null) {
                Log.d(TAG,"On a une nouvelle location :" + location.toString());
                //mapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
                mapView.getOverlays().add(locationOverlay);
            } else {
                Log.d(TAG,"Toujours pas de location connue");
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG,"Le statut a changé pour le provider " + provider + ", nouveau statut : "+ status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG,"Provider disponible : " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG,"Provider désactivé : " + provider);
        }
    };


    public void displayDetailsPOI(Poi poi){

        String titre="";
        if(poi.getNom_poi()==null) titre=poi.getCategories().toString();
        else titre = poi.getNom_poi();

        String message = "CatÃ©gorie: "+poi.getCategories().toString()+"\n"
                +" Distance: "+poi.getDistance()+"\n"
                +" Adresse: "+poi.getAdresse()+"\n"
                +" Latitude: "+poi.getLatitude()+"\n"
                +" Latitude: "+poi.getLongitude()+"\n";
        /*
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(MainActivity.this)
                .setTitle(titre)
                .setMessage(message)
                .setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myQuittingDialogBox.show();

        */

        Intent intent = new Intent(this, voirPoi.class);
        intent.putExtra("distance", poi.getDistance());
        intent.putExtra("nom", poi.getNom_poi());
        intent.putExtra("adresse", poi.getAdresse());
        intent.putExtra("id_poi", poi.getId_poi());
        intent.putExtra("id_cat", poi.getId_categorie());
        intent.putExtra("long", poi.getLongitude());
        intent.putExtra("lat", poi.getLatitude());

        Bundle bundle = new Bundle();
        bundle.putSerializable("poi", poi);

        intent.putExtras(bundle);


        startActivity(intent);

    }





}
