package com.example.musica_listentillthehorizon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase Songdb) {
        Songdb.execSQL("create Table users(username TEXT primary key,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase Songdb, int oldVersion, int newVersion) {
        Songdb.execSQL("drop Table if exists users");
    }
    public Boolean insertData(String username,String password)
    {
        SQLiteDatabase Songdb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = Songdb.insert("users",null,contentValues);
        if (result==-1) return false;
        else return true;
    }

    public Boolean checkusername(String username)
    {
        SQLiteDatabase Songdb = this.getWritableDatabase();
        Cursor cursor = Songdb.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount()>0)   return true;
        else   return false;
    }
    public Boolean checkusernamepassword(String username,String password)
    {
        SQLiteDatabase Songdb = this.getWritableDatabase();
        Cursor cursor = Songdb.rawQuery("Select * from users where username = ? and password = ?", new String[]{username,password});
        if (cursor.getCount()>0)   return true;
        else   return false;
    }

}
