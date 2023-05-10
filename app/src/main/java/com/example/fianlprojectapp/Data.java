package com.example.fianlprojectapp;

public class Data {

    private String latitude;
    private String longitude;
    private String orbitalposition;
    private String azimuth;
    private String elevation;

    public Data(String latitude, String longitude, String orbitalposition, String azimuth, String elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.orbitalposition = orbitalposition;
        this.azimuth = azimuth;
        this.elevation = elevation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrbitalposition() {
        return orbitalposition;
    }

    public void setOrbitalposition(String orbitalposition) {
        this.orbitalposition = orbitalposition;
    }

    public String getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(String azimuth) {
        this.azimuth = azimuth;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
}
