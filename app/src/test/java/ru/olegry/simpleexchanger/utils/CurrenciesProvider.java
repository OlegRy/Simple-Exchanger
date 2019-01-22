package ru.olegry.simpleexchanger.utils;

import java.util.ArrayList;
import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;

public class CurrenciesProvider {

    private CurrenciesProvider() {}

    public static List<Currency> currencies() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("R01010", "036", "AUD", "1", "Australia dollar", "39,0987"));
        currencies.add(new Currency("R01035", "826", "GBP", "1", "Pound", "85,9118"));
        currencies.add(new Currency("R01020A", "944", "AZN", "1", "Manat", "39,0987"));

        return currencies;
    }
}
