package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NewtripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtrip);
    }

    public void onButtonClick_checking(View v){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
    }

}