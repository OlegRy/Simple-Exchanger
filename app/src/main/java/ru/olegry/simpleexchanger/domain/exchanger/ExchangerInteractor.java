package ru.olegry.simpleexchanger.domain.exchanger;

import java.text.ParseException;
import java.util.List;
import java.util.Observable;

import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.repositories.CurrencyRepository;
import ru.olegry.simpleexchanger.utils.Exchanger;

public class ExchangerInteractor {

    private final CurrencyRepository repository;
    private final Exchanger exchanger;

    public ExchangerInteractor(CurrencyRepository repository, Exchanger exchanger) {
        this.repository = repository;
        this.exchanger = exchanger;
    }

    public Currency defaultCurrency() {
        List<Currency> currencies = repository.downloadCurrencies();
        if (currencies != null && !currencies.isEmpty()) {
            return currencies.get(0);
        }
        return null;
    }

    public String exchange(String amount) {
        Currency initialCurrency = repository.initialCurrency();
        Currency resultCurrency = repository.resultCurrency();
        if (initialCurrency == null || resultCurrency == null) {
            throw new IllegalStateException("Initial and result currencies was not chosen");
        }
        try {
            return exchanger.exchange(amount, initialCurrency.getValue(), resultCurrency.getValue());
        } catch (ParseException e) {
            throw new IllegalStateException();
        }
    }

    public Observable currencyChangeObservable() {
        return repository.currencyChangeObservable();
    }
}
