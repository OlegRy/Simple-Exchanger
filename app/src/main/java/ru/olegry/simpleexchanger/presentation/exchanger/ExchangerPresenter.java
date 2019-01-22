package ru.olegry.simpleexchanger.presentation.exchanger;

import java.util.Observable;
import java.util.Observer;

import ru.olegry.simpleexchanger.data.executors.ExecutionCallback;
import ru.olegry.simpleexchanger.data.executors.Task;
import ru.olegry.simpleexchanger.data.executors.TaskExecutor;
import ru.olegry.simpleexchanger.domain.exchanger.ExchangerInteractor;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.models.CurrencyChangeEvent;
import ru.olegry.simpleexchanger.presentation.base.presenter.MvpPresenter;
import ru.olegry.simpleexchanger.presentation.base.presenter.NotRepeatableViewCommand;
import ru.olegry.simpleexchanger.presentation.base.presenter.ViewCommand;

public class ExchangerPresenter extends MvpPresenter<ExchangerView> {

    private final ExchangerInteractor interactor;
    private final TaskExecutor executor = new TaskExecutor();
    private final Observer currencyChangeObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof CurrencyChangeEvent) {
                final CurrencyChangeEvent event = (CurrencyChangeEvent) arg;
                if (event.isInitial()) {
                    apply(new ViewCommand() {
                        @Override
                        public void execute() {
                            getMvpView().showNewInitialCurrency(event.getCurrency());
                        }
                    });

                } else {
                    apply(new ViewCommand() {
                        @Override
                        public void execute() {
                            getMvpView().showNewResultCurrency(event.getCurrency());
                        }
                    });
                }
            }
        }
    };

    public ExchangerPresenter(ExchangerInteractor interactor) {
        this.interactor = interactor;
        this.interactor.currencyChangeObservable().addObserver(currencyChangeObserver);
    }

    @Override
    protected void onFirstViewAttached() {
        currencies();
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
            public void onSuccess(final String result) {
                apply(new NotRepeatableViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().showResult(result);
                    }
                });
            }

            @Override
            public void onError(final Throwable t) {
                apply(new ViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().showError(t);
                    }
                });
            }

            @Override
            public void onTerminated() {}

            @Override
            public void onStarted() {}
        });
    }

    private void currencies() {
        Task<Currency> currenciesTask = new Task<Currency>() {
            @Override
            public Currency perform() {
                return interactor.defaultCurrency();
            }
        };
        executor.execute(currenciesTask, new ExecutionCallback<Currency>() {
            @Override
            public void onSuccess(final Currency result) {
                apply(new ViewCommand() {
                    @Override
                    public void execute() {
                        getMvpView().showConverterSettings(result);
                    }
                });
            }

            @Override
            public void onError(final Throwable t) {
                apply(new ViewCommand() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        interactor.currencyChangeObservable().deleteObserver(currencyChangeObserver);
    }
}
