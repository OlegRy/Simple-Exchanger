package ru.olegry.simpleexchanger;

import android.app.Application;

import ru.olegry.simpleexchanger.di.DependencyGraph;

public class App extends Application {

    private static DependencyGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        graph = new DependencyGraph(this);
    }

    public static <T> T get(Class<T> clazz) {
        return graph.get(clazz);
    }
}
