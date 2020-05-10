package edu.psm.budzetdomowy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import edu.psm.budzetdomowy.src.CBalanceSummary;
import edu.psm.budzetdomowy.src.CTransaction;
import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.Transaction;

public class transactionList extends BottomSheetDialogFragment {

    LinearLayout linearLayout;
    List<CBalanceSummary> history = new LinkedList<>();
   List<CTransaction> categoryTransactions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_transaction_list, container, false);

        linearLayout = v.findViewById(R.id.transactions);

        MockData();
        setContentViews();

        return v;
    }

    public void MockData() {
        //Dodanie testowych danych
        CBalanceSummary cb1 = new CBalanceSummary(Transaction.INCOME, null);
        CBalanceSummary cb2 = new CBalanceSummary(Transaction.EXPENSE, Category.CLOTHES);
        CBalanceSummary cb3 = new CBalanceSummary(Transaction.EXPENSE, Category.FOOD);

        cb1.totalValue = 1455;
        cb2.totalValue = 5468;
        cb3.totalValue = 315;

        cb1.transactions.add(new CTransaction(1, 1000, new Date(2020,10,11), Transaction.INCOME, null, ""));
        cb1.transactions.add(new CTransaction(2, 445, new Date(2020,10,10), Transaction.INCOME, null, ""));

        cb2.transactions.add(new CTransaction(3, 468, new Date(2020,10,11), Transaction.EXPENSE, Category.CLOTHES, ""));
        cb2.transactions.add(new CTransaction(4, 3000, new Date(2020,10,10), Transaction.EXPENSE, Category.CLOTHES, "Nowe buty"));
        cb2.transactions.add(new CTransaction(5, 2000, new Date(2020,10,9), Transaction.EXPENSE, Category.CLOTHES, ""));

        cb3.transactions.add(new CTransaction(6, 315, new Date(2020,10,9), Transaction.EXPENSE, Category.FOOD, ""));

        history.add(cb1);
        history.add(cb2);
        history.add(cb3);
    }

    public void setContentViews() {
        //Wyświetlanie wszystkich kategorii
        for(CBalanceSummary category : history) {
            // Stworzenie pojedynczej kategorii
            View categoryView = getLayoutInflater().inflate(R.layout.category_layout, null);

            TextView title = categoryView.findViewById(R.id.title);
            TextView totalValue = categoryView.findViewById(R.id.totalValue);
            ImageView categoryIcon = categoryView.findViewById(R.id.category_icon);
            ConstraintLayout categoryLayout = categoryView.findViewById(R.id.layout);

            if(category.type == Transaction.EXPENSE) {
                title.setText(category.category);
                totalValue.setTextColor(getResources().getColor(R.color.expenseColor));
            } else {
                title.setText("Przychód");
            }
            totalValue.setText(String.format("%.2f", category.totalValue) + "zł");


            linearLayout.addView(categoryLayout);

            LinearLayout transactionsLayout = new LinearLayout(getContext());
            transactionsLayout.setOrientation(LinearLayout.VERTICAL);

            // Dodanie wszystkich transakcji dla danej kategorii
            for(CTransaction transaction : category.transactions) {
                View view = getLayoutInflater().inflate(R.layout.transactions_layout, null);

                TextView price = view.findViewById(R.id.totalValue);
                TextView date = view.findViewById(R.id.date);
                TextView note = view.findViewById(R.id.note);
                TextView icon = view.findViewById(R.id.icon);
                ConstraintLayout transactionLayout = view.findViewById(R.id.layout);

                DateFormat dateFormat = new SimpleDateFormat("d MMMM", new Locale("pl", "PL"));
                date.setText(dateFormat.format(transaction.date.getTime()));

                if(transaction.type == Transaction.EXPENSE) {
                    icon.setBackground(getResources().getDrawable(R.drawable.rounded_type_expense));
                } else {
                    icon.setBackground(getResources().getDrawable(R.drawable.rounded_type_income));
                }

                if(transaction.note == "") {
                    note.setVisibility(View.GONE);
                } else {
                    note.setText(transaction.note);
                }

                price.setText(String.format("%.2f", transaction.value) + "zł");

                transactionsLayout.addView(transactionLayout);
            }

            linearLayout.addView(transactionsLayout);
        }
    }

    public int getStringIdentifier(Context context, String name) {
        if(name == null) {
            name = "";
        }

        return context.getResources().getIdentifier(name + ".png", "string", context.getPackageName());
    }
}
