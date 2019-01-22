package ru.olegry.simpleexchanger.data.bus;

import java.util.Observable;

import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.models.CurrencyChangeEvent;

public class CurrenciesChangedObservable extends Observable {

    public void onCurrencyChanged(Currency currency, boolean isInitial) {
        setChanged();
        notifyObservers(new CurrencyChangeEvent(currency, isInitial));
    }
}
