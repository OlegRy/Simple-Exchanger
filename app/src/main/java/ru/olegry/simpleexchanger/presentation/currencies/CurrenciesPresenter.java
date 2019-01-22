package ru.olegry.simpleexchanger.presentation.currencies;

import java.util.List;

import ru.olegry.simpleexchanger.data.executors.ExecutionCallback;
import ru.olegry.simpleexchanger.data.executors.Task;
import ru.olegry.simpleexchanger.data.executors.TaskExecutor;
import ru.olegry.simpleexchanger.domain.currencies.CurrenciesInteractor;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.presentation.base.MvpPresenter;

public class CurrenciesPresenter extends MvpPresenter<CurrenciesView> {

    private final CurrenciesInteractor interactor;
    private final TaskExecutor executor = new TaskExecutor();

    public CurrenciesPresenter(CurrenciesInteractor interactor) {
        this.interactor = interactor;
    }

    void currencies() {
        Task<List<Currency>> task = new Task<List<Currency>>() {
            @Override
            public List<Currency> perform() {
                return interactor.currencies();
            }
        };
        executor.execute(task, new ExecutionCallback<List<Currency>>() {
            @Override
            public void onSuccess(List<Currency> result) {
                getMvpView().showCurrencies(result);
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().showError(t);
            }

            @Override
            public void onTerminated() {
                getMvpView().hideProgress();
            }

            @Override
            public void onStarted() {
                getMvpView().showProgress();
            }
        });
    }

    void onCurrencySelected(Currency currency, boolean isInitial) {
        interactor.updateCurrency(currency, isInitial);
        getMvpView().goBack();
    }
}
