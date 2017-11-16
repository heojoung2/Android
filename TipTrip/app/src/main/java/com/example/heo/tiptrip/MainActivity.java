package com.example.heo.tiptrip;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String name="";
    String country="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        name = intent.getExtras().get("name").toString();        //전 액티비티에서 넘긴 아이템 받기
        country = intent.getExtras().get("country").toString();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(name);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), TriplistActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //호출되었던 액티비티의 윗부분이 모두 제거
        startActivity(intent);
    }

    public void onButtonClick_menu(View v){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void onButtonClick_household(View v){
        Intent intent = new Intent(getApplicationContext(), HouseholdActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("country", country);
        startActivity(intent);
    }
    public void onButtonClick_touristsite(View v){
        Intent intent = new Intent(getApplicationContext(), TouristsiteActivity.class);
        intent.putExtra("country", country);
        startActivity(intent);
    }
    public void onButtonClick_dailog(View v){
        Intent intent = new Intent(getApplicationContext(), DailyActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("country", country);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
