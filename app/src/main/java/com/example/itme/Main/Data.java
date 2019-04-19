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
import com.google.android.gms.common.api.GoogleApiClient;


public class Data extends AppCompatActivity {

    private Button rate;
    private GoogleApiClient googleApiClient;
    private String photoReference;
    private int maxwidth = 800;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
//        String placeId = intent.getStringExtra("placeId");
        String rating = intent.getStringExtra("rating");
        String photo = intent.getStringExtra("photos");
        String priceLevel = intent.getStringExtra("priceLevel");

        System.out.println("Le nom est " + name);
//        System.out.println("L'id est " + placeId);
        System.out.println("Le rating est " + rating);
        System.out.println("Le price level est " + priceLevel);

        TextView nameView = (TextView) findViewById(R.id.name);
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
                Intent otherActivity3 = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(otherActivity3);
                finish();

            }
        });
//        accessPic(photo);
        accessPic2(photo);

    }

//    public void accessPic(String photoReference) {
//        GoogleMapAPI googlemapapi2 = APIClient.getClient().create(GoogleMapAPI.class);
//        String key = getText(R.string.google_maps_key).toString();
//        googlemapapi2.getPhoto(key, photoReference, maxwidth).enqueue(new Callback<PlacePhotoResult>() {
//
//
//            @Override
//            public void onResponse(Call<PlacePhotoResult> call, Response<PlacePhotoResult> response) {
//                Bitmap photo = (response.body().getBitmap());
//                Log.d("Ca marche ?", "Ca marche ?");
//                ImageView imageView = (ImageView) findViewById(R.id.photos);
//                imageView.setImageBitmap(photo);
//            }
//
//            @Override
//            public void onFailure(Call<PlacePhotoResult> call, Throwable t) {
//                Log.d("erreur", "erreur con :" + t.getMessage());
//            }
//        });
//}
    public void accessPic2(String photoReference){
        ImageView ivPhoto = (ImageView) findViewById(R.id.photos);
        String url = "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=800" +
                "&photoreference="+photoReference +
                "&key=" + getText(R.string.google_maps_key).toString();

        Glide.with(this)
                .load(url)
                .into(ivPhoto);
    }

}