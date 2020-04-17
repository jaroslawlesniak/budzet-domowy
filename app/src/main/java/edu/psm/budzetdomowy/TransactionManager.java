package edu.psm.budzetdomowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.Transaction;

public class TransactionManager extends AppCompatActivity implements View.OnClickListener {

    boolean isFirstSelect = true;

    String selectedCategory;
    int transactionType;

    TextView dateTextView;
    Button submitButton;
    Spinner categoriesList;

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

        selectedCategory = getIntent().getStringExtra("category");
        transactionType = getIntent().getIntExtra("type", Transaction.EXPENSE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(transactionType == Transaction.INCOME ? "Dodaj przychód" : "Dodaj wydatek");

        dateTextView = (TextView) findViewById(R.id.dateTextView);
        submitButton = (Button) findViewById(R.id.submitButton);
        categoriesList = (Spinner) findViewById(R.id.categoriesList);


        if(selectedCategory != null) {
            submitButton.setText("Dodaj do '" + selectedCategory + "'");
        }

        dateTextView.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        setDate();
        prepareSpinner();
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
            case R.id.submitButton:
                if(selectedCategory == null) {
                    categoriesList.performClick();
                }
                break;
        }
    }

    void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("pl", "PL"));
        dateTextView.setText(dateFormat.format(calendar.getTime()));
    }

    void prepareSpinner() {
        List<String> list = new ArrayList<>();

        list.add(Category.SPORT);
        list.add(Category.HOME);
        list.add(Category.ENTERTAINMENT);
        list.add(Category.FOOD);
        list.add(Category.COSMETICS);
        list.add(Category.MEDIA);
        list.add(Category.TAXI);
        list.add(Category.SHOPPING);
        list.add(Category.ANIMALS);
        list.add(Category.HEALTH);
        list.add(Category.TRANSPORT);
        list.add(Category.CLOTHES);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesList.setAdapter(dataAdapter);

        categoriesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                String selectedItem = parent.getSelectedItem().toString();

                if(!isFirstSelect) {
                    selectedCategory = selectedItem;
                    submitButton.setText("Dodaj do '" + selectedCategory + "'");
                }

                isFirstSelect = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
