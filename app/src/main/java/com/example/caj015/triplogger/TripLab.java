package com.example.caj015.triplogger;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.caj015.triplogger.database.TripBaseHelper;
import com.example.caj015.triplogger.database.TripCursorWrapper;
import com.example.caj015.triplogger.database.TripDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TripLab
{
    private static TripLab tTripLab;
    private Context tContext;
    private SQLiteDatabase tDatabase;

    public static TripLab get(Context context)
    {
        if(tTripLab == null)
        {
            tTripLab = new TripLab(context);
        }

        return tTripLab;
    }

    private TripLab(Context context)
    {
        tContext = context.getApplicationContext();
        tDatabase = new TripBaseHelper(tContext).getWritableDatabase();
    }

    public void addTrip(Trip t)
    {
        ContentValues values = getContentValues(t);

        tDatabase.insert(TripDbSchema.TripTable.NAME, null, values);
    }

    public List<Trip> gettTrips()
    {
        List<Trip> trips = new ArrayList<>();

        TripCursorWrapper cursor = queryTrips(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                trips.add(cursor.getTrip());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return trips;
    }

    public Trip gettTrip(UUID id)
    {
        TripCursorWrapper cursor = queryTrips(TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] {id.toString()});

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTrip();
        }
        finally
        {
            cursor.close();
        }
    }

    public File getPhotoFile(Trip trip)
    {
        File externalFilesDir = tContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null)
        {
            return null;
        }

        return new File(externalFilesDir, trip.getPhotoFileName());
    }

    public void updateTrip(Trip trip)
    {
        String uuidString = trip.gettId().toString();
        ContentValues values = getContentValues(trip);

        tDatabase.update(TripDbSchema.TripTable.NAME, values, TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    private static ContentValues getContentValues(Trip trip)
    {
        ContentValues values = new ContentValues();
        values.put(TripDbSchema.TripTable.Cols.UUID, trip.gettId().toString());
        values.put(TripDbSchema.TripTable.Cols.TITLE, trip.gettTitle());//.toString());
        values.put(TripDbSchema.TripTable.Cols.DATE, trip.gettDate().toString());
        values.put(TripDbSchema.TripTable.Cols.FINISHED, trip.istFinish() ? 1:0);
        values.put(TripDbSchema.TripTable.Cols.PIC, trip.gettPic());

        return values;
    }

    private TripCursorWrapper queryTrips(String whereClause, String[] whereArgs)
    {
        Cursor cursor = tDatabase.query(
                TripDbSchema.TripTable.NAME,
                null, //Columns
                whereClause,
                whereArgs,
                null, //Group by
                null, //Having
                null //Order by
        );
        return new TripCursorWrapper(cursor);
    }
}


















