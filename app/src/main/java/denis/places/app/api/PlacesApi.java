package denis.places.app.api;

import denis.places.app.model.Predictions;

import java.util.List;

/**
 * Created by denis on 4/3/16.
 */
public abstract class PlacesApi {
    public abstract List<Predictions> getPlaces(String place) throws Exception;
}
