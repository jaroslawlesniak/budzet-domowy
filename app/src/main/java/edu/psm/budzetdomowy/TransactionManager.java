package edu.psm.budzetdomowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.psm.budzetdomowy.utils.Transaction;

public class TransactionManager extends AppCompatActivity implements View.OnClickListener {

    String defaultCategory;
    int transactionType;

    TextView dateTextView;
    final Calendar calendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener transactionDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_manager);

        defaultCategory = getIntent().getStringExtra("category");
        transactionType = getIntent().getIntExtra("type", Transaction.EXPENSE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(transactionType == Transaction.INCOME ? "Dodaj przychód" : "Dodaj wydatek");

        dateTextView = (TextView) findViewById(R.id.dateTextView);

        dateTextView.setOnClickListener(this);

        setDate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dateTextView:
                new DatePickerDialog(
                        this,
                        transactionDate,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            break;
        }
    }

    void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("pl", "PL"));
        dateTextView.setText(dateFormat.format(calendar.getTime()));
    }
}
