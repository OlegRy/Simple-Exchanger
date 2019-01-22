package ru.olegry.simpleexchanger.presentation.base;

public class MvpPresenter<V extends MvpView> {

    private V mView;

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }

    public void onDestroy() {}

    protected V getMvpView() {
        return mView;
    }
}
