package ru.olegry.simpleexchanger.data.executors;

public interface Task<T> {
    T perform();
}
