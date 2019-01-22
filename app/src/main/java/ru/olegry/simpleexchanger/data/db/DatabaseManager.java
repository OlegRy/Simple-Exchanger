package ru.olegry.simpleexchanger.data.db;

import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;

public interface DatabaseManager {
    void saveCurrencies(List<Currency> currencies);
    List<Currency> currencies();
}
