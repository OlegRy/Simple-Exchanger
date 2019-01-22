package ru.olegry.simpleexchanger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.olegry.simpleexchanger.domain.exchanger.ExchangerInteractor;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.repositories.CurrencyRepository;
import ru.olegry.simpleexchanger.repositories.TestCurrenciesRepository;
import ru.olegry.simpleexchanger.utils.Exchanger;

public class ExchangerInteractorTest {

    private ExchangerInteractor interactor;

    @Before
    public void setUp() {
        CurrencyRepository repository = new TestCurrenciesRepository();
        Exchanger exchanger = new Exchanger();

        interactor = new ExchangerInteractor(repository, exchanger);
    }

    @Test
    public void testDefaultCurrencyNotNull() {
        Currency currency = interactor.defaultCurrency();
        Assert.assertNotNull(currency);
    }

    @Test
    public void testDefaultCurrencyReturnsAsExpected() {
        Currency expected = new Currency("R01010", "036", "AUD", "1", "Australia dollar", "39,0987");
        Currency actual = interactor.defaultCurrency();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExchangeWithNormalIntInput() {
        String expected = "4,55";
        String actual = interactor.exchange("10");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void textExchangeWithNormalDoubleInput() {
        String expected = "568,03";
        String actual = interactor.exchange("1248,144");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void testExchangeWithWrongInputThrowsException() {
        interactor.exchange("faifhiahfah");
    }
}
