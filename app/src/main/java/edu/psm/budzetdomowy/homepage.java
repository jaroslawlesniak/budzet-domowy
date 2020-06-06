package edu.psm.budzetdomowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.psm.budzetdomowy.src.CDatabase;
import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.SummaryInterval;
import edu.psm.budzetdomowy.utils.Transaction;

public class homepage extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    // Czy wybieramy podsumowanie z dnia, miesiąca itp.
    int selectedSummaryInterval = SummaryInterval.MONTH;

    // Okresy podsumowania - od, do
    Date startSummaryInterval, endSummaryInterval;

    CDatabase database = new CDatabase(this);

    TextView selectedInterval;

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

        findViewById(R.id.addIncome).setOnClickListener(this);
        findViewById(R.id.addExpense).setOnClickListener(this);

        //Button otwierający listę transakcji
        Button buttonOpenBottomSheet = findViewById(R.id.button2);
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
                    selectedSummaryInterval = SummaryInterval.DAY;
                    setSummaryInterval(SummaryInterval.DAY);
                }

                if (id == R.id.week) {
                    selectedSummaryInterval = SummaryInterval.WEEK;
                    setSummaryInterval(SummaryInterval.WEEK);
                }

                if (id == R.id.month) {
                    selectedSummaryInterval = SummaryInterval.MONTH;
                    setSummaryInterval(SummaryInterval.MONTH);
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

        // Określenie pierwszego dnia miesiąca
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        startSummaryInterval = cal.getTime();

        // Określenie ostatniego dnia miesiąca
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        endSummaryInterval = cal.getTime();
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
        }
    }

    private void setSummaryInterval(int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if(type == SummaryInterval.DAY) {
            startSummaryInterval = cal.getTime();
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

    }
}
