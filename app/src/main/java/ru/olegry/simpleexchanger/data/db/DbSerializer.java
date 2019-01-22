package ru.olegry.simpleexchanger.data.db;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;

class DbSerializer {

    private DbSerializer() {}

    static ContentValues currencyToCV(Currency currency) {
        ContentValues cv = new ContentValues();
        cv.put(CurrenciesTable.COLUMN_ID, currency.getId());
        cv.put(CurrenciesTable.COLUMN_NUM_CODE, currency.getNumCode());
        cv.put(CurrenciesTable.COLUMN_CHAR_CODE, currency.getCharCode());
        cv.put(CurrenciesTable.COLUMN_NOMINAL, currency.getNominal());
        cv.put(CurrenciesTable.COLUMN_NAME, currency.getName());
        cv.put(CurrenciesTable.COLUMN_VALUE, currency.getValue());
        return cv;
    }

    static List<Currency> cursorToCurrencies(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(CurrenciesTable.COLUMN_ID);
        int numCodeIndex = cursor.getColumnIndex(CurrenciesTable.COLUMN_NUM_CODE);
        int charCodeIndex = cursor.getColumnIndex(CurrenciesTable.COLUMN_CHAR_CODE);
        int nominalIndex = cursor.getColumnIndex(CurrenciesTable.COLUMN_NOMINAL);
        int nameIndex = cursor.getColumnIndex(CurrenciesTable.COLUMN_NAME);
        int valueIndex = cursor.getColumnIndex(CurrenciesTable.COLUMN_VALUE);

        List<Currency> currencies = new ArrayList<>();
        do {
            Currency currency = new Currency();
            currency.setId(cursor.getString(idIndex));
            currency.setNumCode(cursor.getInt(numCodeIndex));
            currency.setCharCode(cursor.getString(charCodeIndex));
            currency.setNominal(cursor.getString(nominalIndex));
            currency.setName(cursor.getString(nameIndex));
            currency.setValue(cursor.getString(valueIndex));

            currencies.add(currency);
        } while (cursor.moveToNext());

        return currencies;
    }
}
