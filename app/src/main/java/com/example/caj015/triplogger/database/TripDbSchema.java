package com.example.caj015.triplogger.database;



public class TripDbSchema
{
    public static final class TripTable
    {
        public static final String NAME = "trips";
        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String FINISHED = "finished";
            public static final String PIC = "pic";
        }
    }
}
