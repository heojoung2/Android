package com.example.heo.tiptrip;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TriplistActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    DBHelper dbHelper;
    String table_name="TRIPLIST";
    List<String> trip_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triplist);

        dbHelper = new DBHelper(getApplicationContext(), "db.db");
        try {       //table_name이 존재 할 경우
            dbHelper.Create_table(table_name);
        }
        catch(Exception e)
        {}
        ListView(); //리스트뷰 보이는 함수

        //dbHelper.Drop(table_name);
    }

    public void ListView(){
        ListView list = (ListView) findViewById(R.id.existing_trip_listview);
        trip_list = dbHelper.All_element(table_name);  //TRIPLIST테이블의 모든 요소 모두 불러오기

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,trip_list);    //ArrayAdapter : 배열에 담긴 데이터를 관리하는 클래스
        list.setAdapter(adapter);       //setAdapter로 액티비티에 보이게 한다
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,trip_list));

        list.setOnItemClickListener(this);   //리스트뷰의 아이템을 클릭했을 때 처리할 리스너를 설정
        list.setOnItemLongClickListener(this);  //롱클릭헀을 때
    }

    public void onButtonClick_newtrip(View v){
        Intent intent = new Intent(getApplicationContext(), NewtripActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);       //다음 액티비티를 스택에 넣지 않는다.
        dbHelper.close();
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        String str = trip_list.get(position);       //선택한 제목 전체 받아오기
        int split=str.indexOf(" ");
        String name=str.substring(split+1,str.length());
        String country=str.substring(1,split-1);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("name",name);       //아이템 넘기기
        intent.putExtra("country",country);

        dbHelper.close();
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("삭제하시겠습니까?");

        dialog.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String str = trip_list.get(position);
                int split=str.indexOf(" ");
                String name=str.substring(split+1,str.length());
                String country=str.substring(1,split-1);

                dbHelper.Delete(table_name,name,country);   //선택한 제목 삭제
                try{        //없을 경우
                    dbHelper.Drop(name+country+"household");
                    dbHelper.Drop(name+country+"dailog");
                }
                catch(Exception e){
                }
                ListView();
            }
        });

        dialog.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {   //아무것도 안함
            }
        });

        dialog.show();
        return true;
    }
}