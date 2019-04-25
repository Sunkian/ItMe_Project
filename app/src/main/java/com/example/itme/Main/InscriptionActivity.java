package com.example.itme.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.itme.R;
import com.example.itme.api.APIDataBase;
import com.example.itme.api.DataBaseAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity {

  private Button retour;
  private Button validate;


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
        finish();
      }
    });

    this.validate = (Button) findViewById(R.id.validate);    //Appel des deux boutons pour la redirection vers les pages 'connexion' et 'inscription'
    validate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view1) {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        int age = Integer.parseInt(((EditText) findViewById(R.id.age)).getText().toString());
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String gender = ((EditText) findViewById(R.id.gender)).getText().toString();

        DataBaseAPI databaseApi = APIDataBase.getClient().create(DataBaseAPI.class);
        databaseApi.register(email, password, age, gender).enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
            Log.d("response:", response.body());
            if (response.body() != "Duplicate") {
              System.out.println("response:" + response.body());

//                            Intent intentRetour2 = new Intent(InscriptionActivity.this, MainActivity.class);
//                            startActivity(intentRetour2);
//                            finish();
            }


          }

          @Override
          public void onFailure(Call<String> call, Throwable t) {

          }
        });


      }
    });


  }
}
