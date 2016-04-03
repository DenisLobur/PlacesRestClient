package denis.places.app.api;

import android.util.Log;
import denis.places.app.model.PlacesResponse;
import denis.places.app.model.Predictions;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import java.util.List;

/**
 * Created by denis on 4/3/16.
 */
public class GoogleMapApi {
    private static final String TAG = GoogleMapApi.class.getSimpleName();
    private static final String API_ENDPOINT = "https://maps.googleapis.com/maps/api";
    //private static final String API_KEY = "AIzaSyAIvNt3kCH_2T_b47FrCDq8CSLdVzZuz3c";
    private static final String API_KEY = "AIzaSyASo1B_KONERXL1gH9cJfN0O-fusmOVRzw";

    private PlacesRest placesRest;

    public GoogleMapApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .build();

        placesRest = restAdapter.create(PlacesRest.class);
    }

    public List<Predictions> getPlaces(String query) throws Exception {
        try {
            PlacesResponse response = placesRest.getPlaces(query, API_KEY);
            if (response != null) {
                return response.getPredictionses();
            }
        } catch (RetrofitError re) {
            Log.d(TAG, re.getMessage());
        }

        return null;
    }
}
