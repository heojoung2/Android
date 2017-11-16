package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class NewDailyActivity extends AppCompatActivity {
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

        setContentView(R.layout.activity_newdaily);
    }

    public void onButtonClick_data_checking(View v) {
        Intent intent = new Intent(getApplicationContext(), NewDaily2Activity.class);

        DatePicker datepicker;  //데이트 이벤트
        datepicker = (DatePicker)findViewById(R.id.datePicker);

        EditText title_text = (EditText) findViewById(R.id.title_text);
        String title = title_text.getText().toString();

        if(title.length() == 0) {
            Toast.makeText(getApplicationContext(),"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
        else {
            intent.putExtra("name", name);
            intent.putExtra("country", country);
            intent.putExtra("title", title);
            intent.putExtra("year", datepicker.getYear());
            intent.putExtra("month", datepicker.getMonth() + 1);
            intent.putExtra("day", datepicker.getDayOfMonth());

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }
}