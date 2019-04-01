package com.example.itme.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.itme.R;

public class InscriptionActivity extends AppCompatActivity {

    private Button retour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRetour = new Intent(InscriptionActivity.this, MainActivity.class);
                startActivity(intentRetour);
            }
        });
    }
}
