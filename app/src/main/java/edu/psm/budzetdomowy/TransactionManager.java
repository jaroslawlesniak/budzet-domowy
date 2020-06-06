package edu.psm.budzetdomowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.*;

import edu.psm.budzetdomowy.src.CDatabase;
import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.Transaction;

public class TransactionManager extends AppCompatActivity implements View.OnClickListener {

    boolean isFirstSelect = true;

    String selectedCategory;
    int transactionType;

    TextView dateTextView;
    Button categoryButton;
    Spinner categoriesList;
    EditText editTextNote;

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
        categoryButton = (Button) findViewById(R.id.categoryButton);
        categoriesList = (Spinner) findViewById(R.id.categoriesList);
        editTextNote = findViewById(R.id.editTextNote);


        if(selectedCategory != null) {
            categoryButton.setText("Dodaj do '" + selectedCategory + "'");
        }

        dateTextView.setOnClickListener(this);
        categoryButton.setOnClickListener(this);

        setDate();
        prepareSpinner();

        //kalkulator
        findViewById(R.id.calc1).setOnClickListener(this);
        findViewById(R.id.calc2).setOnClickListener(this);
        findViewById(R.id.calc3).setOnClickListener(this);
        findViewById(R.id.calc4).setOnClickListener(this);
        findViewById(R.id.calc5).setOnClickListener(this);
        findViewById(R.id.calc6).setOnClickListener(this);
        findViewById(R.id.calc7).setOnClickListener(this);
        findViewById(R.id.calc8).setOnClickListener(this);
        findViewById(R.id.calc9).setOnClickListener(this);
        findViewById(R.id.calc0).setOnClickListener(this);
        findViewById(R.id.calcPlus).setOnClickListener(this);
        findViewById(R.id.calcMinus).setOnClickListener(this);
        findViewById(R.id.calcMultiply).setOnClickListener(this);
        findViewById(R.id.calcDivision).setOnClickListener(this);
        findViewById(R.id.calcDot).setOnClickListener(this);
        findViewById(R.id.calcEqual).setOnClickListener(this);
        findViewById(R.id.calcDelete).setOnClickListener(this);
        findViewById(R.id.calcClear).setOnClickListener(this);
        findViewById(R.id.submitTransactionButton).setOnClickListener(this);
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
    StringBuilder sb = new StringBuilder();
    int indexOfSign=-1; //jeśli nie było znaku to jest -1, jeśli był to inna wartość
    int isDot = 0; //sprawdzenie czy w pierwszej liczbie była już kropka użyta : 0 nie, 1 tak, 2/3 jedno/dwa miejsca po przecinku wpisane
    int isDot2 = 0; //sprawdzenie czy w drugiej liczbie była już kropka użyta : 0 nie, 1 tak, 2/3 jedno/dwa miejsca po przecinku wpisane
    String result = null;

    CDatabase cDatabase = new CDatabase(this);

    @Override
    public void onClick(View view) {
        final TextView tvKwota = findViewById(R.id.tvKwota);

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
            case R.id.categoryButton:
                if(selectedCategory == null) {
                    categoriesList.performClick();
                }
                break;

            case R.id.submitTransactionButton:
                if (sb.length()<=0 || selectedCategory == null){
                Toast.makeText(this, "wprowadź dane", Toast.LENGTH_SHORT).show();
                break;
                }
                Date finalDate = new Date(calendar.getTime().getTime());
                String finalNote = editTextNote.getText().toString();
                if(indexOfSign!=-1) { //jeśli był znak działania to usuń go i to co po nim, bierzemy pierwszą wartość
                    sb.delete(indexOfSign, sb.length());
                    tvKwota.setText(sb);
                }
                float finalValue =  Float.parseFloat(String.valueOf(sb));
                cDatabase.addTransation(finalValue, finalDate, transactionType, selectedCategory, finalNote);

                System.out.println(finalValue);
                System.out.println(finalNote);
                System.out.println(finalDate);

                Intent intent = new Intent(this, homepage.class);
                startActivity(intent);
                break;

            case R.id.calc1:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("1");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc2:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("2");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc3:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("3");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc4:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("4");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc5:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("5");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc6:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("6");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc7:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("7");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc8:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("8");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc9:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                sb.append("9");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calc0:
                if(result!=null) prepareToNextCalculation();
                if(isDot<3){
                    if(isDot>0) isDot++;
                sb.append("0");
                    if(sb.length()-indexOfSign==2) {
                        sb.append(".");
                        isDot = 1;
                    }
                tvKwota.setText(sb);}
                break;
            case R.id.calcDot:
                if(result!=null) prepareToNextCalculation();
                if(sb.length()-1==indexOfSign) sb.append("0");
                if (isDot==0){
                isDot = 1;
                sb.append(".");
                tvKwota.setText(sb);}
                break;
            case R.id.calcPlus:
                if (indexOfSign==-1 && sb.length()!=0){
                    sb.append("+");
                    tvKwota.setText(sb);
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcMinus:
                if (indexOfSign==-1 && sb.length()!=0){
                    sb.append("-");
                    tvKwota.setText(sb);
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcMultiply:
                if (indexOfSign==-1 && sb.length()!=0){
                    sb.append("*");
                    tvKwota.setText(sb);
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcDivision:
                if (indexOfSign==-1 && sb.length()!=0){
                    sb.append("/");
                    tvKwota.setText(sb);
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcEqual:
                if(result==null) {
                    result = calculate(String.valueOf(sb), indexOfSign);
                    sb.replace(0, sb.length(), result);
                    tvKwota.setText(sb);
                    indexOfSign = -1;
                }
                break;
            case R.id.calcDelete:
                if(sb.length()>0) deleteOneChar();
                if(sb.length()>0) tvKwota.setText(sb);
                else tvKwota.setText("Wpisz kwotę");
                break;
            case R.id.calcClear: //działa
                sb.delete(0, sb.length());
                tvKwota.setText("Wpisz kwotę");
                indexOfSign = -1;
                isDot = 0;
                break;
        }
    }

    void deleteOneChar(){

        //jeśli usuwa przecinek
        if(isDot==1){//jeśli przed przecinkiem jest tylko zero (lub po znaku jest tylko zero) to usuń też zero
            if(sb.charAt(sb.length()-2)=='0') sb.deleteCharAt(sb.length()-1);
        }

        // jeśli usuwa coś po przecinku
        if(isDot>0) isDot--;


        //jeśli usuwa znak
        if(sb.length()-1==indexOfSign) { // trzeba dać znać jakoś czy była już kropka i ile miejsc po niej
            indexOfSign = -1;
            //albo osobna zmienna na pierwszą i drugą kropkę(i trochę zmian w kodzie), albo sprawdzanie trzech ostatnich charów
            if (sb.charAt(sb.length() - 2) == '.') isDot=1;
            else if (sb.charAt(sb.length() - 3) == '.') isDot=2;
            else if (sb.charAt(sb.length() - 4) == '.') isDot=3;
        }

        sb.deleteCharAt(sb.length()-1);
    }

    void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("pl", "PL"));
        String stringDate = dateFormat.format(calendar.getTime());
        dateTextView.setText(stringDate);
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
                    categoryButton.setText("Dodaj do '" + selectedCategory + "'");
                }

                isFirstSelect = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //rozdzielenie stringa na składowe i obliczenie wyniku
    String calculate(String gluedString, int indexOfSign) {
        if(indexOfSign == -1) return gluedString; //Jeśli nie wpisano znaku, to nie wykonuje obliczeń

        String firstNumber = gluedString.substring(0,indexOfSign);
        String secondNumber = gluedString.substring(indexOfSign+1);
        if(secondNumber.equals("")) return firstNumber;
        char sign = gluedString.charAt(indexOfSign);

        double a=Double.parseDouble(firstNumber);
        double b=Double.parseDouble(secondNumber);
        Double doubleValue;
        String textValue;

        if(sign=='/' && b == 0) {
            Toast.makeText(getApplicationContext(),"Nie dzielimy przez zero!", Toast.LENGTH_SHORT).show();
            return gluedString;
        }

        if (sign == '+'){
            doubleValue = a+b;
        }
        else if (sign == '-'){
            doubleValue = a-b;
        }
        else if (sign == '*'){
            doubleValue = a*b;
        }
        else {
            doubleValue = a/b;
        }

        textValue = String.format(Locale.US,"%.2f", doubleValue);
        return textValue;
    }
    void prepareToNextCalculation(){
        sb.delete(0, sb.length());
        isDot = 0;
        indexOfSign = -1;
        result = null;}
}
