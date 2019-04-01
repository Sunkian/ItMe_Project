package com.example.itme.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.itme.R;

public class Data extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String placeId = intent.getStringExtra("PlaceId");
    }
}
