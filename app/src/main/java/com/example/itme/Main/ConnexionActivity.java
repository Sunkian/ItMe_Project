package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.itme.R;

public class ConnexionActivity extends AppCompatActivity {

    private Button map;
    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);




        this.map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view1) {
                Intent otherActivity1 = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(otherActivity1);
                finish();
            }
        });


        retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRetour = new Intent(ConnexionActivity.this, MainActivity.class);
                startActivity(intentRetour);
            }
        });


}
}