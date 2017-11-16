package com.example.heo.tiptrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

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
        ListView();
    }

    public void onButtonClick_newdialog(View v){
        Intent intent = new Intent(getApplicationContext(), NewDailyActivity.class);

        intent.putExtra("name", name);
        intent.putExtra("country", country);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void ListView(){
        DBHelper dbHelper;
        List<String> daily_list;

        dbHelper = new DBHelper(getApplicationContext(), "db.db");
        ListView list = (ListView) findViewById(R.id.dialog_listview);
        daily_list = dbHelper.Display_dailly(name,country);  //TRIPLIST테이블의 모든 요소 모두 불러오기

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,daily_list);    //ArrayAdapter : 배열에 담긴 데이터를 관리하는 클래스
        list.setAdapter(adapter);       //setAdapter로 액티비티에 보이게 한다
        dbHelper.close();
    }
}