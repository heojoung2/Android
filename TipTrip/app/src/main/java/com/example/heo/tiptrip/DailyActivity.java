package com.example.heo.tiptrip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class DailyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    String name="";
    String country="";
    List<String> daily_list;
    DBHelper dbHelper;

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
        dbHelper = new DBHelper(getApplicationContext(), "db.db");
        ListView list = (ListView) findViewById(R.id.dialog_listview);
        daily_list = dbHelper.Display_dailly(name,country);  //TRIPLIST테이블의 모든 요소 모두 불러오기

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,daily_list);    //ArrayAdapter : 배열에 담긴 데이터를 관리하는 클래스
        list.setAdapter(adapter);       //setAdapter로 액티비티에 보이게 한다

        list.setOnItemClickListener(this);   //리스트뷰의 아이템을 클릭했을 때 처리할 리스너를 설정
        list.setOnItemLongClickListener(this);  //롱클릭했을 때
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        DBHelper dbHelper;
        dbHelper = new DBHelper(getApplicationContext(), "db.db");

        String str = daily_list.get(position);       //선택한 항목 전체 받아오기
        int split=str.indexOf(" ");
        String title=str.substring(split+1,str.length());
        String data = str.substring(0,split);
        String date[] = data.split("/");

        Intent intent = new Intent(getApplicationContext(), DailyContextActivity.class);
        intent.putExtra("name",name);       //아이템 넘기기
        intent.putExtra("country",country);
        intent.putExtra("title",title);
        intent.putExtra("year",date[0]);
        intent.putExtra("month",date[1]);
        intent.putExtra("day",date[2]);

        dbHelper.close();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("삭제하시겠습니까?");

        dialog.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String str = daily_list.get(position);
                int split=str.indexOf(" ");
                String title=str.substring(split+1,str.length());
                String data = str.substring(0,split);
                String date[] = data.split("/");

                dbHelper.Delete_Daily(name,country,title,date[0],date[1],date[2]);
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