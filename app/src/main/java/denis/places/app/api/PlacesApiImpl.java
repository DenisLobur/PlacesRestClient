package denis.places.app.api;

import denis.places.app.model.Predictions;

import java.util.List;

/**
 * Created by denis on 4/3/16.
 */
public class PlacesApiImpl extends PlacesApi {

    private GoogleMapApi googleMapApi;

    public PlacesApiImpl() {
        googleMapApi = new GoogleMapApi();
    }

    @Override
    public List<Predictions> getPlaces(String place) throws Exception {
        return googleMapApi.getPlaces(place);
    }
}
