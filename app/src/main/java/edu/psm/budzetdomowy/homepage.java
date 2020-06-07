package edu.psm.budzetdomowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.psm.budzetdomowy.src.CDatabase;
import edu.psm.budzetdomowy.src.CSummary;
import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.CategoryColor;
import edu.psm.budzetdomowy.utils.SummaryInterval;
import edu.psm.budzetdomowy.utils.Transaction;

public class homepage extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    // Czy wybieramy podsumowanie z dnia, miesiąca itp.
    int selectedSummaryInterval = SummaryInterval.MONTH;

    // Okresy podsumowania - od, do
    Date startSummaryInterval, endSummaryInterval;

    CDatabase database = new CDatabase(this);

    TextView selectedInterval, incomeText, expenseText, avaliableMoney;
    Button saldoButton;

    TextView sport, home, entertainment, food, cosmetics, media, taxi, shopping, animals, health, transport, clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        selectedInterval = findViewById(R.id.selectedInterval);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        findViewById(R.id.nextInterval).setOnClickListener(this);
        findViewById(R.id.previousInterval).setOnClickListener(this);

        findViewById(R.id.addIncome).setOnClickListener(this);
        findViewById(R.id.addExpense).setOnClickListener(this);

        incomeText = findViewById(R.id.incomeText);
        expenseText = findViewById(R.id.expenseText);
        avaliableMoney = findViewById(R.id.avaliableMoney);
        saldoButton = findViewById(R.id.saldoBtn);

        sport = findViewById(R.id.sport);
        home = findViewById(R.id.home);
        entertainment = findViewById(R.id.entertainment);
        food = findViewById(R.id.food);
        cosmetics = findViewById(R.id.cosmetics);
        media = findViewById(R.id.media);
        taxi = findViewById(R.id.taxi);
        shopping = findViewById(R.id.shopping);
        animals = findViewById(R.id.animals);
        health = findViewById(R.id.health);
        transport = findViewById(R.id.transport);
        clothes = findViewById(R.id.clothes);

        //Button otwierający listę transakcji
        Button buttonOpenBottomSheet = findViewById(R.id.saldoBtn);
        buttonOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionList e = new transactionList();
                e.database = new CDatabase(context);
                e.startDate = startSummaryInterval;
                e.endDate = endSummaryInterval;
                e.show(getSupportFragmentManager(), "shdhdhs");
            }
        });

        //Wybranie przedziału podsumowania z wysuwanego menu
        final NavigationView navigation = findViewById(R.id.nav_view);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.day) {
                    setSummaryInterval(SummaryInterval.DAY);
                }

                if (id == R.id.week) {
                    setSummaryInterval(SummaryInterval.WEEK);
                }

                if (id == R.id.month) {
                    setSummaryInterval(SummaryInterval.MONTH);
                }

                if (id == R.id.year) {
                    setSummaryInterval(SummaryInterval.YEAR);
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        // Ustawienie obecnej daty
        DateFormat dateFormat = new SimpleDateFormat("LLLL", new Locale("pl", "PL"));
        Date date = new Date();
        selectedInterval.setText(dateFormat.format(date).substring(0, 1).toUpperCase() + dateFormat.format(date).substring(1).toLowerCase());

        //Wybór przedziału podumowania, od pierwszego do ostataniego dnia miesiąca
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Ustawienie początku dnia
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Określenie pierwszego dnia miesiąca
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        startSummaryInterval = cal.getTime();

        // Określenie ostatniego dnia miesiąca
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        endSummaryInterval = cal.getTime();

        // Odświeżenie salda, sumy przychodów i wydatków
        refreshSummary();
    }

    protected void openTransactionActivity(String category, int type) {
        Intent intent = new Intent(getBaseContext(), TransactionManager.class);

        intent.putExtra("category", category);
        intent.putExtra("type", type);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn1:
                openTransactionActivity(Category.SPORT, Transaction.EXPENSE);
                break;
            case R.id.btn2:
                openTransactionActivity(Category.HOME, Transaction.EXPENSE);
                break;
            case R.id.btn3:
                openTransactionActivity(Category.ENTERTAINMENT, Transaction.EXPENSE);
                break;
            case R.id.btn4:
                openTransactionActivity(Category.FOOD, Transaction.EXPENSE);
                break;
            case R.id.btn5:
                openTransactionActivity(Category.COSMETICS, Transaction.EXPENSE);
                break;
            case R.id.btn6:
                openTransactionActivity(Category.MEDIA, Transaction.EXPENSE);
                break;
            case R.id.btn7:
                openTransactionActivity(Category.TAXI, Transaction.EXPENSE);
                break;
            case R.id.btn8:
                openTransactionActivity(Category.SHOPPING, Transaction.EXPENSE);
                break;
            case R.id.btn9:
                openTransactionActivity(Category.ANIMALS, Transaction.EXPENSE);
                break;
            case R.id.btn10:
                openTransactionActivity(Category.HEALTH, Transaction.EXPENSE);
                break;
            case R.id.btn11:
                openTransactionActivity(Category.TRANSPORT, Transaction.EXPENSE);
                break;
            case R.id.btn12:
                openTransactionActivity(Category.CLOTHES, Transaction.EXPENSE);
                break;
            case R.id.addIncome:
                openTransactionActivity(null, Transaction.INCOME);
                break;
            case R.id.addExpense:
                openTransactionActivity(null, Transaction.EXPENSE);
                break;
            case R.id.nextInterval:
                changeSummaryInterval(1);
                break;
            case R.id.previousInterval:
                changeSummaryInterval(-1);
                break;
        }
    }

    private void setSummaryInterval(int type) {
        selectedSummaryInterval = type;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Ustawienie początku dnia
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if(type == SummaryInterval.DAY) {
            startSummaryInterval = cal.getTime();
            cal.add(Calendar.DATE, 1);
            cal.add(Calendar.SECOND, -1);
            endSummaryInterval = cal.getTime();
        }

        if(type == SummaryInterval.WEEK) {
            cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
            startSummaryInterval = cal.getTime();
            cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
            endSummaryInterval = cal.getTime();
        }

        if(type == SummaryInterval.MONTH) {
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            startSummaryInterval = cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            endSummaryInterval = cal.getTime();
        }

        if(type == SummaryInterval.YEAR) {
            cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
            startSummaryInterval = cal.getTime();
            cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
            endSummaryInterval = cal.getTime();
        }

        DateFormat dateFormat = new SimpleDateFormat("d MMMM YYYY", new Locale("pl", "PL"));

        if(type == SummaryInterval.DAY) {
            selectedInterval.setText(dateFormat.format(startSummaryInterval));
        } else {
            selectedInterval.setText(dateFormat.format(startSummaryInterval) + " - " + dateFormat.format(endSummaryInterval));
        }

        // Odświeżenie salda, sumy przychodów i wydatków
        refreshSummary();
    }

    private void changeSummaryInterval(int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startSummaryInterval);

        if(selectedSummaryInterval == SummaryInterval.DAY) {
            calendar.add(Calendar.DATE, type);
            startSummaryInterval = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            calendar.add(Calendar.SECOND, -1);
            endSummaryInterval = calendar.getTime();
        }

        if(selectedSummaryInterval == SummaryInterval.WEEK) {
            calendar.add(Calendar.WEEK_OF_YEAR, type);
            startSummaryInterval = calendar.getTime();
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.add(Calendar.SECOND, -1);
            endSummaryInterval = calendar.getTime();
        }

        if(selectedSummaryInterval == SummaryInterval.MONTH) {
            calendar.add(Calendar.MONTH, type);
            startSummaryInterval = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            endSummaryInterval = calendar.getTime();
        }

        if(selectedSummaryInterval == SummaryInterval.YEAR) {
            calendar.add(Calendar.YEAR, type);
            startSummaryInterval = calendar.getTime();
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.SECOND, -1);
            endSummaryInterval = calendar.getTime();
        }

        DateFormat dateFormat = new SimpleDateFormat("d MMMM YYYY", new Locale("pl", "PL"));

        if(selectedSummaryInterval == SummaryInterval.DAY) {
            selectedInterval.setText(dateFormat.format(startSummaryInterval));
        } else {
            selectedInterval.setText(dateFormat.format(startSummaryInterval) + " - " + dateFormat.format(endSummaryInterval));
        }

        // Odświeżenie salda, sumy przychodów i wydatków
        refreshSummary();
    }

    public void refreshSummary() {
        float income, expense, saldo, avaliableMoney;
        List<CSummary> categories;

        income = database.getIncomeForInterval(startSummaryInterval, endSummaryInterval);
        expense = database.getExpenseForInterval(startSummaryInterval, endSummaryInterval);
        categories = database.getCategoriesSummaryCost(startSummaryInterval, endSummaryInterval);
        avaliableMoney = database.getAvaliableMoney();

        int[] categoryColors = new int[categories.size()];

        saldo = income - expense;

        incomeText.setText(String.format("%.2f", income) + "zł");
        expenseText.setText(String.format("%.2f", expense) + "zł");
        saldoButton.setText(String.format("%.2f", saldo) + "zł");
        this.avaliableMoney.setText(String.format("%.2f", avaliableMoney) + "zł");

        if(saldo < 0) {
            saldoButton.setBackgroundResource(R.color.expenseColor);
        } else {
            saldoButton.setBackgroundResource(R.color.incomeColor);
        }

        animals.setText("0.00zł");
        clothes.setText("0.00zł");
        cosmetics.setText("0.00zł");
        entertainment.setText("0.00zł");
        food.setText("0.00zł");
        health.setText("0.00zł");
        home.setText("0.00zł");
        media.setText("0.00zł");
        shopping.setText("0.00zł");
        sport.setText("0.00zł");
        taxi.setText("0.00zł");
        transport.setText("0.00zł");

        for(int i = 0; i < categories.size(); i++) {
            CSummary category = categories.get(i);

            String value = String.format("%.2f", category.value) + "zł";

            switch (category.name) {
                case Category.ANIMALS:
                    animals.setText(value);
                    categoryColors[i] = CategoryColor.ANIMALS;
                    break;
                case Category.CLOTHES:
                    clothes.setText(value);
                    categoryColors[i] = CategoryColor.CLOTHES;
                    break;
                case Category.COSMETICS:
                    cosmetics.setText(value);
                    categoryColors[i] = CategoryColor.COSMETICS;
                    break;
                case Category.ENTERTAINMENT:
                    entertainment.setText(value);
                    categoryColors[i] = CategoryColor.ENTERTAINMENT;
                    break;
                case Category.FOOD:
                    food.setText(value);
                    categoryColors[i] = CategoryColor.FOOD;
                    break;
                case Category.HEALTH:
                    health.setText(value);
                    categoryColors[i] = CategoryColor.HEALTH;
                    break;
                case Category.HOME:
                    home.setText(value);
                    categoryColors[i] = CategoryColor.HOME;
                    break;
                case Category.MEDIA:
                    media.setText(value);
                    categoryColors[i] = CategoryColor.MEDIA;
                    break;
                case Category.SHOPPING:
                    shopping.setText(value);
                    categoryColors[i] = CategoryColor.SHOPPING;
                    break;
                case Category.SPORT:
                    sport.setText(value);
                    categoryColors[i] = CategoryColor.SPORT;
                    break;
                case Category.TAXI:
                    taxi.setText(value);
                    categoryColors[i] = CategoryColor.TAXI;
                    break;
                case Category.TRANSPORT:
                    transport.setText(value);
                    categoryColors[i] = CategoryColor.TRANSPORT;
                    break;
            }
        }

        //Przygotowanie wykresu
        List<PieEntry> pieEntries = new ArrayList<>();

        for(CSummary category : categories) {
            pieEntries.add(new PieEntry(category.value, ""));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(categoryColors);
        PieData pieData = new PieData(dataSet);

        PieChart chart = findViewById(R.id.chart);
        chart.setData(pieData);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        chart.setHoleRadius(60);
        chart.setHoleColor(getResources().getColor(R.color.colorPrimary));
        chart.setEntryLabelTextSize(0);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(255);

        chart.invalidate();
    }
}
