package com.example.caj015.triplogger.database;


import android.database.Cursor;

import com.example.caj015.triplogger.Opt;
import com.example.caj015.triplogger.Trip;

import java.util.UUID;

public class CursorWrappers extends android.database.CursorWrapper
{
    public CursorWrappers(Cursor cursor)
    {
        super(cursor);
    }

    public Trip getTrip()
    {
        String uuidString = getString(getColumnIndex(TripDbSchema.TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripDbSchema.TripTable.Cols.TITLE));
        String date = getString(getColumnIndex(TripDbSchema.TripTable.Cols.DATE));
        String type = getString(getColumnIndex(TripDbSchema.TripTable.Cols.TYPE));
        String dest = getString(getColumnIndex(TripDbSchema.TripTable.Cols.DESTINATION));
        String duration = getString(getColumnIndex(TripDbSchema.TripTable.Cols.DURATION));
        String comment = getString(getColumnIndex(TripDbSchema.TripTable.Cols.COMMENT));
        String lat = getString(getColumnIndex(TripDbSchema.TripTable.Cols.LATITUDE));
        String lon = getString(getColumnIndex(TripDbSchema.TripTable.Cols.LONGITUDE));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.settTitle(title);
        trip.settDate(date);
        trip.settType(type);
        trip.settDestination(dest);
        trip.settDuration(duration);
        trip.settComment(comment);
        trip.settLat(lat);
        trip.settLon(lon);

        return trip;
    }

    public Opt getOpt()
    {
        Integer ident = getInt(getColumnIndex(TripDbSchema.OptTable.Cols.IDENT));
        String name = getString(getColumnIndex(TripDbSchema.OptTable.Cols.NAME));
        String id = getString(getColumnIndex(TripDbSchema.OptTable.Cols.ID));
        String gender = getString(getColumnIndex(TripDbSchema.OptTable.Cols.GENDER));
        String email = getString(getColumnIndex(TripDbSchema.OptTable.Cols.EMAIL));
        String comment = getString(getColumnIndex(TripDbSchema.OptTable.Cols.COMMENT));

        Opt opt = new Opt();
        //opt.setoIdent(ident); - Ident should never change
        opt.setoName(name);
        opt.setoId(id);
        opt.setoGender(gender);
        opt.setoEmail(email);
        opt.setoComment(comment);

        return opt;
    }
}
