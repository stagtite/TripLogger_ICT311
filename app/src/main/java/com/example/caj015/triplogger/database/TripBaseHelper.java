package com.example.caj015.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.caj015.triplogger.database.TripDbSchema.TripTable;
import com.example.caj015.triplogger.database.TripDbSchema.SettingsTable;

public class TripBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tripBase.db";

    public TripBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TripTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TripTable.Cols.UUID + ", " +
                TripTable.Cols.TITLE + ", " +
                TripTable.Cols.DATE + ", " +
                TripTable.Cols.TYPE + "," +
                TripTable.Cols.DESTINATION + "," +
                TripTable.Cols.DURATION + "," +
                TripTable.Cols.COMMENT + "," +
                TripTable.Cols.LATITUDE + "," +
                TripTable.Cols.LONGITUDE +
                ")"
        );

        db.execSQL("create table " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.UUID + " integer primary key unique, " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.ID + ", " +
                SettingsTable.Cols.GENDER + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.COMMENT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
