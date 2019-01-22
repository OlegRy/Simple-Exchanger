package ru.olegry.simpleexchanger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.olegry.simpleexchanger.domain.currencies.CurrenciesInteractor;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.repositories.CurrencyRepository;
import ru.olegry.simpleexchanger.repositories.TestCurrenciesRepository;

public class CurrenciesInteractorTest {

    private CurrenciesInteractor interactor;
    private CurrencyRepository repository;

    @Before
    public void setUp() {
        repository = new TestCurrenciesRepository();
        interactor = new CurrenciesInteractor(repository);
    }

    @Test
    public void testCurrenciesReturnsExpectedListSize() {
        int expected = repository.currencies().size();
        int actual = interactor.currencies().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateCurrencyUpdatesInitialCurrency() {
        Currency initialCurrency = repository.initialCurrency();
        Currency oldInitialCurrency = new Currency(initialCurrency.getId(), initialCurrency.getNumCode(),
                initialCurrency.getCharCode(), initialCurrency.getNominal(), initialCurrency.getName(), initialCurrency.getValue());
        interactor.updateCurrency(new Currency("R01115", "986", "BRL", "1", "Real", "17,6859"), true);
        initialCurrency = repository.initialCurrency();

        Assert.assertNotEquals(initialCurrency, oldInitialCurrency);
    }

    @Test
    public void testUpdateCurrencyUpdatesResultCurrency() {
        Currency initialCurrency = repository.resultCurrency();
        Currency oldInitialCurrency = new Currency(initialCurrency.getId(), initialCurrency.getNumCode(),
                initialCurrency.getCharCode(), initialCurrency.getNominal(), initialCurrency.getName(), initialCurrency.getValue());
        interactor.updateCurrency(new Currency("R01239", "978", "EUR", "1", "Euro", "75,5841"), false);
        initialCurrency = repository.resultCurrency();

        Assert.assertNotEquals(initialCurrency, oldInitialCurrency);
    }
}
