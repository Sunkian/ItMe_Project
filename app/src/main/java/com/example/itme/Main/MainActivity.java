package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.itme.GlobalVariable;
import com.example.itme.R;
import com.example.itme.api.APIDataBase;
import com.example.itme.api.DataBaseAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private Button connexion;
  private Button inscription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.connexion = (Button) findViewById(R.id.connexion);    //Appel des deux boutons pour la redirection vers les pages 'connexion' et 'inscription'
    connexion.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view1) {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        DataBaseAPI databaseApi = APIDataBase.getClient().create(DataBaseAPI.class);
        databaseApi.logIn(email, password).enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("response:" + response.body());
            if (!response.body().equals("Ko")) {
              GlobalVariable gv = ((GlobalVariable) getApplicationContext());
              gv.setId(response.body());
              Intent otherActivity1 = new Intent(getApplicationContext(), ConnexionActivity.class);
              startActivity(otherActivity1);
              finish();
            }
          }

          @Override
          public void onFailure(Call<String> call, Throwable t) {

          }
        });


      }
    });

    this.inscription = (Button) findViewById(R.id.inscription);
    inscription.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view1) {
        Intent otherActivity1 = new Intent(getApplicationContext(), InscriptionActivity.class);
        startActivity(otherActivity1);
        finish();
      }
    });
  }
}