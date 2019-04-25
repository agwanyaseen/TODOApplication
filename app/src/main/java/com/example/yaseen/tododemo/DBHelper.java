package com.example.yaseen.tododemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    Cursor c;

    public DBHelper(Context context) {
        super(context, "TodoDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE TODOLIST (id INTEGER PRIMARY KEY AUTOINCREMENT  , list TEXT ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TODOLIST");
    }

    public Boolean insertList(String list) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put("list", list);
        long row = sqLiteDatabase.insert("TODOLIST", null, val);
        if (row > 0)
            return true;
        else
            return false;


    }

    public Cursor viewRecords() {
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(" Select * from TODOLIST", null);
        return c;
    }

    public int getID(String list){
        Cursor c;
        int id=0;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT id FROM TODOLIST WHERE list = '"+list+"'",null);
        System.out.println("Cursor value = "+list);
        while(c.moveToNext()) {
            id =  c.getInt(0);
        }
      return id;
      
    }


    public Boolean deleteList(int id){
        System.out.println("DBHelper Called = "+id);
        SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("DELETE FROM TODOLIST WHERE id = " + id);
       return true;
    }

    public Boolean updateList(int id,String newTodo){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("Cursor Id  = "+id);
        System.out.println("CUrsor value = "+newTodo);
        db.execSQL(" UPDATE TODOLIST SET list = '"+newTodo+"' WHERE id = " +id);
        return true;
    }

}

