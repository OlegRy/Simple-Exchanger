package ru.olegry.simpleexchanger.presentation.exchanger;

import java.util.Observable;
import java.util.Observer;

import ru.olegry.simpleexchanger.data.executors.ExecutionCallback;
import ru.olegry.simpleexchanger.data.executors.Task;
import ru.olegry.simpleexchanger.data.executors.TaskExecutor;
import ru.olegry.simpleexchanger.domain.exchanger.ExchangerInteractor;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.models.CurrencyChangeEvent;
import ru.olegry.simpleexchanger.presentation.base.MvpPresenter;

public class ExchangerPresenter extends MvpPresenter<ExchangerView> {

    private final ExchangerInteractor interactor;
    private final TaskExecutor executor = new TaskExecutor();
    private final Observer currencyChangeObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof CurrencyChangeEvent) {
                CurrencyChangeEvent event = (CurrencyChangeEvent) arg;
                if (event.isInitial()) {
                    getMvpView().showNewInitialCurrency(event.getCurrency());
                } else {
                    getMvpView().showNewResultCurrency(event.getCurrency());
                }
            }
        }
    };

    public ExchangerPresenter(ExchangerInteractor interactor) {
        this.interactor = interactor;
        this.interactor.currencyChangeObservable().addObserver(currencyChangeObserver);
    }

    void currencies() {
        Task<Currency> currenciesTask = new Task<Currency>() {
            @Override
            public Currency perform() {
                return interactor.defaultCurrency();
            }
        };
        executor.execute(currenciesTask, new ExecutionCallback<Currency>() {
            @Override
            public void onSuccess(Currency result) {
                getMvpView().showConverterSettings(result);
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

    void exchange(final String amount) {
        Task<String> exchangeTask = new Task<String>() {
            @Override
            public String perform() {
                return interactor.exchange(amount);
            }
        };
        executor.execute(exchangeTask, new ExecutionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getMvpView().showResult(result);
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().showError(t);
            }

            @Override
            public void onTerminated() {}

            @Override
            public void onStarted() {}
        });
    }

    @Override
    public void onDestroy() {
        interactor.currencyChangeObservable().deleteObserver(currencyChangeObserver);
    }
}
