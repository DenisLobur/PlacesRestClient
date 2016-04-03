package denis.places.app.workers;

/**
 * Created by denis on 4/3/16.
 */
public interface JobCallback<T> {
    void onResult(T result);
    void onCancel();
    void onFailed(Exception ex);
}
