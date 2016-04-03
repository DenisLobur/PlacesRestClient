package denis.places.app.workers;

import android.os.Process;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by denis on 4/3/16.
 */
public class PriorityThreadFactory implements ThreadFactory {

    private final int priority;
    private final AtomicInteger number = new AtomicInteger();
    private final String threadName;

    public PriorityThreadFactory(int priority, String threadName) {
        this.priority = priority;
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadName + " -> " + number.getAndIncrement()) {
            @Override
            public void run() {
                Process.setThreadPriority(priority);
                super.run();
            }
        };
    }

}
