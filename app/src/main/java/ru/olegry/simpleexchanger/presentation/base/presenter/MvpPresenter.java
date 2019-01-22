package ru.olegry.simpleexchanger.presentation.base.presenter;

import java.util.LinkedList;
import java.util.Queue;

import ru.olegry.simpleexchanger.presentation.base.MvpView;

public class MvpPresenter<V extends MvpView> {

    private V mView;
    private boolean isFirstAttach = true;
    private Queue<ViewCommand> commands = new LinkedList<>();

    public void attachView(V view) {
        mView = view;
        if (isFirstAttach) {
            isFirstAttach = false;
            onFirstViewAttached();
        } else {
            if (!commands.isEmpty()) {
                for (ViewCommand command : commands) {
                    command.execute();
                }
            }
        }
    }

    public void detachView() {
        mView = null;
    }

    public void onDestroy() {
        commands.clear();
    }

    protected V getMvpView() {
        return mView;
    }

    protected void onFirstViewAttached() {
    }

    protected void apply(ViewCommand command) {
        if (!(command instanceof NotRepeatableViewCommand)) commands.add(command);
        command.execute();
    }
}
