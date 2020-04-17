package edu.psm.budzetdomowy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.Transaction;

public class homepage extends AppCompatActivity implements View.OnClickListener {

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
