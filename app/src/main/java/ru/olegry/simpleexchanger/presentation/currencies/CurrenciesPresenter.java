package ru.olegry.simpleexchanger.presentation.currencies;

import java.util.List;

import ru.olegry.simpleexchanger.data.executors.ExecutionCallback;
import ru.olegry.simpleexchanger.data.executors.Task;
import ru.olegry.simpleexchanger.data.executors.TaskExecutor;
import ru.olegry.simpleexchanger.domain.currencies.CurrenciesInteractor;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.presentation.base.presenter.MvpPresenter;
import ru.olegry.simpleexchanger.presentation.base.presenter.NotRepeatableViewCommand;
import ru.olegry.simpleexchanger.presentation.base.presenter.ViewCommand;

public class CurrenciesPresenter extends MvpPresenter<CurrenciesView> {

    private final CurrenciesInteractor interactor;
    private final TaskExecutor executor = new TaskExecutor();

    public CurrenciesPresenter(CurrenciesInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onFirstViewAttached() {
        currencies();
    }

    void onCurrencySelected(Currency currency, boolean isInitial) {
        interactor.updateCurrency(currency, isInitial);
        getMvpView().goBack();
    }

    private void currencies() {
        Task<List<Currency>> task = new Task<List<Currency>>() {
            @Override
            public List<Currency> perform() {
                return interactor.currencies();
            }
        };
        executor.execute(task, new ExecutionCallback<List<Currency>>() {
            @Override
            public void onSuccess(final List<Currency> result) {
                apply(new ViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().showCurrencies(result);
                    }
                });
            }

            @Override
            public void onError(final Throwable t) {
                apply(new NotRepeatableViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().showError(t);
                    }
                });
            }

            @Override
            public void onTerminated() {
                apply(new ViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().hideProgress();
                    }
                });
            }

            @Override
            public void onStarted() {
                apply(new ViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().showProgress();
                    }
                });
            }
        });
    }
}
