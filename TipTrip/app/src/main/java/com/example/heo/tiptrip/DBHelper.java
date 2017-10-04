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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void Drop(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DROP TABLE "+name;
        db.execSQL(query);
        db.close();
    }

    public void Create_table(String name) {       //테이블 생성
        SQLiteDatabase db = getWritableDatabase();
        String query = "CREATE TABLE "+name+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, country TEXT);";
        db.execSQL(query);
        db.close();
    }


    public void Insert(String table_name, String name, String country){
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO "+table_name+" VALUES(null, '" + name + "', '" + country + "');";
        db.execSQL(query);
        db.close();
    }

    public List<String> get_name_country(String table_name,int position){
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT name,country FROM "+table_name +" WHERE id = "+ (position+1), null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(0));
            result.add(cursor.getString(1));
        }
        db.close();
        return result;
    }

    public List<String> All_element(String table_name){
        SQLiteDatabase db = getReadableDatabase();
        List<String> result = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name, null);
        while (cursor.moveToNext()){
            result.add("["+cursor.getString(2)+"] "+cursor.getString(1));
        }
        db.close();
        return result;
    }
}
