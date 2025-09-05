package dz.imane.travel_app;

import android.content.Context;
import android.content.SharedPreferences;

/*  l'application créerait une instance de cette classe et définirait le nom d'utilisateur et le mot de passe à l'aide des méthodes setter.
Ensuite, l'application enverrait cette instance à un serveur pour l'authentification.
 */
public class Utilisateur
{

       int id_utilisateur;
        String nom_utilisateur;
        String email;
        String mot_passe;
        int age;
        String sexe;
        int rayon_interet;


        String nom_categorie;

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_passe() {
        return mot_passe;
    }

    public void setMot_passe(String mot_passe) {
        this.mot_passe = mot_passe;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

   public int getRayon_interet() {
        return rayon_interet;
    }

    public void setRayon_interet(int rayon_interet) {
        this.rayon_interet = rayon_interet;
    }



}