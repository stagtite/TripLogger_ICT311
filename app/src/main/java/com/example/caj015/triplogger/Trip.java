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
    private String tType;
    private String tDest;
    private String tDuration;
    private String tComment;
    private String tLat;
    private String tLon;

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

    public String gettType()
    {
        return tType;
    }

    public void settType(String type)
    {
        tType = type;
    }

    public String gettDestination()
    {
        return tDest;
    }

    public void settDestination(String dest)
    {
        tDest = dest;
    }

    public String gettDuration()
    {
        return tDuration;
    }

    public void settDuration(String duration)
    {
        tDuration = duration;
    }

    public String gettComment()
    {
        return tComment;
    }

    public void settComment(String comment)
    {
        tComment = comment;
    }

    public String gettLat()
    {
        return tLat;
    }

    public void settLat(String lat)
    {
        tLat = lat;
    }

    public String gettLon()
    {
        return tLon;
    }

    public void settLon(String lon)
    {
        tLon = lon;
    }
}