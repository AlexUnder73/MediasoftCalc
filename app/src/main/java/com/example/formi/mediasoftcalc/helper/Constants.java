package com.example.formi.mediasoftcalc.helper;

import android.provider.BaseColumns;

public class Constants {

    public static class DataBase{
        public static final String DATABASE_NAME = "calculationsDB";
        public static final int DATABASE_VERSION = 1;

        class Queries{
            public static final String CREATE_TABLE = "CREATE TABLE " + Constants.DataBase.Columns.TABLE_NAME + "("
                    + Constants.DataBase.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Constants.DataBase.Columns.COLUMN_EXPRESSION + " TEXT NOT NULL, "
                    + Constants.DataBase.Columns.COLUMN_RESULT + " TEXT NOT NULL"
                    + ");";
            public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Columns.TABLE_NAME;
            public static final String SELECT_ALL = "SELECT * FROM " + Columns.TABLE_NAME;
        }

        class Columns implements BaseColumns{
            public static final String TABLE_NAME = "calculations";

            public static final String COLUMN_EXPRESSION = "expression";
            public static final String COLUMN_RESULT = "result";
        }
    }
}
