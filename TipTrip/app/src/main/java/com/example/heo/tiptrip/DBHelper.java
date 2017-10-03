package com.example.heo.tiptrip;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name){      //생성자로 관리할 DB와 버전 정보를 받음
        super(context,name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {       //테이블 생성
        String query = "CREATE TABLE TRIPLIST (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, country TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXITS TRIPLIST";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void insert(String name, String country){
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO TRIPLIST VALUES(null, '" + name + "', '" + country + "');";
        db.execSQL(query);
        db.close();
    }

    public List<String> get_name_country(int position){
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT name,country FROM TRIPLIST", null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(0));
            result.add(cursor.getString(1));
            System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+cursor.getString(0));
            System.out.println("Z"+cursor.getString(1));

        }
        return result;
    }

    public List<String> All_element(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM TRIPLIST", null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(1)+ " ["+cursor.getString(2)+"]");
        }
        return result;
    }


}
