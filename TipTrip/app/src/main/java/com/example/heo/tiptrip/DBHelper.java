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

    public void Drop(String table_name)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DROP TABLE "+table_name;
        db.execSQL(query);
        db.close();
    }

    public void Create_table(String name) {       //테이블 생성
        SQLiteDatabase db = getWritableDatabase();
        String query="";

        if(name=="TRIPLIST")
        {
            query = "CREATE TABLE "+name+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, country TEXT);";
        }
        else if(name=="HOUSEHOLD")
        {
            query = "CREATE TABLE "+name+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, country TEXT, data TEXT, money FLOAT, change FLOAT, number INTEGER);";
        }
        else if(name=="DAILOG")
        {
            query = "CREATE TABLE "+name+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, country TEXT, data TEXT, context TEXT);";
        }
        db.execSQL(query);
        db.close();
    }

    public void Insert_triplist(String name, String country){
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO TRIPLIST VALUES(null, '" + name + "', '" + country + "');";
        db.execSQL(query);
        db.close();
    }

    public void Insert_household(String name, String country,String data , float money, float change, int number){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";
        db.execSQL(query);
        db.close();
    }

    public void Insert_dailog(String name, String country,String data, String context){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";
        db.execSQL(query);
        db.close();
    }

    public void Delete(String table_name,String name, String country){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+table_name+" WHERE name='"+name+"'"+" AND country='"+country+"'";
        db.execSQL(query);
        db.close();
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
