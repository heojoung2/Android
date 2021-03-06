package com.example.heo.tiptrip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewtripActivity extends AppCompatActivity {

    private String[] country_list={"네덜란드","독일","라트비아","룩셈부르크","리투아니아","모나코","몬테네그로","몰타","바티칸시국","벨기에","산마리노","스페인","슬로바키아","슬로베니아","아일랜드","안도라","에스토니아","오스트리아","이탈리아","일본","코소보","키프로스","포르투갈","프랑스","핀란드","한국","홍콩"};
    private String country="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("New Trip");
        setContentView(R.layout.activity_newtrip);
    }

    public void onButtonClick_country(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("나라목록");

        //다이얼로그 내용에 표시할 아이템 리스트와 아이템을 클릭했을 때 반응할 리스터를 설정한다
        dialog.setItems(country_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView country_text = (TextView) findViewById(R.id.country_text);
                country = country_list[i];
                country_text.setText(country);      //country_text에 선택한 나라 보여주기
            }
        });
        dialog.show();
    }

    public void onButtonClick_checking(View v){
        EditText name_text = (EditText) findViewById(R.id.name_text);
        String name = name_text.getText().toString();

        if(name.length() == 0) {
            Toast.makeText(getApplicationContext(),"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();
        }
        else if(country.length()==0) {
            Toast.makeText(getApplicationContext(),"나라를 선택해 주세요",Toast.LENGTH_SHORT).show();
        }
        else{
            DBHelper dbHelper = new DBHelper(getApplicationContext(), "db.db");     //db불러오기
            int cnt=dbHelper.Search_count(name,country);

            if(cnt!=0){ //겹치는게 있을 경우
                Toast.makeText(getApplicationContext(),"중복됩니다",Toast.LENGTH_SHORT).show();
                dbHelper.close();
            }
            else {

                dbHelper.Insert_triplist(name_text.getText().toString(), country);     //데이터 추가
                dbHelper.close();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name", name_text.getText());
                intent.putExtra("country", country);
                startActivity(intent);
            }
        }
    }
}