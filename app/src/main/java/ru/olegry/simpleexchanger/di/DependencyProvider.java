package ru.olegry.simpleexchanger.di;

import android.content.Context;

import ru.olegry.simpleexchanger.data.cache.CurrenciesCache;
import ru.olegry.simpleexchanger.data.db.DatabaseManager;
import ru.olegry.simpleexchanger.data.db.DatabaseManagerImpl;
import ru.olegry.simpleexchanger.data.network.NetworkManager;
import ru.olegry.simpleexchanger.data.network.Parser;
import ru.olegry.simpleexchanger.data.network.RequestHelper;
import ru.olegry.simpleexchanger.data.resources.ResourceManager;
import ru.olegry.simpleexchanger.domain.currencies.CurrenciesInteractor;
import ru.olegry.simpleexchanger.domain.exchanger.ExchangerInteractor;
import ru.olegry.simpleexchanger.presentation.currencies.CurrenciesPresenter;
import ru.olegry.simpleexchanger.presentation.exchanger.ExchangerPresenter;
import ru.olegry.simpleexchanger.repositories.CurrencyRepository;
import ru.olegry.simpleexchanger.repositories.CurrencyRepositoryImpl;
import ru.olegry.simpleexchanger.utils.ExchangerUtil;

class DependencyProvider {

    RequestHelper provideRequestHelper(String baseUrl) {
        return new RequestHelper(baseUrl);
    }

    Parser provideParser() {
        return new Parser();
    }

    NetworkManager provideNetworkManager(RequestHelper requestHelper, Parser parser) {
        return new NetworkManager.Builder()
                .withRequestHelper(requestHelper)
                .withParser(parser)
                .build();
    }

    DatabaseManager provideDatabaseManager(Context context) {
        return new DatabaseManagerImpl(context);
    }

    CurrenciesCache provideCache() {
        return new CurrenciesCache();
    }

    CurrencyRepository provideRepository(NetworkManager networkManager, DatabaseManager databaseManager, CurrenciesCache cache) {
        return new CurrencyRepositoryImpl(networkManager, databaseManager, cache);
    }

    ExchangerInteractor provideExchangerInteractor(CurrencyRepository repository, ExchangerUtil exchanger) {
        return new ExchangerInteractor(repository, exchanger);
    }

    CurrenciesInteractor provideCurrenciesInteractor(CurrencyRepository repository) {
        return new CurrenciesInteractor(repository);
    }

    ExchangerPresenter provideExchangerPresenter(ExchangerInteractor interactor) {
        return new ExchangerPresenter(interactor);
    }

    CurrenciesPresenter provideCurrenciesPresenter(CurrenciesInteractor interactor) {
        return new CurrenciesPresenter(interactor);
    }

    ResourceManager provideResourceManager(Context context) {
        return new ResourceManager(context);
    }

    ExchangerUtil provideExchanger() {
        return new ExchangerUtil();
    }
}
