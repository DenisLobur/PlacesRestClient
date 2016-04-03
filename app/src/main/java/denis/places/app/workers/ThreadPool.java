package denis.places.app.workers;

import android.os.Process;
import android.util.Log;

import java.util.concurrent.*;

/**
 * Created by denis on 4/3/16.
 */
public class ThreadPool {

    private static final String TAG = ThreadPool.class.getSimpleName();
    private static final int DEFAULT_THREAD_AMMOUNT = 4;
    private static final int MAX_POOL_SIZE = 8;
    private static final long KEEP_ALIVE_TEME_SECONDS = 10l;

    private final Executor executor;

    public ThreadPool() {
        this(DEFAULT_THREAD_AMMOUNT, MAX_POOL_SIZE);
    }

    public ThreadPool(Executor executor) {
        this.executor = executor;
    }

    public ThreadPool(int initPoolSize, int maxPoolSize) {
        executor = new ThreadPoolExecutor(initPoolSize,
                maxPoolSize,
                KEEP_ALIVE_TEME_SECONDS,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND, "Thread Pool"));
    }

    public <T> Runnable2 <T> submit(Job<T> job, JobCallback<T> jobCallback) {
        Worker<T> worker = new Worker<T>(job, jobCallback);
        executor.execute(worker);

        return worker;
    }

    public <T> Runnable2 <T> submit(Job<T> job) {
        return submit(job, null);
    }

    private static class Worker<T> implements Runnable, Runnable2<T> {

        private Job<T> job;
        private JobCallback<T> jobCallback;
        private volatile boolean isCancelled;
        private boolean isDone;
        private T result;
        private Exception exception;

        public Worker(Job<T> job, JobCallback<T> jobCallback) {
            this.job = job;
            this.jobCallback = jobCallback;
        }

        @Override
        public void run() {
            T res = null;
            Exception ex = null;

            try {
                Thread.currentThread().setName(job.getClass().getName());
                res = job.run();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                ex = e;
            } finally {
                Thread.currentThread().setName("idle");
            }

            synchronized (this) {
                exception = ex;
                result = res;
                isDone = true;
                notifyAll();
            }

            if (jobCallback != null) {
                if (!isCancelled) {
                    if (ex != null) {
                        jobCallback.onFailed(ex);
                    } else {
                        jobCallback.onResult(res);
                    }
                }
            }
        }

        @Override
        public void cancel() {
            if (isCancelled) return;
            isCancelled = true;
            if (job != null) {
                job.cancel();
            }
            if (jobCallback != null) {
                jobCallback.onCancel();
            }
        }

        @Override
        public boolean isCancelled() {
            return isCancelled;
        }

        @Override
        public boolean isDone() {
            return isDone;
        }

        @Override
        public T get() throws Exception {
            while (!isDone) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                    Log.d(TAG, "Worker: ignore exception " + ie);
                }
            }

            if (exception != null) {
                throw exception;
            }

            return result;
        }

        @Override
        public String toString() {
            return String.format("Worker [Job=%s]", job);
        }
    }

    @Override
    public String toString() {
        if (executor instanceof ThreadPoolExecutor) {
            return String.format("ThreadPool [executor= %s; queue= %s]", executor, ((ThreadPoolExecutor)executor).getQueue());
        }

        return super.toString();
    }
}
