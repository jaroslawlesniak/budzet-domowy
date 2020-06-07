package edu.psm.budzetdomowy.src;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.SurfaceControl;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.psm.budzetdomowy.utils.Transaction;

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
        super(context, DATABASE_NAME, null, 2);
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

    public List<CTransaction> getTransactions(Date startDate, Date endDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =  db.rawQuery( "SELECT * FROM " + TRANSACTIONS_TABLE_NAME + " WHERE " + TRANSACTION_DATE + " <= " + endDate.getTime() + " AND " + TRANSACTION_DATE + " >= " + startDate.getTime(), null );

        List<CTransaction> transactions = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do {
                CTransaction transaction = new CTransaction(
                    cursor.getInt(0),
                    cursor.getFloat(1),
                    new Date(cursor.getLong(2)),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5)
                );

                transactions.add(transaction);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return transactions;
    }

    public void addTransation(float value, Date date, int type, String category, String note) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TRANSACTION_VALUE, value);
        cv.put(TRANSACTION_DATE, date.getTime());
        cv.put(TRANSACTION_NOTE, note);
        cv.put(TRANSACTION_CATEGORY, category);
        cv.put(TRANSACTION_TYPE, type);

        db.insertOrThrow(TRANSACTIONS_TABLE_NAME, null, cv);

        db.close();
    }

    public void deleteTransaction(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TRANSACTIONS_TABLE_NAME, TRANSACTION_ID + "=" + id, null);

        db.close();
    }

    public void updateTransaction(int id, float value, Date date, int type, String category, String note) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TRANSACTION_VALUE, value);
        cv.put(TRANSACTION_DATE, date.getTime());
        cv.put(TRANSACTION_NOTE, note);
        cv.put(TRANSACTION_CATEGORY, category);
        cv.put(TRANSACTION_TYPE, type);

        db.update(TRANSACTIONS_TABLE_NAME, cv, TRANSACTION_ID + "=" + id, null);

        db.close();
    }

    public float getIncomeForInterval(Date startDate, Date endDate) {
        String query = "SELECT SUM(" + TRANSACTION_VALUE + ") AS income FROM " + TRANSACTIONS_TABLE_NAME + " WHERE " + TRANSACTION_DATE + " <= " + endDate.getTime() + " AND " + TRANSACTION_DATE + " >= " + startDate.getTime() + " AND " + TRANSACTION_TYPE + " = " + Transaction.INCOME;
        float income = 0.0f;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            income = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        return income;
    }

    public float getExpenseForInterval(Date startDate, Date endDate) {
        String query = "SELECT SUM(" + TRANSACTION_VALUE + ") AS expense FROM " + TRANSACTIONS_TABLE_NAME + " WHERE " + TRANSACTION_DATE + " <= " + endDate.getTime() + " AND " + TRANSACTION_DATE + " >= " + startDate.getTime() + " AND " + TRANSACTION_TYPE + " = " + Transaction.EXPENSE;
        float expense = 0.0f;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            expense = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        return expense;
    }

    public float getAvaliableMoney() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        float money = 0.0f;

        try {
            Date starDate = format.parse("1970-01-01");
            Date endDate = format.parse("2070-12-31");

            float income = getIncomeForInterval(starDate, endDate);
            float expense = getExpenseForInterval(starDate, endDate);

            money = income - expense;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return money;
    }
}