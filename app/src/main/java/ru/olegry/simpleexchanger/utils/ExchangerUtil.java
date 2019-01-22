package ru.olegry.simpleexchanger.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class ExchangerUtil {

    private DecimalFormat format;

    public ExchangerUtil() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        format = new DecimalFormat("#,##", symbols);
        format.setMaximumFractionDigits(2);
    }

    public String exchange(String amount, String initialValue, String resultValue) throws ParseException {
        BigDecimal initialDecimal = new BigDecimal(format.parse(initialValue).doubleValue());
        BigDecimal resultDecimal = new BigDecimal(format.parse(resultValue).doubleValue());
        BigDecimal amountDecimal = new BigDecimal(format.parse(amount).doubleValue());

        BigDecimal result = amountDecimal.multiply(initialDecimal).divide(resultDecimal, RoundingMode.DOWN);
        return format.format(result.doubleValue());
    }
}
