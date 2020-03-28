package com.example.homepharmacist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.check(R.id.brandnameRB);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SQLiteHelper dbHelper = new SQLiteHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String info[] = new String[5];
                Cursor cursor =  db.query("DRUG", new String[] {"ID", "BRANDNAME", "DRUGNAME", "EXP", "REMARKS"}, "BRANDNAME=\""+(String) adapterView.getItemAtPosition(i)+"\"", null, null, null, null);
                if (cursor.moveToNext()) {
                    for (int count = 0; count < 5; count++)
                        info[count] = cursor.getString(count);
                }


                ShowRemarks a = new ShowRemarks(info);
                a.show(getFragmentManager(),"FORM");


            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_database,container,false);


        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i==R.id.brandnameRB)
                    displayDatabase(getView(),true);
                else
                    displayDatabase(getView(),false);
            }
        });

        Button addBTN = (Button) view.findViewById(R.id.addBTN);
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialog addDialog = new AddDialog(null);
                addDialog.show(getFragmentManager(),"FORM");
            }
        });

        return view;

    }




    public void displayDatabase(View view, Boolean sortByName){

        SQLiteHelper dbHelper = new SQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        int count = (int) DatabaseUtils.queryNumEntries(db,"DRUG");
        String[] brand_name = new String[count];
        String[] drug_name = new String[count];
        String[] exp_date = new String[count];
        String[] remarks = new String[count];

        int i = 0;
        Cursor cursor;
        if (sortByName)
            cursor = db.query("DRUG", new String[]{"ID", "BRANDNAME", "DRUGNAME", "EXP", "REMARKS"}, null, null, null, null, "BRANDNAME COLLATE NOCASE ASC");
        else
            cursor = db.query("DRUG", new String[]{"ID", "BRANDNAME", "DRUGNAME", "EXP", "REMARKS"}, null, null, null, null, "EXP COLLATE NOCASE ASC");
        while (cursor.moveToNext()) {
            brand_name[i] = cursor.getString(1);
            drug_name[i] = cursor.getString(2);
            exp_date[i] = cursor.getString(3);
            remarks[i] = cursor.getString(4);
            i++;
        }

        ListView listview = (ListView) view.findViewById(R.id.listView);


        myAdapter adapter = new myAdapter(getContext(),brand_name,drug_name,exp_date);
        listview.setAdapter(adapter);

        TextView totalTV = (TextView) view.findViewById(R.id.totalTV);
        totalTV.setText("Total: " + count + " entries");

    }

    class myAdapter extends ArrayAdapter<String>{
        String[] brand_name, drug_name, exp_date;
        Context context;
        public myAdapter(@NonNull Context context, String[] brand_name, String[] drug_name, String[] exp_date) {
            super(context, R.layout.drug_list,R.id.titleTV,brand_name);
            this.context = context;
            this.brand_name = brand_name;
            this.drug_name = drug_name;
            this.exp_date = exp_date;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.drug_list,parent,false);

            TextView title = row.findViewById(R.id.titleTV);
            TextView subtitle = row.findViewById(R.id.subtitleTV);
            TextView side = row.findViewById(R.id.sideTV);

            title.setText(brand_name[position]);
            subtitle.setText(drug_name[position]);
            side.setText(exp_date[position]);

            return row;
        }
    }

}
