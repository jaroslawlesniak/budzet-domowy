package edu.psm.budzetdomowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.SummaryInterval;
import edu.psm.budzetdomowy.utils.Transaction;

public class homepage extends AppCompatActivity implements View.OnClickListener {

    int selectedSummaryInterval = SummaryInterval.MONTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

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
                }

                if (id == R.id.week) {
                    selectedSummaryInterval = SummaryInterval.WEEK;
                }

                if (id == R.id.month) {
                    selectedSummaryInterval = SummaryInterval.MONTH;
                }

                if (id == R.id.year) {
                    selectedSummaryInterval = SummaryInterval.YEAR;
                }

                if (id == R.id.year) {
                    selectedSummaryInterval = SummaryInterval.ALL;
                }

                if (id == R.id.custom) {
                    selectedSummaryInterval = SummaryInterval.CUSTOM_DAY;
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
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
}
