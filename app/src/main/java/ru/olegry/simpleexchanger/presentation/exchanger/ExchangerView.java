package ru.olegry.simpleexchanger.presentation.exchanger;

import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.presentation.base.ProgressMvpView;

public interface ExchangerView extends ProgressMvpView {

    void showConverterSettings(Currency currency);
    void showResult(String result);
    void showNewInitialCurrency(Currency currency);
    void showNewResultCurrency(Currency currency);
}
