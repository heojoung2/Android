package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DailyContextActivity extends AppCompatActivity {
    String name;
    String country;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("새 일지");

        Intent intent = getIntent();
        name = intent.getExtras().get("name").toString();
        country = intent.getExtras().get("country").toString();
        String title = intent.getExtras().get("title").toString();
        String year = intent.getExtras().get("year").toString();
        String month = intent.getExtras().get("month").toString();
        String day = intent.getExtras().get("day").toString();

        setContentView(R.layout.activity_daily_context);

        TextView textView = (TextView) findViewById(R.id.choice_daily_text);

        dbHelper = new DBHelper(getApplicationContext(), "db.db");
        String result = dbHelper.Select_Daily(name, country, title, year, month, day);
        textView.setText(result);
        dbHelper.close();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), DailyActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("country", country);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        dbHelper.close();
        startActivity(intent);
    }
}