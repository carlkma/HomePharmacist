package com.example.homepharmacist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddDialog extends AppCompatDialogFragment {
    private EditText brandnameET;
    private EditText drugnameET;
    private EditText remarksET;
    private DatePicker datepicker;
    private DialogListener listener;
    String[] info;
    int id = -1;

    public AddDialog(String[] info){
        super();
        this.info = info;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.form_add,null);
        brandnameET = (EditText) view.findViewById(R.id.brandnameET);
        drugnameET = (EditText) view.findViewById(R.id.drugnameET);
        remarksET = (EditText) view.findViewById(R.id.remarksET);
        datepicker = (DatePicker) view.findViewById(R.id.datePicker);

        if (info != null){
            brandnameET.setText(info[1]);
            drugnameET.setText(info[2]);
            remarksET.setText(info[4]);
            String[] date = info[3].split("-");
            datepicker.updateDate(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
            builder.setTitle("Modify Drug");
            id = Integer.parseInt(info[0]);
        }
        else
            builder.setTitle("Add Drug");

        builder.setView(view)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String brandname = brandnameET.getText().toString();
                        String drugname = drugnameET.getText().toString();
                        String remarks = remarksET.getText().toString();
                        String exp = datepicker.getYear()+"-"+datepicker.getMonth()+"-"+datepicker.getDayOfMonth();
                        listener.applyTexts(brandname,drugname,remarks,exp,id,false);
                    }
                })
                .setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyTexts(null,null,null,null,id,true);
                    }
                });



        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "must implement DialogListener");

        }

    }

    public interface DialogListener{
        void applyTexts(String brandname, String drugname, String remarks, String exp, int id, boolean delete);
    }

}
