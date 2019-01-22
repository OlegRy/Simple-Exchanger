package ru.olegry.simpleexchanger.data.db;

class CurrenciesTable {

    static final String TABLE_NAME = "currencies";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NUM_CODE = "numCode";
    static final String COLUMN_CHAR_CODE = "charCode";
    static final String COLUMN_NOMINAL = "nominal";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_VALUE = "value";

    private CurrenciesTable() {}

    static String createTableScript() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s VARCHAR(20) PRIMARY KEY," +
                        "%s VARCHAR(20)," +
                        "%s VARCHAR(10)," +
                        "%s VARCHAR(10)," +
                        "%s VARCHAR(100)," +
                        "%s VARCHAR(20))",
                TABLE_NAME, COLUMN_ID, COLUMN_NUM_CODE, COLUMN_CHAR_CODE,
                COLUMN_NOMINAL, COLUMN_NAME, COLUMN_VALUE);
    }

    static String clearTableScript() {
        return String.format("DELETE FROM %s", TABLE_NAME);
    }
}
