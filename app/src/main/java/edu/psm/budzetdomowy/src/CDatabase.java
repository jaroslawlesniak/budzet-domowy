package edu.psm.budzetdomowy.src;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class CDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HomeBudget";

    private static final String TRANSACTIONS_TABLE_NAME = "Transactions";
    private static final String TRANSACTION_ID = "ID";
    private static final String TRANSACTION_VALUE = "Value";
    private static final String TRANSACTION_DATE = "Date";
    private static final String TRANSACTION_TYPE = "Type";
    private static final String TRANSACTION_CATEGORY = "Category";
    private static final String TRANSACTION_NOTE = "Note";

    public CDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE IF NOT EXISTS " + TRANSACTIONS_TABLE_NAME + "(" +
                    TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TRANSACTION_VALUE + " REAL, " +
                    TRANSACTION_DATE + " NUMERIC, " +
                    TRANSACTION_TYPE + " INTEGER, " +
                    TRANSACTION_CATEGORY + " TEXT, " +
                    TRANSACTION_NOTE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getTransactions(Date startDate, Date endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + TRANSACTIONS_TABLE_NAME + " WHERE " + TRANSACTION_DATE + " <= " + endDate + " AND " + TRANSACTION_DATE + " >= " + startDate, null );
        return res;
    }

    public void addTransation(float value, Date date, int type, String category, String note) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("INSERT INTO " + TRANSACTIONS_TABLE_NAME + " (" + TRANSACTION_VALUE + ", " + TRANSACTION_DATE + ", " + TRANSACTION_TYPE + ", " + TRANSACTION_CATEGORY + ", " + TRANSACTION_NOTE + ")" +
                         "VALUES (" + value +", " + date + ", " + type + ", " + category + ", " + note +  ")", null);
    }

    public void deleteTransaction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("DELETE FROM " + TRANSACTIONS_TABLE_NAME + " WHERE " + TRANSACTION_ID + " = " + id, null);
    }

    public void updateTransaction(int id, float value, Date date, int type, String category, String note) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("UPDATE " + TRANSACTIONS_TABLE_NAME + " SET " +
               TRANSACTION_VALUE + " = " + value + ", " +
               TRANSACTION_DATE + " = " + date + ", " +
               TRANSACTION_TYPE + " = " + type + ", " +
               TRANSACTION_CATEGORY + " = " + category + ", " +
               TRANSACTION_NOTE + " = " + note, null);
    }
}
