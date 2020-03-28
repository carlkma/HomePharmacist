package com.example.homepharmacist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Data.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE DRUG (ID INTEGER PRIMARY KEY,BRANDNAME TEXT,DRUGNAME TEXT,EXP TEXT,REMARKS TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS DRUG";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        ContentValues values = new ContentValues();
        values.put("BRANDNAME", "Tylenol");
        values.put("DRUGNAME", "Acetaminophen");
        values.put("EXP", "2025-2-11");
        values.put("REMARKS", "SAMPLE");
        db.insert("DRUG", null, values);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
