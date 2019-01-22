package ru.olegry.simpleexchanger.domain.currencies;

import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.repositories.CurrencyRepository;

public class CurrenciesInteractor {

    private CurrencyRepository repository;

    public CurrenciesInteractor(CurrencyRepository repository) {
        this.repository = repository;
    }

    public List<Currency> currencies() {
        return repository.currencies();
    }

    public void updateCurrency(Currency currency, boolean isInitial) {
        if (isInitial) {
            repository.updateInitialCurrency(currency);
        } else {
            repository.updateResultCurrency(currency);
        }
    }
}
