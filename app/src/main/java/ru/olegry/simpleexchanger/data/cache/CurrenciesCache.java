package ru.olegry.simpleexchanger.data.cache;

import java.util.ArrayList;
import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;

public class CurrenciesCache {

    private List<Currency> savedCurrencies;

    public void saveCurrencies(List<Currency> currencies) {
        savedCurrencies = new ArrayList<>(currencies);
    }

    public List<Currency> getSavedCurrencies() {
        return savedCurrencies;
    }

    public void clear() {
        if (savedCurrencies != null) savedCurrencies.clear();
    }
}
