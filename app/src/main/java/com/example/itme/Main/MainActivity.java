package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.itme.R;

public class MainActivity extends AppCompatActivity {

    private Button connexion;
    private Button inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.connexion = (Button) findViewById(R.id.connexion);    //Appel des deux boutons pour la redirection vers les pages 'connexion' et 'inscription'
        connexion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view1) {
                Intent otherActivity1 = new Intent(getApplicationContext(), ConnexionActivity.class);
                startActivity(otherActivity1);
                finish();

            }
        });

        this.inscription = (Button) findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view1) {
                Intent otherActivity1 = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(otherActivity1);
                finish();
            }
        });
    }
}