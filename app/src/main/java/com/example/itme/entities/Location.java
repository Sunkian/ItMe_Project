package com.example.itme.entities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

  @SerializedName("lat")
  private Double lat;

  @SerializedName("lng")
  private Double lng;

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLng() {
    return lng;
  }

  public void setLng(Double lng) {
    this.lng = lng;
  }

}