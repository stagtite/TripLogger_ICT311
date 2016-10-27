package com.example.caj015.triplogger;

import java.util.Date;
import java.util.UUID;

public class Trip
{
    private UUID tId;
    private String tTitle;
    private Date tDate;
    private boolean tFinish;
    private String tPic;

    public Trip()
    {
        this(UUID.randomUUID());
    }

    public Trip(UUID id)
    {
        tId = id;
        tDate = new Date();
    }

    public UUID gettId()
    {
        return tId;
    }

    public String gettTitle()
    {
        return tTitle;
    }

    public void settTitle(String title)
    {
        tTitle = title;
    }

    public Date gettDate()
    {
        return tDate;
    }

    public void settDate(Date date)
    {
        tDate = date;
    }

    public boolean istFinish()
    {
        return tFinish;
    }

    public void settFinish(boolean finished)
    {
        tFinish = finished;
    }

    public String gettPic()
    {
        return tPic;
    }

    public void settPic(String pic)
    {
        tPic = pic;
    }

    public String getPhotoFileName()
    {
        return "IMG_" + gettId().toString() + ".jpg";
    }
}
