package com.example.homepharmacist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements AddDialog.DialogListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DatabaseFragment()).commit();

        bottomNav.setSelectedItemId(R.id.nav_database);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){

                        case R.id.nav_database:
                            selectedFragment = new DatabaseFragment();
                            break;
                        case R.id.nav_reminders:
                            selectedFragment = new RemindersFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
    @Override
    public void applyTexts(String brandname, String drugname, String remarks, String exp, int id, boolean delete) {


        SQLiteHelper dbHelper = new SQLiteHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (delete)
            db.delete("DRUG","ID="+id,null);
        else {
            ContentValues values = new ContentValues();
            values.put("BRANDNAME", brandname);
            values.put("DRUGNAME", drugname);
            values.put("EXP", exp);
            values.put("REMARKS", remarks);
            if (id == -1)
                db.insert("DRUG", null, values);
            else
                db.update("DRUG", values, "ID=" + id, null);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DatabaseFragment()).commit();

    }





}
