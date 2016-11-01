package com.example.caj015.triplogger;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.caj015.triplogger.database.CursorWrappers;
import com.example.caj015.triplogger.database.TripBaseHelper;
import com.example.caj015.triplogger.database.TripDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TripLab
{
    private static TripLab tTripLab;
    private static TripLab oTripLab;
    private Context tContext;
    private SQLiteDatabase tDatabase;

    //Get context for Trip
    public static TripLab get(Context context)
    {
        if(tTripLab == null)
        {
            tTripLab = new TripLab(context);
        }

        return tTripLab;
    }

    //Get context for Options
    public static TripLab getO(Context context)
    {
        if(oTripLab == null)
        {
            oTripLab = new TripLab (context);
        }

        return oTripLab;
    }

    //Trip Lab reference - Both Trip and Options
    private TripLab(Context context)
    {
        tContext = context.getApplicationContext();
        tDatabase = new TripBaseHelper(tContext).getWritableDatabase();
    }

    //Add Trip
    public void addTrip(Trip t)
    {
        ContentValues values = getContentValues(t);

        tDatabase.insert(TripDbSchema.TripTable.NAME, null, values);
    }

    //Delete Trip
    public void deleteTrip(Trip trip)
    {
        String uuidString = trip.gettId().toString();

        tDatabase.delete(TripDbSchema.TripTable.NAME, TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    //Read Trip
    public List<Trip> gettTrips()
    {
        List<Trip> trips = new ArrayList<>();

        CursorWrappers cursor = queryTrips(null, null);

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

    //Read Trip also(?)
    public Trip gettTrip(UUID id)
    {
        CursorWrappers cursor = queryTrips(TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] {id.toString()});

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

    //Read Options also (?)
    public Opt getoOpt(Integer id)
    {
        CursorWrappers cursor = queryOpts(TripDbSchema.OptTable.Cols.IDENT + " = ?", new String[] {id.toString()});

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
            cursor.close();;
        }
    }

    //Update Trip
    public void updateTrip(Trip trip)
    {
        String uuidString = trip.gettId().toString();
        ContentValues values = getContentValues(trip);

        tDatabase.update(TripDbSchema.TripTable.NAME, values,
                TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    //Update Opts
    public void updateOpt(Opt opt)
    {
        String name = opt.getoName();
        ContentValues values = getContentValues(opt);

        tDatabase.update(TripDbSchema.OptTable.NAME, values,
                TripDbSchema.OptTable.Cols.NAME + " = ?", new String [] {name});
    }

    //Read photo
    public File getPhotoFile(Trip trip)
    {
        File externalFilesDir = tContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null)
        {
            return null;
        }

        return new File(externalFilesDir, trip.getPhotoFileName());
    }

    //Trip values
    private static ContentValues getContentValues(Trip trip)
    {
        ContentValues values = new ContentValues();
        values.put(TripDbSchema.TripTable.Cols.UUID, trip.gettId().toString());
        values.put(TripDbSchema.TripTable.Cols.TITLE, trip.gettTitle());
        values.put(TripDbSchema.TripTable.Cols.DATE, trip.gettDate());
        values.put(TripDbSchema.TripTable.Cols.TYPE, trip.gettType());
        values.put(TripDbSchema.TripTable.Cols.DESTINATION, trip.gettDestination());
        values.put(TripDbSchema.TripTable.Cols.DURATION, trip.gettDuration());
        values.put(TripDbSchema.TripTable.Cols.COMMENT, trip.gettComment());
        values.put(TripDbSchema.TripTable.Cols.LATITUDE, trip.gettLat());
        values.put(TripDbSchema.TripTable.Cols.LONGITUDE, trip.gettLon());

        return values;
    }

    //Options values
    private static ContentValues getContentValues(Opt optSet)
    {
        ContentValues values = new ContentValues();

        values.put(TripDbSchema.OptTable.Cols.NAME, optSet.getoName());
        values.put(TripDbSchema.OptTable.Cols.ID, optSet.getoId());
        values.put(TripDbSchema.OptTable.Cols.GENDER, optSet.getoGender());
        values.put(TripDbSchema.OptTable.Cols.EMAIL, optSet.getoEmail());
        values.put(TripDbSchema.OptTable.Cols.COMMENT, optSet.getoComment());

        return values;
    }

    //Trip Cursor Wrapper
    private CursorWrappers queryTrips(String whereClause, String[] whereArgs)
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
        return new CursorWrappers(cursor);
    }

    //Options Cursor Wrapper
    public CursorWrappers queryOpts(String whereClause, String[] whereArgs)
    {
        Cursor cursor = tDatabase.query(
                TripDbSchema.OptTable.NAME,
                null, //Columns
                whereClause,
                whereArgs,
                null, //Group by
                null, //Having
                null //Order by
        );

        return new CursorWrappers(cursor);
    }
}


















