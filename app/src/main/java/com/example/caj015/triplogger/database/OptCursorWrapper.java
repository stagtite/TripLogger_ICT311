package com.example.caj015.triplogger.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.caj015.triplogger.Opt;

import java.util.UUID;

public class OptCursorWrapper extends CursorWrapper
{
    public OptCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Opt getOpt()
    {
        String uuidString = getString(getColumnIndex(TripDbSchema.SettingsTable.Cols.UUID));
        String name = getString(getColumnIndex(TripDbSchema.SettingsTable.Cols.NAME));
        String id = getString(getColumnIndex(TripDbSchema.SettingsTable.Cols.ID));
        String gender = getString(getColumnIndex(TripDbSchema.SettingsTable.Cols.GENDER));
        String email = getString(getColumnIndex(TripDbSchema.SettingsTable.Cols.EMAIL));
        String comment = getString(getColumnIndex(TripDbSchema.SettingsTable.Cols.COMMENT));

        Opt optSet = new Opt(UUID.fromString(uuidString));
        optSet.setoName(name);
        optSet.setoId(id);
        optSet.setoGender(gender);
        optSet.setoEmail(email);
        optSet.setoComment(comment);

        return optSet;
    }
}
