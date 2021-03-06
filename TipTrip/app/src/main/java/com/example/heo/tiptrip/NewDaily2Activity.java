package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class NewDaily2Activity extends AppCompatActivity {
    String name = "";
    String country = "";
    String title = "";
    String year = "";
    String month = "";
    String day = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        name = intent.getExtras().get("name").toString();
        country = intent.getExtras().get("country").toString();
        title = intent.getExtras().get("title").toString();
        year = intent.getExtras().get("year").toString();
        month = intent.getExtras().get("month").toString();
        day = intent.getExtras().get("day").toString();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("새 일지");

        setContentView(R.layout.activity_newdaily2);
    }

    public void onButtonClick_new_daily_checking(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("country", country);

        EditText daily_text = (EditText) findViewById(R.id.daily_text);
        String context = daily_text.getText().toString();

        DBHelper dbHelper = new DBHelper(getApplicationContext(), "db.db");     //db불러오기
        dbHelper.Insert_daily(name,country,title,year,month,day,context);
        dbHelper.close();

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

}