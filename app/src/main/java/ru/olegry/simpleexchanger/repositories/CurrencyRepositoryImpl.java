package ru.olegry.simpleexchanger.repositories;

import java.util.List;
import java.util.Observable;

import ru.olegry.simpleexchanger.data.bus.CurrenciesChangedObservable;
import ru.olegry.simpleexchanger.data.cache.CurrenciesCache;
import ru.olegry.simpleexchanger.data.db.DatabaseManager;
import ru.olegry.simpleexchanger.data.network.NetworkManager;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.models.Response;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private NetworkManager networkManager;
    private DatabaseManager dbManager;
    private CurrenciesCache cache;
    private Currency initialCurrency;
    private Currency resultCurrency;

    private CurrenciesChangedObservable currencyChangeObservable = new CurrenciesChangedObservable();

    public CurrencyRepositoryImpl(NetworkManager networkManager, DatabaseManager databaseManager, CurrenciesCache cache) {
        this.networkManager = networkManager;
        this.dbManager = databaseManager;
        this.cache = cache;
    }

    @Override
    public List<Currency> currencies() {
        List<Currency> savedCurrencies = cache.getSavedCurrencies();
        return savedCurrencies != null && !savedCurrencies.isEmpty() ? savedCurrencies : dbManager.currencies();
    }

    @Override
    public List<Currency> downloadCurrencies() {
        List<Currency> currencyList = loadCurrencies();
        if (currencyList == null) {
            currencyList = dbManager.currencies();
        }
        if (currencyList != null && !currencyList.isEmpty()) {
            saveInitialAndResult(currencyList.get(0));
        }
        return currencyList;
    }

    @Override
    public Currency initialCurrency() {
        return initialCurrency;
    }

    @Override
    public Currency resultCurrency() {
        return resultCurrency;
    }

    @Override
    public void updateInitialCurrency(Currency currency) {
        initialCurrency = currency;
        currencyChangeObservable.onCurrencyChanged(currency, true);
    }

    @Override
    public void updateResultCurrency(Currency currency) {
        resultCurrency = currency;
        currencyChangeObservable.onCurrencyChanged(currency, false);
    }

    @Override
    public Observable currencyChangeObservable() {
        return currencyChangeObservable;
    }

    private List<Currency> loadCurrencies() {
        try {
            Response response = networkManager.currencies();
            if (response != null) {
                List<Currency> currencyList = response.getCurrencyList();
                dbManager.saveCurrencies(currencyList);
                cache.saveCurrencies(currencyList);
                return currencyList;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void saveInitialAndResult(Currency currency) {
        initialCurrency = resultCurrency = currency;
    }
}
