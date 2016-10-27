package com.example.caj015.triplogger.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.caj015.triplogger.Trip;

import java.util.Date;
import java.util.UUID;

public class TripCursorWrapper extends CursorWrapper
{
    public TripCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Trip getTrip()
    {
        String uuidString = getString(getColumnIndex(TripDbSchema.TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripDbSchema.TripTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TripDbSchema.TripTable.Cols.DATE));
        int isFinished = getInt(getColumnIndex(TripDbSchema.TripTable.Cols.FINISHED));
        String pic = getString(getColumnIndex(TripDbSchema.TripTable.Cols.PIC));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.settTitle(title);
        trip.settDate(new Date(date));
        trip.settFinish(isFinished != 0);
        trip.settPic(pic);

        return trip;
    }
}
