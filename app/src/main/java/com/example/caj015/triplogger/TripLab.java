package com.example.caj015.triplogger;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.caj015.triplogger.database.OptCursorWrapper;
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

    public void deleteTrip(Trip trip)
    {
        String uuidString = trip.gettId().toString();

        tDatabase.delete(TripDbSchema.TripTable.NAME, TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] {uuidString});
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
        values.put(TripDbSchema.TripTable.Cols.TYPE, trip.gettType());
        values.put(TripDbSchema.TripTable.Cols.DESTINATION, trip.gettDestination());
        values.put(TripDbSchema.TripTable.Cols.DURATION, trip.gettDuration());
        values.put(TripDbSchema.TripTable.Cols.COMMENT, trip.gettComment());
        values.put(TripDbSchema.TripTable.Cols.LATITUDE, trip.gettLat());
        values.put(TripDbSchema.TripTable.Cols.LONGITUDE, trip.gettLon());

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

    //OPTIONS SECTION - MERGED FROM OPTLAB
    private static TripLab oOptLab;
    private Context oContext;
    private SQLiteDatabase oDatabase;

    /*public static OptLab get(Context context)
    {
        if(oOptLab == null)
        {
            oOptLab = new OptLab(context);
        }

        return oOptLab;
    }

    private OptLab(Context context)
    {
        oContext = context.getApplicationContext();
        oDatabase = new TripBaseHelper(oContext).getWritableDatabase();
    }*/

    public void addOpt(Opt o)
    {
        ContentValues values = getContentValues(o);

        oDatabase.insert(TripDbSchema.SettingsTable.NAME, null, values);
    }

    public void deleteOpt(Opt optSet)
    {
        String uuidString = optSet.getoId();

        oDatabase.delete(TripDbSchema.SettingsTable.NAME, TripDbSchema.SettingsTable.Cols.UUID + " = ?", new String[] {uuidString});
    }


    public List<Opt> getoOptions()
    {
        List<Opt> opts = new ArrayList<>();

        OptCursorWrapper cursor = queryOpts(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                opts.add(cursor.getOpt());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return opts;
    }

    public Opt getoOptions(UUID id)
    {
        OptCursorWrapper cursor = queryOpts(TripDbSchema.SettingsTable.Cols.UUID + " = ?", new String[] {id.toString()});

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getOpt();
        }
        finally
        {
            cursor.close();
        }
    }

    public void updateOpt(Opt optSet)
    {
        String uuidString = optSet.getoId().toString();
        ContentValues values = getContentValues(optSet);

        oDatabase.update(TripDbSchema.SettingsTable.NAME, values, TripDbSchema.SettingsTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    private static ContentValues getContentValues(Opt optSet)
    {
        ContentValues values = new ContentValues();
        values.put(TripDbSchema.SettingsTable.Cols.UUID, optSet.getoId());
        values.put(TripDbSchema.SettingsTable.Cols.NAME, optSet.getoName());
        values.put(TripDbSchema.SettingsTable.Cols.ID, optSet.getoId());
        values.put(TripDbSchema.SettingsTable.Cols.GENDER, optSet.getoGender());
        values.put(TripDbSchema.SettingsTable.Cols.EMAIL, optSet.getoEmail());
        values.put(TripDbSchema.SettingsTable.Cols.COMMENT, optSet.getoComment());

        return values;
    }

    private OptCursorWrapper queryOpts(String whereClause, String[] whereArgs)
    {
        Cursor cursor = oDatabase.query(
                TripDbSchema.SettingsTable.NAME,
                null, //Columns
                whereClause,
                whereArgs,
                null, //Group by
                null, //Having
                null //Order by
        );
        return new OptCursorWrapper(cursor);
    }
}


















