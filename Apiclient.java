package dz.imane.travel_app;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;  // pour convertir les réponses JSON en objets Java

public class Apiclient {

    private static final String BASE_URL = "http://192.168.97.14:8080/recommandation/monapi/"; //  "http://192.168.8.104:8080/   http://10.0.2.2:8080/
    private static Retrofit retrofit = null;

    public static Retrofit getClient() { // vérifie si l'objet retrofit a été créé.

        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.client(httpClient.build()).build();
        }
        return retrofit;
    }
    public static ApiService getService(){
        ApiService apiService = getClient().create(ApiService.class);
        return apiService;
    }
}

/*  la classe ApiClient garantit qu'une
seule instance de Retrofit est créée pendant toute la durée de vie de l'application .
 */
