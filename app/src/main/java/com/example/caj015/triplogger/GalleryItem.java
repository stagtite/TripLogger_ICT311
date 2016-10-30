package com.example.caj015.triplogger;

public class GalleryItem {
    private String tCaption;
    private String tId;
    private String tUrl;
    private double tLat;
    private double tLon;

    public String getUrl() {
        return tUrl;
    }

    public void setUrl(String url) {
        tUrl = url;
    }

    public String getCaption() {
        return tCaption;
    }

    public void setCaption(String caption) {
        tCaption = caption;
    }

    public String getId() {
        return tId;
    }

    public void setId(String id) {
        tId = id;
    }

    public double getLat() {
        return tLat;
    }

    public void setLat(double lat) {
        tLat = lat;
    }

    public double getLon() {
        return tLon;
    }

    public void setLon(double lon) {
        tLon = lon;
    }

    @Override
    public String toString() {
        return tCaption;
    }
}
