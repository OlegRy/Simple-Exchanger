package ru.olegry.simpleexchanger.models;

public class CurrencyChangeEvent {

    private Currency currency;
    private boolean isInitial;

    public CurrencyChangeEvent(Currency currency, boolean isInitial) {
        this.currency = currency;
        this.isInitial = isInitial;
    }

    public Currency getCurrency() {
        return currency;
    }

    public boolean isInitial() {
        return isInitial;
    }
}
