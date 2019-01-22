package ru.olegry.simpleexchanger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import ru.olegry.simpleexchanger.utils.ExchangerUtil;

public class ExchangerUtilTest {

    private ExchangerUtil util;

    @Before
    public void setUp() {
        util = new ExchangerUtil();
    }

    @Test
    public void testNormalInput() throws ParseException {
        String initialValue = "66,3309";
        String resultValue = "75,5841";
        String expected = "8,78";

        String result = util.exchange("10", initialValue, resultValue);
        Assert.assertEquals(expected, result);
    }

    @Test(expected = ParseException.class)
    public void testWrongInput() throws ParseException {
        String initialValue = "66,3309";
        String resultValue = "75,5841";

        util.exchange("afiahifhai", initialValue, resultValue);
    }
}
