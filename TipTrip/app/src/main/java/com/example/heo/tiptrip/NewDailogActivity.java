package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NewDailogActivity extends AppCompatActivity {
    String name = "";
    String country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        name = intent.getExtras().get("name").toString();
        country = intent.getExtras().get("country").toString();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("새 일지");

        setContentView(R.layout.activity_newdailog);
    }

    public void onButtonClick_data_checking(View v) {
    }
}