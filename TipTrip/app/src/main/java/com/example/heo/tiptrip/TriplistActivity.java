package com.example.heo.tiptrip;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TriplistActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DBHelper dbHelper;
    String table_name="TRIPLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triplist);
        ListView list = (ListView) findViewById(R.id.existing_trip_listview);

        dbHelper = new DBHelper(getApplicationContext(), "db.db");
        try {
            dbHelper.Create_table(table_name);
        }
        catch(Exception e)
        {
            int a=3;
        }

        List<String> trip_list = dbHelper.All_element(table_name);

        //ArrayAdapter : 배열에 담긴 데이터를 관리하는 클래스
        //setAdapter로 액티비티에 보이게 한다
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,trip_list));

        list.setOnItemClickListener(this);   //리스트뷰의 아이템을 클릭했을 때 처리할 리스너를 설정

    }

    public void onButtonClick_newtrip(View v){
        Intent intent = new Intent(getApplicationContext(), NewtripActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);   //다음 액티비티를 스택에 넣지 않는다.

        dbHelper.close();
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        List<String> name_country = dbHelper.get_name_country(table_name,position);
        String name = name_country.get(0);
        String country = name_country.get(1);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("name",name);       //아이템 넘기기
        intent.putExtra("country",country);

        dbHelper.close();
        startActivity(intent);
    }

}