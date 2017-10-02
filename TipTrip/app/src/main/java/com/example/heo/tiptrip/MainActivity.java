package com.example.heo.tiptrip;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Toast.makeText(getApplicationContext(), intent.getExtras().get("name").toString(),Toast.LENGTH_SHORT).show();

    }

    public void onButtonClick_menu(View v){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void onButtonClick_household(View v){
        Intent intent = new Intent(getApplicationContext(), HouseholdActivity.class);
        startActivity(intent);
    }
    public void onButtonClick_touristsite(View v){
        Intent intent = new Intent(getApplicationContext(), TouristsiteActivity.class);
        startActivity(intent);
    }
    public void onButtonClick_dailog(View v){
        Intent intent = new Intent(getApplicationContext(), DailogActivity.class);
        startActivity(intent);
    }
}
