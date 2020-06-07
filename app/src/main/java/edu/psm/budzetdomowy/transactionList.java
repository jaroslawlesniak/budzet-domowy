package edu.psm.budzetdomowy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import edu.psm.budzetdomowy.src.CBalanceSummary;
import edu.psm.budzetdomowy.src.CDatabase;
import edu.psm.budzetdomowy.src.CTransaction;
import edu.psm.budzetdomowy.utils.Category;
import edu.psm.budzetdomowy.utils.Transaction;

public class transactionList extends BottomSheetDialogFragment {

    LinearLayout linearLayout;
    List<CBalanceSummary> history = new LinkedList<>();
    Date startDate, endDate;
    CDatabase database;
    private ActionMode mActionMode;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_transaction_list, container, false);

        linearLayout = v.findViewById(R.id.transactions);

        List<CTransaction> transactions = database.getTransactions(startDate, endDate);
        history = parseTransactionsToHistory(transactions);

        setContentViews();

        return v;
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
            final ImageButton toggle = categoryView.findViewById(R.id.toggle);

            if(category.type == Transaction.EXPENSE) {
                title.setText(category.category);
                totalValue.setTextColor(getResources().getColor(R.color.expenseColor));

                switch (category.category) {
                    case Category.HOME:
                        categoryIcon.setBackgroundResource(R.drawable.dom);
                        break;
                    case Category.COSMETICS:
                        categoryIcon.setBackgroundResource(R.drawable.kosmetyki);
                        break;
                    case Category.MEDIA:
                        categoryIcon.setBackgroundResource(R.drawable.media);
                        break;
                    case Category.FOOD:
                        categoryIcon.setBackgroundResource(R.drawable.restauracja);
                        break;
                    case Category.ENTERTAINMENT:
                        categoryIcon.setBackgroundResource(R.drawable.rozrywka);
                        break;
                    case Category.SPORT:
                        categoryIcon.setBackgroundResource(R.drawable.sport);
                        break;
                    case Category.TAXI:
                        categoryIcon.setBackgroundResource(R.drawable.taxi);
                        break;
                    case Category.TRANSPORT:
                        categoryIcon.setBackgroundResource(R.drawable.transport);
                        break;
                    case Category.CLOTHES:
                        categoryIcon.setBackgroundResource(R.drawable.ubrania);
                        break;
                    case Category.HEALTH:
                        categoryIcon.setBackgroundResource(R.drawable.zdrowie);
                        break;
                    case Category.ANIMALS:
                        categoryIcon.setBackgroundResource(R.drawable.zwierzeta);
                        break;
                    case Category.SHOPPING:
                        categoryIcon.setBackgroundResource(R.drawable.zywnosc);
                        break;
                }
            } else {
                title.setText("Przychód");
            }
            totalValue.setText(String.format("%.2f", category.totalValue) + "zł");


            linearLayout.addView(categoryLayout);

            final LinearLayout transactionsLayout = new LinearLayout(getContext());
            transactionsLayout.setOrientation(LinearLayout.VERTICAL);

            // Pokazywanie i ukrywanie transakcji dla kategorii
            categoryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(transactionsLayout.getVisibility() == LinearLayout.GONE) {
                        transactionsLayout.setVisibility(View.VISIBLE);
                        toggle.setBackgroundResource(R.drawable.arrow_up);
                    } else {
                        transactionsLayout.setVisibility(View.GONE);
                        toggle.setBackgroundResource(R.drawable.arrow_down);
                    }
                }
            });

            // Dodanie wszystkich transakcji dla danej kategorii
            for(CTransaction transaction : category.transactions) {
                View view = getLayoutInflater().inflate(R.layout.transactions_layout, null);

                TextView price = view.findViewById(R.id.totalValue);
                TextView date = view.findViewById(R.id.date);
                TextView note = view.findViewById(R.id.note);
                TextView icon = view.findViewById(R.id.icon);
                final ConstraintLayout transactionLayout = view.findViewById(R.id.layout);


//                transactionLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        if (mActionMode != null) {
//                            return false;
//                        }
//                        transactionLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                        mActionMode = getActivity().startActionMode(mActionModeCallback);
//                        return true;
//                    }
//                });

                transactionLayout.setOnLongClickListener(new View.OnLongClickListener() {
                     public boolean onLongClick(View v) {
                         System.out.println("id elementu" + v.getId());
                         OptionDialog optionDialog = new OptionDialog();
                         optionDialog.show(getFragmentManager(), "cokolwiek");
                         return true;
                     }
                });



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

            transactionsLayout.setVisibility(View.GONE);

            linearLayout.addView(transactionsLayout);
        }
    }
//to można wyrzucić
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.edit_transaction_menu, menu);
            mode.setTitle("Wybierz opcję");
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit_transaction:
                    System.out.println("edycja");
                    mode.finish();
                    return true;
                case R.id.delete_transaction:
                    System.out.println("usuwanie");
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };


    public List<CBalanceSummary> parseTransactionsToHistory(List<CTransaction> transactions) {
        List<CBalanceSummary> history = new ArrayList<>();

        history.add(new CBalanceSummary(Transaction.INCOME));

        for(CTransaction transaction : transactions) {
            if(transaction.type == Transaction.INCOME) {
                history.get(0).totalValue += transaction.value;
                history.get(0).transactions.add(transaction);
            } else {
                int categoryPosistion = getCategoryPosition(transaction.category, history);

                if(categoryPosistion == -1) {
                    CBalanceSummary balanceSummary = new CBalanceSummary(Transaction.EXPENSE, transaction.category);
                    balanceSummary.totalValue = transaction.value;
                    balanceSummary.transactions.add(transaction);

                    history.add(balanceSummary);
                } else {
                    history.get(categoryPosistion).totalValue += transaction.value;
                    history.get(categoryPosistion).transactions.add(transaction);
                }
            }
        }

        return history;
    }

    private int getCategoryPosition(String category, List<CBalanceSummary> balanceSummaries) {
        int index = -1;

        for(int i = 0; i < balanceSummaries.size(); i++) {
            if(balanceSummaries.get(i).category != null && balanceSummaries.get(i).category.equals(category)) {
                index = i;
                break;
            }
        }

        return index;
    }


}
