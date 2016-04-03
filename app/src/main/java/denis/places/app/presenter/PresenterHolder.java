package denis.places.app.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by denis on 4/3/16.
 */
public class PresenterHolder {

    private static final String TAG = PresenterHolder.class.getSimpleName();
    private final Map<String, Presenter> presenters = new HashMap<String, Presenter>();

    public static String getNextId() {
        return UUID.randomUUID().toString();
    }

    public void put(String id, Presenter presenter) {
        Log.d(TAG, "putting to holder id= " + id + " presenter= " + presenter);
        presenters.put(id, presenter);
    }

    public Presenter get(String id) {
        Log.d(TAG, "retrieving from holder id= " + id);
        return presenters.get(id);
    }

    public void remove(String id) {
        Log.d(TAG, "deleting from holder id= " + id);
        presenters.remove(id);
    }

    public void destroy() {
        Set<String> ids = presenters.keySet();
        for (String currentId : ids) {
            Presenter presenter = presenters.get(currentId);
            if (presenter != null) {
                presenter.destroy();
            }
        }

        presenters.clear();
    }

}
