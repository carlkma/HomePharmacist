package com.example.homepharmacist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class ShowRemarks extends AppCompatDialogFragment {
    String[] info;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (info[4] == null | info[4].isEmpty())
            builder.setMessage("No remarks");
        else
            builder.setMessage(info[4]);
        builder.setTitle("Remarks")
                .setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddDialog addDialog = new AddDialog(info);
                        addDialog.show(getFragmentManager(),"FORM");
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //nothing
                    }
                });


        return builder.create();

    }

    public ShowRemarks(String[] info){
        super();
        this.info = info;
    }
}
