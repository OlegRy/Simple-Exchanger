package ru.olegry.simpleexchanger.presentation.currencies;

import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.presentation.base.ProgressMvpView;

public interface CurrenciesView extends ProgressMvpView {
    void showCurrencies(List<Currency> currencies);
    void goBack();
}
