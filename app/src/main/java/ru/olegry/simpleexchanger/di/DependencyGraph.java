package ru.olegry.simpleexchanger.di;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import ru.olegry.simpleexchanger.data.cache.CurrenciesCache;
import ru.olegry.simpleexchanger.data.db.DatabaseManager;
import ru.olegry.simpleexchanger.data.network.NetworkManager;
import ru.olegry.simpleexchanger.data.network.Parser;
import ru.olegry.simpleexchanger.data.network.RequestHelper;
import ru.olegry.simpleexchanger.data.resources.ResourceManager;
import ru.olegry.simpleexchanger.domain.currencies.CurrenciesInteractor;
import ru.olegry.simpleexchanger.domain.exchanger.ExchangerInteractor;
import ru.olegry.simpleexchanger.presentation.currencies.CurrenciesPresenter;
import ru.olegry.simpleexchanger.presentation.exchanger.ExchangerPresenter;
import ru.olegry.simpleexchanger.repositories.CurrencyRepository;
import ru.olegry.simpleexchanger.utils.Exchanger;

public class DependencyGraph {

    private final Map<Class<?>, Object> dependencies = new HashMap<>();

    public DependencyGraph(Application application) {
        DependencyProvider provider = new DependencyProvider();

        RequestHelper requestHelper = provider.provideRequestHelper("http://www.cbr.ru/");
        Parser parser = provider.provideParser();
        NetworkManager networkManager = provider.provideNetworkManager(requestHelper, parser);
        DatabaseManager dbManager = provider.provideDatabaseManager(application);
        CurrenciesCache cache = provider.provideCache();
        CurrencyRepository repository = provider.provideRepository(networkManager, dbManager, cache);
        Exchanger exchanger = provider.provideExchanger();
        ExchangerInteractor exchangerInteractor = provider.provideExchangerInteractor(repository, exchanger);
        CurrenciesInteractor currenciesInteractor = provider.provideCurrenciesInteractor(repository);
        ExchangerPresenter exchangerPresenter = provider.provideExchangerPresenter(exchangerInteractor);
        CurrenciesPresenter currenciesPresenter = provider.provideCurrenciesPresenter(currenciesInteractor);
        ResourceManager resourceManager = provider.provideResourceManager(application);

        dependencies.put(RequestHelper.class, requestHelper);
        dependencies.put(Parser.class, parser);
        dependencies.put(NetworkManager.class, networkManager);
        dependencies.put(DatabaseManager.class, dbManager);
        dependencies.put(CurrenciesCache.class, cache);
        dependencies.put(CurrencyRepository.class, repository);
        dependencies.put(ExchangerInteractor.class, exchangerInteractor);
        dependencies.put(CurrenciesInteractor.class, currenciesInteractor);
        dependencies.put(ExchangerPresenter.class, exchangerPresenter);
        dependencies.put(CurrenciesPresenter.class, currenciesPresenter);
        dependencies.put(ResourceManager.class, resourceManager);
        dependencies.put(Exchanger.class, exchanger);
    }

    public <T> T get(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class you've provided is null");
        }
        Object object = dependencies.get(clazz);
        return object != null ? clazz.cast(object) : null;
    }

    public void clear() {
        dependencies.clear();
    }
}
