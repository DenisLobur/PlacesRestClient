package denis.places.app.api;


import denis.places.app.model.PlacesResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by denis on 4/3/16.
 */
public interface PlacesRest {

    @GET("/place/autocomplete/json")
    PlacesResponse getPlaces(@Query("input") String input,
                             @Query("key") String apiKey);
}
