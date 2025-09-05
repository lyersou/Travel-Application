package dz.imane.travel_app;

/* Ce code dÃƒÆ’Ã‚Â©finit une interface ApiService qui spÃƒÆ’Ã‚Â©cifie les mÃƒÆ’Ã‚Â©thodes HTTP
que le client Android peut utiliser pour accÃƒÆ’Ã‚Â©der aux ressources sur le serveur RESTful.
Les annotations @PUT, @POST et @GET sont utilisÃƒÆ’Ã‚Â©es pour dÃƒÆ’Ã‚Â©finir les types de requÃƒÆ’Ã‚Âªtes HTTP
 les annotations @Body et @Query sont utilisÃƒÆ’Ã‚Â©es pour spÃƒÆ’Ã‚Â©cifier les paramÃƒÆ’Ã‚Â¨tres de requÃƒÆ’Ã‚Âªte.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @PUT("utilisateur/mettre-a-jour-utilisateur")
    Call<Void> mettreAJourUtilisateur(@Body Utilisateur utilisateur);

    @POST("utilisateur/s-enregistrer")
    Call<Void> enregistrer(@Body Utilisateur utilisateur);



    @GET("utilisateur/getUtilisateur/{id_utilisateur}") // problm la .
    Call<Utilisateur> getUtilisateur(@Path("id_utilisateur") int id_utilisateur) ;

 /* @GET("utilisateur/getUtilisateur") // Suppression de {id_utilisateur} dans l'URL
  Call<Utilisateur> getUtilisateur(@Query("id_utilisateur") int id_utilisateur); // Utilisation de @Query pour passer le paramÃ¨tre de requÃªte
*/



    @GET("utilisateur/se-connecter")
    Call<Integer> seConnecter(@Query("nom_utilisateur") String nomUtilisateur, @Query("mot_passe") String motDePasse);

    @GET("utilisateur/rec")
    Call<ArrayList<Poi>> getRecommendationsList(@Query("lon") double lon,
                                                @Query("lat") double lat,
                                                @Query("id_utilisateur") int id_utilisateur,
                                                @Query("date") String date);

    @POST("utilisateur/ajouter-poi-dans-wishlist")
    Call<Void> ajouterPoiDansWishlist(@Query("id_utilisateur") int idUtilisateur, @Body Poi poi);

    @POST("utilisateur/supprimer-poi-dans-wishlist")
    Call<Void> supprimerPoiDansWishlist(@Query("id_utilisateur") int idUtilisateur, @Body Poi poi);

    @GET("utilisateur/get_Wish/{id_utilisateur}")
    Call<ArrayList<Wish>> getWish(@Path("id_utilisateur") int id_utilisateur);

    @POST("utilisateur/ajouter-poi-dans-favoris")
    Call<Void> ajouterPOIDansFavoris(@Query("id_utilisateur") int id_utilisateur, @Body Poi poi);


    @GET("utilisateur/get_Favorites/{id_utilisateur}")
    Call<ArrayList<Favorie>> getFavorites(@Path("id_utilisateur") int id_utilisateur);

    @POST("utilisateur/supprimer-poi-dans-favoris")
    Call<Void> supprimerPOIDansFavoris(@Query("id_utilisateur") int id_utilisateur, @Body Poi poi); // modifier

    @POST("utilisateur/ajouter-commentaire")
    Call<Void> ajouterCommentaire(@Query("commentaire")String commentaire, @Query("id_utilisateur") int id_utilisateur, @Body Poi poi);

    @POST("utilisateur/ajouter-note")
    Call<Void> ajouterNotePOI(@Query("id_utilisateur") int id_utilisateur, @Body Poi poi);

    @POST("utilisateur/ajouterCategorieUtilisateur")
    Call<Void> ajouterCategorieUtilisateur(@Query("id_categorie") int id_categorie, @Query("id_utilisateur") int id_utilisateur);


    @PUT("utilisateur/{id_utilisateur}/categories")
    Call<Void> mettreAjourCategorieUtilisateur(@Path("id_utilisateur") int id_utilisateur, @Body ArrayList<Integer> categories);

    @GET("utilisateur/get-categorie-utilisateur/{id_utilisateur}")
    Call<ArrayList<Categorier>> getCategorieUtilisateur(@Path("id_utilisateur") int id_utilisateur);


}
