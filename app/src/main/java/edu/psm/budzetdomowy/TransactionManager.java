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
import java.text.*;

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
    int indexOfSign=0;
    boolean isSign = false; //sprawdzenie czy był już znak
    int isDot = 0; //sprawdzenie czy była już kropka użyta : 0 nie, 1 tak, 2/3 jedno/dwa miejsca po przecinku wpisane
    String result = null;

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
            case R.id.submitButton:
                if(selectedCategory == null) {
                    categoriesList.performClick();
                }
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
                sb.append("0");
                tvKwota.setText(sb);
                if(isDot>0) isDot++;}
                break;
            case R.id.calcDot:
                if(result!=null) prepareToNextCalculation();
                if(sb.length()==0 || sb.length()-1==indexOfSign) sb.append("0");
                if (isDot==0){
                isDot = 1;
                sb.append(".");
                tvKwota.setText(sb);}
                break;
            case R.id.calcPlus:
                if (!isSign){
                    sb.append("+");
                    tvKwota.setText(sb);
                    isSign = true;
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcMinus:
                if (isSign){}
                else {
                    sb.append("-");
                    tvKwota.setText(sb);
                    isSign = true;
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcMultiply:
                if (!isSign){
                    sb.append("*");
                    tvKwota.setText(sb);
                    isSign = true;
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcDivision:
                if (!isSign){
                    sb.append("/");
                    tvKwota.setText(sb);
                    isSign = true;
                    isDot = 0;
                    indexOfSign=sb.length()-1;
                    result = null;}
                break;
            case R.id.calcEqual: //wysypuje przy działaniu na otrzymanym już wyniku
                result = calculate(String.valueOf(sb), indexOfSign);
                sb.replace(0, sb.length(), result);
                tvKwota.setText(sb);
                isSign = false;
                break;
            case R.id.calcDelete:
                if(sb.length()==indexOfSign) isSign = false;
                if(isDot>0) isDot--;
                sb.deleteCharAt(sb.length()-1);
                tvKwota.setText(sb);
                break;
            case R.id.calcClear: //działa
                sb.delete(0, sb.length());
                tvKwota.setText(sb);
                isSign = false;
                isDot = 0;
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
    //rozdzielenie stringa na składowe i obliczenie wyniku
    String calculate(String gluedString, int indexOfSign) {
        if(indexOfSign == 0) return gluedString; //Jeśli nie wpisano znaku, to nie wykonuje obliczeń

        String firstNumber = gluedString.substring(0,indexOfSign);
        String secondNumber = gluedString.substring(indexOfSign+1);
        char sign = gluedString.charAt(indexOfSign);

        double a=Double.parseDouble(firstNumber);
        double b=Double.parseDouble(secondNumber);
        Double doubleValue;
        String textValue;

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

        textValue = String.format(Locale.US,"%.2f", doubleValue); //Zaokrąglenia
         //tutaj jest dobre zaokrąglenie a potem nagle jakieś dodatkowe liczby na 3 miejscu po przecinku
        //ale chciałabym też żeby konwersja nie zamieniała kropki na przecinek bo potem się wysypują kolejne obliczenia
        return textValue;
    }
    void prepareToNextCalculation(){
        sb.delete(0, sb.length());
        isSign = false;
        isDot = 0;
        result = null;}
}
