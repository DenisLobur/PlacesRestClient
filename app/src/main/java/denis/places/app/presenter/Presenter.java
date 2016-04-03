package denis.places.app.presenter;

import android.os.Bundle;
import android.os.Handler;
import denis.places.app.IPlacesApp;
import denis.places.app.view.PresenterView;

/**
 * Created by denis on 4/3/16.
 */
public abstract class Presenter {
    private final IPlacesApp app;
    private Handler handler = new Handler();

    protected Presenter(IPlacesApp app) {
        this.app = app;
    }

    protected abstract void onAttachView(PresenterView view);
    protected abstract void onDetachView();
    public void onSaveInstanceState(Bundle outState) {}
    public void onRestoreInstanceState(Bundle outState) {}
    public void onDestroy(){}
    public void runOnUIThread(Runnable runnable) {
        if (runnable != null) {
            handler.post(runnable);
        }
    }

    public final void attach(PresenterView view) {
        onAttachView(view);
    }

    public final void dettach() {
        onDetachView();
    }

    public final void saveInstanceState(Bundle outState) {
        onSaveInstanceState(outState);
    }

    public final void restoreInstanceState(Bundle outState) {
        onRestoreInstanceState(outState);
    }

    public final void destroy() {
        onDestroy();
    }


}
