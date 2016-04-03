package denis.places.app;

import android.content.Context;
import denis.places.app.api.PlacesApi;
import denis.places.app.presenter.PresenterHolderProvider;
import denis.places.app.workers.ThreadPool;

/**
 * Created by denis on 4/3/16.
 */
public interface IPlacesApp extends PresenterHolderProvider {
    Context getApplicationContext();
    ThreadPool getThreadPool();
    PlacesApi getPlacesApi();
}
