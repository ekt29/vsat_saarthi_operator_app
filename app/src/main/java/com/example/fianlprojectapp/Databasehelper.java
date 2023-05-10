package com.example.fianlprojectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Databasehelper extends SQLiteOpenHelper {

    private static final String TAG = "Databasehelper";

    public Databasehelper(@Nullable Context context) {
        super(context, "myDatabase.db", null, 22);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists mytable(id integer primary key autoincrement, latitude text,longitude text,orbitalposition text,azimuth text,elevation text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists mytable");
        onCreate(db);
    }

    public void insertData(Data data) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", data.getLatitude());
        contentValues.put("longitude", data.getLongitude());
        contentValues.put("orbitalposition", data.getOrbitalposition());
        contentValues.put("azimuth", data.getAzimuth());
        contentValues.put("elevation", data.getElevation());
        long mytable = sqLiteDatabase.insert("mytable", null, contentValues);
        Log.e(TAG, "insertData " + mytable);

    }


}
