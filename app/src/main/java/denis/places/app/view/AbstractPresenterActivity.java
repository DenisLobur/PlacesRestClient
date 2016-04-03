package denis.places.app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import denis.places.app.IPlacesApp;
import denis.places.app.presenter.Presenter;
import denis.places.app.presenter.PresenterHolder;

/**
 * Created by denis on 4/3/16.
 */
public abstract class AbstractPresenterActivity extends AppCompatActivity {

    private static final String TAG = AbstractPresenterActivity.class.getSimpleName();
    private static final String PRESENTER_ID = "presenter_id";

    private IPlacesApp app;
    private Presenter presenter;
    private String presenterId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IPlacesApp placesApp = getApp();
        PresenterHolder presenterHolder = placesApp.getPresenterHolder();

        if (savedInstanceState == null) {
            presenter = onCreatePresenter();
            presenterId = PresenterHolder.getNextId();
            presenterHolder.put(presenterId, presenter);
            Log.d(TAG, "onCreate: new presenter with id= " + presenterId);
        } else {
            presenterId = getPresenterId(savedInstanceState);
            Log.d(TAG, "onCreate: saved presenter with id= " + presenterId);
            presenter = presenterHolder.get(presenterId);
            if (presenter == null) {
                presenterId = PresenterHolder.getNextId();
                presenter = onCreatePresenter();
                presenterHolder.put(presenterId, presenter);
            } else {
                Log.d(TAG, "onCreate: presenter exists and functions, id= " + presenterId);
            }

            presenter.restoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.attach(getPresenterView());
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.dettach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isFinishing()) {
            if (presenter != null) {
                presenter.destroy();
            }

            IPlacesApp placesApp = getApp();
            PresenterHolder presenterHolder = placesApp.getPresenterHolder();
            presenterHolder.remove(presenterId);

            presenter = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PRESENTER_ID, presenterId);
        presenter.onSaveInstanceState(outState);
    }

    public Presenter onCreatePresenter() {
        Log.d(TAG, "onCreatePresenter");
        return new DefaultPresenter(app);
    }

    public PresenterView getPresenterView() {
        return new DefaultPresenterView();
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public IPlacesApp getApp() {
        if (app == null) {
            app = (IPlacesApp) getApplication();
        }

        return app;
    }

    private static class DefaultPresenter extends Presenter {

        public DefaultPresenter(IPlacesApp app) {
            super(app);
        }

        @Override
        protected void onAttachView(PresenterView view) {

        }

        @Override
        protected void onDetachView() {

        }
    }

    private static class DefaultPresenterView implements PresenterView {

    }

    private String getPresenterId(Bundle state) {
        return state.getString(PRESENTER_ID);
    }
}
