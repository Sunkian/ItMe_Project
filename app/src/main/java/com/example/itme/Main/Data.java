package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.itme.R;
import com.example.itme.api.APIDataBase;
import com.example.itme.api.DataBaseAPI;
import com.google.android.gms.common.api.GoogleApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Data extends AppCompatActivity {

  private Button rate;
  private GoogleApiClient googleApiClient;
  private String photoReference;
  private int maxwidth = 800;
  private String restaurantId;
  private String name;
  private String placeId;
  private String rating;
  private String priceLevel;
  private String type;
  private String address;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_data);

    Intent intent = getIntent();
    name = intent.getStringExtra("name");
    placeId = intent.getStringExtra("placeId");
    rating = intent.getStringExtra("rating");
    String photo = intent.getStringExtra("photos");
    priceLevel = intent.getStringExtra("priceLevel");
    type = intent.getStringExtra("type");
    address = intent.getStringExtra("address");


    System.out.println("Le nom est " + name);
//        System.out.println("L'id est " + placeId);
    System.out.println("Le rating est " + rating);
    System.out.println("Le price level est " + priceLevel);

    TextView nameView = (TextView) findViewById(R.id.email);
    nameView.setText(name);
//        TextView placeIdView = (TextView) findViewById(R.id.placeId);
//        placeIdView.setText(placeId);
    TextView ratingView = (TextView) findViewById(R.id.rating);
    ratingView.setText(rating);
    TextView priceLevelView = (TextView) findViewById(R.id.price_level);
    priceLevelView.setText(priceLevel);

    this.rate = (Button) findViewById(R.id.rate);
    rate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view1) {
        goToRating();

      }
    });
    initRestaurant();
    accessPic2(photo);

  }

  private void initRestaurant() { //Faire un getRating (eme chose) dans rating pour afficher le rating qui a déjà été fait si yen a un
    DataBaseAPI databaseApi = APIDataBase.getClient().create(DataBaseAPI.class);
    databaseApi.newRestaurant(name, type, address, priceLevel, rating, placeId).enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        restaurantId = response.body();
//                    System.out.println("response:" + response.body());

      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }


  public void accessPic2(String photoReference) {
    ImageView ivPhoto = (ImageView) findViewById(R.id.photos);
    String url = "https://maps.googleapis.com/maps/api/place/photo" +
      "?maxwidth=800" +
      "&photoreference=" + photoReference +
      "&key=" + getText(R.string.google_maps_key).toString();

    Glide.with(this)
      .load(url)
      .into(ivPhoto);
  }

  public void goToRating() {
    Intent intent = new Intent(Data.this, RatingActivity.class);
    intent.putExtra("restaurantId", String.valueOf(restaurantId));
    startActivity(intent);
    finish();

  }

}