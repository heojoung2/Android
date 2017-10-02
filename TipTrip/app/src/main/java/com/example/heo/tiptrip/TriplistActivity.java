package com.example.heo.tiptrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TriplistActivity extends Activity implements AdapterView.OnItemClickListener {
    private String[] examples = {"example1","example2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triplist);

        ListView list = (ListView) findViewById(R.id.existing_trip_listview);

        //ArrayAdapter : 배열에 담긴 데이터를 관리하는 클래스
        //setAdapter로 액티비티에 보이게 한다
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,examples));

        list.setOnItemClickListener(this);   //리스트뷰의 아이템을 클릭했을 때 처리할 리스너를 설정
    }

    public void onButtonClick_newtrip(View v){
        Intent intent = new Intent(getApplicationContext(), NewtripActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);   //다음 액티비티를 스택에 넣지 않는다.
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        Toast.makeText(getApplicationContext(),examples[position],Toast.LENGTH_SHORT).show();
        

    }
}