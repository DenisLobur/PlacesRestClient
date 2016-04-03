package denis.places.app.workers;

/**
 * Created by denis on 4/3/16.
 */
public interface Runnable2<T> {
    void cancel();
    boolean isCancelled();
    boolean isDone();
    T get() throws Exception;
}
