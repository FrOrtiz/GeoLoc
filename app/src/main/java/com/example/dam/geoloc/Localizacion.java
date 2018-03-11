package com.example.dam.geoloc;

import com.google.android.gms.maps.model.LatLng;

public class Localizacion {

    private LatLng latLng;
    private String date;

    public Localizacion() {
    }

    public Localizacion(LatLng latLng, String date) {
        this.latLng = latLng;
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
