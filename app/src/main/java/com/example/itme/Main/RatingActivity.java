package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.itme.GlobalVariable;
import com.example.itme.R;
import com.example.itme.api.APIDataBase;
import com.example.itme.api.DataBaseAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {


  private Button retourR;
  private String restaurantId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rating);

    Intent intent = getIntent();
    restaurantId = intent.getStringExtra("restaurantId");


    // initiate rating bar and a button
    final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
    Button submitButton = (Button) findViewById(R.id.submitButton);
    getTheRating(simpleRatingBar); //On appelle la fonction pour que l'ancien rating s'affich dès qu'on arrive sur la page

    // perform click event on button
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v23) {
        // get values and then displayed in a toast
        String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
        Float rating =  simpleRatingBar.getRating();
        Toast.makeText(getApplicationContext(), totalStars + "\n" + rating, Toast.LENGTH_LONG).show();
        newRating(rating);
      }
    });

    retourR = (Button) findViewById(R.id.retourR);
    retourR.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intentRetour = new Intent(RatingActivity.this, ConnexionActivity.class);
        startActivity(intentRetour);
      }
    });


  }

  private void newRating(Float rating) {
    GlobalVariable gv = ((GlobalVariable) getApplicationContext());
    String user = gv.getId();
    DataBaseAPI databaseApi = APIDataBase.getClient().create(DataBaseAPI.class);
    databaseApi.newRating(user, restaurantId, rating).enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        if (response.body() != "Duplicate") {
          System.out.println("response:" + response.body());
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }

  private void getTheRating(final RatingBar simpleRatingBar){
    GlobalVariable gv = ((GlobalVariable) getApplicationContext());
    String user = gv.getId();
    DataBaseAPI databaseApi = APIDataBase.getClient().create(DataBaseAPI.class);
    databaseApi.getTheRating(user, restaurantId).enqueue(new Callback<Float>() {
      @Override
      public void onResponse(Call<Float> call, Response<Float> response) {
        System.out.println("response:" + response.body());
        simpleRatingBar.setRating(response.body());
      }

      @Override
      public void onFailure(Call<Float> call, Throwable t) {

      }
    });
  }

}