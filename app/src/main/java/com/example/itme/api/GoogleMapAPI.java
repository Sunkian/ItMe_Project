package com.example.itme.api;

import com.example.itme.entities.PlacesResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapAPI {

  @GET("place/nearbysearch/json")
  Call<PlacesResults> getNearBy(
    @Query("location") String location,
    @Query("radius") int radius,
    @Query("type") String type,
    @Query("key") String key
  );
}