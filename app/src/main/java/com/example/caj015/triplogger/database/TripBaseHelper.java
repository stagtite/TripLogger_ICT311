package com.example.caj015.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.caj015.triplogger.database.TripDbSchema.TripTable;
import com.example.caj015.triplogger.database.TripDbSchema.OptTable;

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

        db.execSQL("create table " + OptTable.NAME + "(" +
                " _ident integer primary key, " +
                OptTable.Cols.IDENT + ", " +
                OptTable.Cols.NAME + ", " +
                OptTable.Cols.ID + ", " +
                OptTable.Cols.GENDER + ", " +
                OptTable.Cols.EMAIL + ", " +
                OptTable.Cols.COMMENT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
