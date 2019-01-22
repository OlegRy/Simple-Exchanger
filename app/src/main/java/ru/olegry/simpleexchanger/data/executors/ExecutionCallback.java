package ru.olegry.simpleexchanger.data.executors;

public interface ExecutionCallback<T> {
    void onSuccess(T result);
    void onError(Throwable t);
    void onTerminated();
    void onStarted();
}
