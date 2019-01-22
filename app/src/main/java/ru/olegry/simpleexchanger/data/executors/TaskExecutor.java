package ru.olegry.simpleexchanger.data.executors;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    public <T> void execute(final Task<T> task, final ExecutionCallback<T> callback) {
        callback.onStarted();
        final Callable<T> callable = new Callable<T>() {
            @Override
            public T call() {
                return task.perform();
            }
        };
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final T result = callable.call();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(result);
                            callback.onTerminated();
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                            callback.onTerminated();
                        }
                    });
                }
            }
        });
    }
}
