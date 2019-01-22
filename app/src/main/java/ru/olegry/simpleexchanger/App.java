package ru.olegry.simpleexchanger;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import ru.olegry.simpleexchanger.di.DependencyGraph;

public class App extends Application {

    private static DependencyGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                graph = new DependencyGraph(App.this);
            }

            @Override
            public void onActivityStarted(Activity activity) { }

            @Override
            public void onActivityResumed(Activity activity) { }

            @Override
            public void onActivityPaused(Activity activity) { }

            @Override
            public void onActivityStopped(Activity activity) { }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity.isFinishing()) graph.clear();
            }
        });
    }

    public static <T> T get(Class<T> clazz) {
        return graph.get(clazz);
    }
}
