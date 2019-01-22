package ru.olegry.simpleexchanger.repositories;

import java.util.List;
import java.util.Observable;

import ru.olegry.simpleexchanger.models.Currency;

public interface CurrencyRepository {
    List<Currency> currencies();
    List<Currency> downloadCurrencies();
    Currency initialCurrency();
    Currency resultCurrency();
    void updateInitialCurrency(Currency currency);
    void updateResultCurrency(Currency currency);
    Observable currencyChangeObservable();
}
