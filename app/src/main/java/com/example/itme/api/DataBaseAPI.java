package com.example.itme.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataBaseAPI {

  @GET("register")
  Call<String> register(
    @Query("email") String email,
    @Query("password") String password,
    @Query("age") int age,
    @Query("gender") String gender
  );

  @GET("login")
  Call<String> logIn(
    @Query("email") String email,
    @Query("password") String password
  );


  @GET("newRestaurant")
  Call<String> newRestaurant(
    @Query("name") String name,
    @Query("type") String type,
    @Query("address") String address,
    @Query("priceLevel") String priceLevel,
    @Query("ratingGoogle") String ratingGoogle,
    @Query("placeId") String placeId

  );

  @GET("newRating")
  Call<String> newRating(
    @Query("user") String user,
    @Query("restaurant") String restaurant,
    @Query("rating") Float rating
  );


  @GET("getTheRating")
  Call<Float> getTheRating(
    @Query("user") String user,
    @Query("restaurant") String restaurant
  );


  @POST("recommendation")
  @FormUrlEncoded
  Call<Integer> recommendation(
    @Field("userId") String user,
    @Field("listOfRestaurant") List<List<Integer>> listOfRestaurant
  );



}