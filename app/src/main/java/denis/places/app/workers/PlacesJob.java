package denis.places.app.workers;

import android.util.Log;
import denis.places.app.api.PlacesApi;
import denis.places.app.model.Predictions;

import java.util.List;

/**
 * Created by denis on 4/3/16.
 */
public class PlacesJob extends Job<List<Predictions>> {

    private static final String TAG = PlacesJob.class.getSimpleName();
    private PlacesApi placesApi;
    private String query;

    public PlacesJob(PlacesApi placesApi, String query) {
        this.placesApi = placesApi;
        this.query = query;
    }

    @Override
    public List<Predictions> run() throws Exception {
        List<Predictions> predictionses = null;
        try {
            predictionses = placesApi.getPlaces(query);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return predictionses;
    }
}
