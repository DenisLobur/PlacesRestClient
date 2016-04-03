package denis.places.app.model;

import java.util.List;

/**
 * Created by denis on 4/3/16.
 */
public class Predictions {
    private String description;
    private List<String> types;
    private String place_id;

    public Predictions(String description, List<String> types, String place_id) {
        this.description = description;
        this.types = types;
        this.place_id = place_id;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getType() {
        return types;
    }

    public String getPlaceId() {
        return place_id;
    }
}
