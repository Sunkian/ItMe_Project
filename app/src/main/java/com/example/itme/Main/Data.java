package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.itme.R;

public class Data extends AppCompatActivity {

    private Button rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String placeId = intent.getStringExtra("placeId");
        String rating = intent.getStringExtra("rating");
        String photo = intent.getStringExtra("photos");
        System.out.println("Le nom est " + name);
        System.out.println("L'id est " + placeId);
        System.out.println("Le rating est " + rating);

        TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText(name);
        TextView placeIdView = (TextView) findViewById(R.id.placeId);
        placeIdView.setText(placeId);
        TextView ratingView = (TextView) findViewById(R.id.rating);
        ratingView.setText(rating);


//        TextView photosView = (TextView) findViewById(R.id.photos);
//        photosView.setText(photo);

        this.rate = (Button) findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view1) {
                Intent otherActivity3 = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(otherActivity3);
                finish();

            }
        });




    }
}
