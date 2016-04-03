package denis.places.app;

import android.app.Application;
import denis.places.app.api.PlacesApi;
import denis.places.app.api.PlacesApiImpl;
import denis.places.app.presenter.PresenterHolder;
import denis.places.app.workers.ThreadPool;

/**
 * Created by denis on 4/3/16.
 */
public class PlacesApplication extends Application implements IPlacesApp {

    private PresenterHolder presenterHolder;
    private ThreadPool threadPool;
    private PlacesApi mapApi;

    @Override
    public void onCreate() {
        super.onCreate();

        presenterHolder = new PresenterHolder();
        threadPool = new ThreadPool();
        mapApi = new PlacesApiImpl();
    }

    public PresenterHolder getPresenterHolder() {
        return presenterHolder;
    }

    @Override
    public ThreadPool getThreadPool() {
        return threadPool;
    }

    @Override
    public PlacesApi getPlacesApi() {
        return mapApi;
    }
}
