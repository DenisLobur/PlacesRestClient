package denis.places.app.presenter;

import android.util.Log;
import denis.places.app.IPlacesApp;
import denis.places.app.model.Predictions;
import denis.places.app.view.PresenterView;
import denis.places.app.workers.JobCallback;
import denis.places.app.workers.PlacesJob;

import java.util.List;

/**
 * Created by denis on 4/3/16.
 */
public class PlacesPresenter extends Presenter {

    private static final String TAG = PlacesPresenter.class.getSimpleName();
    private IPlacesApp application;
    private PlacesPresenterView placesView;
    private List<Predictions> places;

    public PlacesPresenter(IPlacesApp app) {
        super(app);
        application = app;
    }


    @Override
    protected void onAttachView(PresenterView view) {
        placesView = (PlacesPresenterView)view;
        if (places != null) {
            placesView.showData(places);
        }
    }

    @Override
    protected void onDetachView() {
        placesView = null;
    }

    public void searchPlaces(String query) {
        application.getThreadPool().submit(new PlacesJob(application.getPlacesApi(), query), placesCallback);
    }

    private JobCallback<List<Predictions>> placesCallback = new JobCallback<List<Predictions>>() {
        @Override
        public void onResult(final List<Predictions> result) {
            for (Predictions predictions : result) {
                Log.d(TAG, predictions.getType().get(0));
            }

            places = result;

            if (placesView != null) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        placesView.showData(result);
                    }
                });
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onFailed(Exception ex) {

        }
    };

    public interface PlacesPresenterView extends PresenterView {
        void showData(List<Predictions> data);
    }
}
