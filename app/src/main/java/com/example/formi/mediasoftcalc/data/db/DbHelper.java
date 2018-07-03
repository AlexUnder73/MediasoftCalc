package com.example.formi.mediasoftcalc.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.formi.mediasoftcalc.domain.model.Calculation;
import com.example.formi.mediasoftcalc.other.Constants;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, Constants.DataBase.DATABASE_NAME, null, Constants.DataBase.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.DataBase.Queries.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DataBase.Queries.DROP_TABLE);
        this.onCreate(db);
    }

    public List<Calculation> getCalculationList(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Calculation> calculationList = new ArrayList<>();

        Cursor cursor = db.rawQuery(Constants.DataBase.Queries.SELECT_ALL, null);

        if(cursor.moveToFirst()){
            do {
                Calculation calculation = new Calculation();
                calculation.setCurrentCalc(cursor.getString(cursor.getColumnIndex(Constants.DataBase.Columns.COLUMN_EXPRESSION)));
                calculation.setResult(cursor.getString(cursor.getColumnIndex(Constants.DataBase.Columns.COLUMN_RESULT)));
                calculationList.add(calculation);
            }while(cursor.moveToNext());
        }

        db.close();
        return calculationList;
    }

    public void addCalculation(Calculation calculation){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Constants.DataBase.Columns.COLUMN_EXPRESSION, calculation.getCurrentCalc());
        cv.put(Constants.DataBase.Columns.COLUMN_RESULT, calculation.getResult());
        db.insert(Constants.DataBase.Columns.TABLE_NAME, null, cv);

        db.close();
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.DataBase.Columns.TABLE_NAME, null, null);

        db.close();
    }

    public void getDataToLogs(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(Constants.DataBase.Queries.SELECT_ALL, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Constants.DataBase.Columns._ID));
                String expression = cursor.getString(cursor.getColumnIndex(Constants.DataBase.Columns.COLUMN_EXPRESSION));
                String result = cursor.getString(cursor.getColumnIndex(Constants.DataBase.Columns.COLUMN_RESULT));
                Log.i("sanya", id + " - " + expression + " = " + result);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
}
