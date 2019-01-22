package ru.olegry.simpleexchanger.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;
import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;

public class DatabaseManagerImpl implements DatabaseManager {

    private final DBHelper dbHelper;

    public DatabaseManagerImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void saveCurrencies(List<Currency> currencyList) {
        if (currencyList != null && !currencyList.isEmpty()) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.beginTransaction();

            try {
                for (Currency currency : currencyList) {
                    database.insertWithOnConflict(
                            CurrenciesTable.TABLE_NAME, null,
                            DbSerializer.currencyToCV(currency), 5);
                }
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                dbHelper.close();
            }
        }
    }

    @Override
    public List<Currency> currencies() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(CurrenciesTable.TABLE_NAME, null, null, null, null, null, null);
        List<Currency> currencies;
        if (cursor.moveToFirst()) {
            currencies = DbSerializer.cursorToCurrencies(cursor);
        } else {
            currencies = Collections.emptyList();
        }
        cursor.close();
        dbHelper.close();
        return currencies;
    }
}
