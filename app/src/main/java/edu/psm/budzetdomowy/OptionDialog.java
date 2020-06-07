package edu.psm.budzetdomowy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import edu.psm.budzetdomowy.src.CDatabase;

public class OptionDialog extends DialogFragment {

    CDatabase cDatabase = new CDatabase(this);

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Wybierz opcję")
                .setPositiveButton("Edytuj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToAnActivity();
                    }
                })
                .setNegativeButton("Usuń", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cDatabase.deleteTransaction(1200);
                        //trzeba przesłać id
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void goToAnActivity() {
        Intent intent = new Intent(this, TransactionManager.class);
        startActivity(intent);
        //trzeba przesłać wartości do wypełnienia
    }
}
