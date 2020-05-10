package edu.psm.budzetdomowy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import edu.psm.budzetdomowy.R;
import edu.psm.budzetdomowy.src.CBalanceSummary;
import edu.psm.budzetdomowy.src.CTransaction;
import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.Transaction;

public class transactionList extends BottomSheetDialogFragment {

    LinearLayout linearLayout;
    List<CBalanceSummary> history = new LinkedList<>();

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
        for(CBalanceSummary transaction : history) {
            // Stworzenie pojedynczej kategorii
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);

            // Dodanie przycisku (strzałka w dół albo w górę), wyświetla wszystkie transakcje dla wybrajen kategorii
            ImageButton expandButton = new ImageButton(getContext());
            expandButton.setBackgroundResource(R.drawable.taxi);
            expandButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

            //Ikona kategorii
            ImageView categoryIcon = new ImageButton(getContext());
            categoryIcon.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            categoryIcon.setBackgroundResource(getStringIdentifier(getContext(), transaction.category));

            // Nazwa kategorii
            TextView categoryName = new TextView(getContext());
            categoryName.setTextColor(Color.WHITE);

            if(transaction.type == Transaction.EXPENSE) {
                categoryName.setText(transaction.category);
            } else {
                categoryName.setText("Przychód");
            }

            // Suma przychodów/wydatków dla kategorii
            TextView totalValue = new TextView(getContext());
            totalValue.setText(String.format("%.2f", transaction.totalValue) + "zł");

            if(transaction.type == Transaction.EXPENSE) {
                totalValue.setTextColor(getResources().getColor(R.color.expenseColor));
            } else {
                totalValue.setTextColor(getResources().getColor(R.color.incomeColor));
            }

            // Lista transakcji dla danej kategorii


            row.addView(expandButton);
            row.addView(categoryIcon);
            row.addView(categoryName);
            row.addView(totalValue);

            linearLayout.addView(row);
        }
    }

    public int getStringIdentifier(Context context, String name) {
        if(name == null) {
            name = "";
        }

        return context.getResources().getIdentifier(name + ".png", "string", context.getPackageName());
    }

    class TransactionsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
