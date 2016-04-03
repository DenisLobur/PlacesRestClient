package denis.places.app.workers;

/**
 * Created by denis on 4/3/16.
 */
public abstract class Job<T> {
    public abstract T run() throws Exception;
    public void cancel() {}
}
