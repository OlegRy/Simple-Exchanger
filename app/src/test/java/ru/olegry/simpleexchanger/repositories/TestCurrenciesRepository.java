package ru.olegry.simpleexchanger.repositories;

import java.util.List;
import java.util.Observable;

import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.utils.CurrenciesProvider;

public class TestCurrenciesRepository implements CurrencyRepository {

    private List<Currency> currencies = CurrenciesProvider.currencies();

    @Override
    public List<Currency> currencies() {
        return currencies;
    }

    @Override
    public List<Currency> downloadCurrencies() {
        return currencies;
    }

    @Override
    public Currency initialCurrency() {
        return currencies.get(0);
    }

    @Override
    public Currency resultCurrency() {
        return currencies.get(1);
    }

    @Override
    public void updateInitialCurrency(Currency currency) {
        currencies.set(0, currency);
    }

    @Override
    public void updateResultCurrency(Currency currency) {
        currencies.set(1, currency);
    }

    @Override
    public Observable currencyChangeObservable() {
        return null;
    }
}
