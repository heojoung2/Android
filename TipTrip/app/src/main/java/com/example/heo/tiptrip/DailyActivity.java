package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DailyActivity extends AppCompatActivity {
    String name="";
    String country="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        name = intent.getExtras().get("name").toString();
        country = intent.getExtras().get("country").toString();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("일지");

        setContentView(R.layout.activity_daily);
    }
    public void onButtonClick_newdialog(View v){
        Intent intent = new Intent(getApplicationContext(), NewDailyActivity.class);

        intent.putExtra("name", name);
        intent.putExtra("country", country);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}