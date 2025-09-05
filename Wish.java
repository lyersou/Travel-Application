package dz.imane.travel_app;

import java.util.ArrayList;

public class Wish {
    int id_poi;
    String nom_poi = null;
    Double latitude;
    Double longitude;
    String adresse = null;
    ArrayList<String> categories; //Les noms des catégories du deuxième niveau du POI
    double distance;
    int note;
    int id_categorie;

    public int getId_poi() {
        return id_poi;
    }

    public void setId_poi(int id_poi) {
        this.id_poi = id_poi;
    }

    public String getNom_poi() {
        return nom_poi;
    }

    public void setNom_poi(String nom_poi) {
        this.nom_poi = nom_poi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }
}

